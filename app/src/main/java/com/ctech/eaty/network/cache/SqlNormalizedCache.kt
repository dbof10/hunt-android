package com.ctech.eaty.network.cache

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteStatement
import com.apollographql.apollo.api.internal.Optional
import com.apollographql.apollo.cache.ApolloCacheHeaders.DO_NOT_STORE
import com.apollographql.apollo.cache.ApolloCacheHeaders.EVICT_AFTER_READ
import com.apollographql.apollo.cache.CacheHeaders
import com.apollographql.apollo.cache.normalized.CacheKey
import com.apollographql.apollo.cache.normalized.NormalizedCache
import com.apollographql.apollo.cache.normalized.Record
import com.apollographql.apollo.cache.normalized.RecordFieldJsonAdapter
import com.ctech.eaty.network.cache.ApolloSql.Companion.COLUMN_KEY
import com.ctech.eaty.network.cache.ApolloSql.Companion.COLUMN_RECORD
import com.ctech.eaty.network.cache.ApolloSql.Companion.TABLE_RECORDS
import java.io.IOException
import java.util.Collections


class SqlNormalizedCache internal constructor(private val recordFieldAdapter: RecordFieldJsonAdapter,
                                              private val apolloSql: ApolloSql) : NormalizedCache() {

    private val INSERT_STATEMENT = String.format("INSERT INTO %s (%s,%s) VALUES (?,?)",
            TABLE_RECORDS,
            COLUMN_KEY,
            COLUMN_RECORD)
    private val UPDATE_STATEMENT = String.format("UPDATE %s SET %s=?, %s=? WHERE %s=?",
            TABLE_RECORDS,
            COLUMN_KEY,
            COLUMN_RECORD,
            COLUMN_KEY)
    private val DELETE_STATEMENT = String.format("DELETE FROM %s WHERE %s=?",
            TABLE_RECORDS,
            COLUMN_KEY)
    private val DELETE_ALL_RECORD_STATEMENT = String.format("DELETE FROM %s", TABLE_RECORDS)

    private var database: SQLiteDatabase = apolloSql.writableDatabase
    private val allColumns = arrayOf(ApolloSql.COLUMN_ID, ApolloSql.COLUMN_KEY, ApolloSql.COLUMN_RECORD)

    private var insertStatement: SQLiteStatement
    private var updateStatement: SQLiteStatement
    private var deleteStatement: SQLiteStatement
    private var deleteAllRecordsStatement: SQLiteStatement

    init {
        insertStatement = database.compileStatement(INSERT_STATEMENT)
        updateStatement = database.compileStatement(UPDATE_STATEMENT)
        deleteStatement = database.compileStatement(DELETE_STATEMENT)
        deleteAllRecordsStatement = database.compileStatement(DELETE_ALL_RECORD_STATEMENT)
    }

    override fun loadRecord(key: String, cacheHeaders: CacheHeaders): Record? {
        return selectRecordForKey(key)
                .apply {
                    if (cacheHeaders.hasHeader(EVICT_AFTER_READ)) {
                        deleteRecord(key)
                    }
                }
                .or(nextCache().flatMap { cache ->
                    Optional.fromNullable(cache.loadRecord(key, cacheHeaders))
                })
                .orNull()
    }

    override fun merge(recordSet: Collection<Record>,
                       cacheHeaders: CacheHeaders): Set<String> {
        if (cacheHeaders.hasHeader(DO_NOT_STORE)) {
            return emptySet()
        }

        val changedKeys: Set<String>
        try {
            database.beginTransaction()
            changedKeys = super.merge(recordSet, cacheHeaders)
            database.setTransactionSuccessful()
        } finally {
            database.endTransaction()
        }
        return changedKeys
    }

    override fun clearAll() {

        nextCache().apply { cache -> cache.clearAll() }
        clearCurrentCache()
    }

    override fun remove(cacheKey: CacheKey): Boolean {
        val result: Boolean

        result = nextCache().map { cache ->
            cache.remove(cacheKey)
        }.or(java.lang.Boolean.FALSE)

        return result || deleteRecord(cacheKey.key())
    }

    fun close() {
        apolloSql.close()
    }

    private fun createRecord(key: String, fields: String): Long {
        insertStatement.bindString(1, key)
        insertStatement.bindString(2, fields)

        return insertStatement.executeInsert()
    }

    private fun updateRecord(key: String, fields: String) {
        updateStatement.bindString(1, key)
        updateStatement.bindString(2, fields)
        updateStatement.bindString(3, key)

        updateStatement.executeInsert()
    }

    private fun deleteRecord(key: String): Boolean {
        deleteStatement.bindString(1, key)
        return deleteStatement.executeUpdateDelete() > 0
    }

    private fun selectRecordForKey(key: String): Optional<Record> {
        val cursor = database.query(ApolloSql.TABLE_RECORDS,
                allColumns, ApolloSql.COLUMN_KEY + " = ?", arrayOf(key), null, null, null)
        if (cursor == null || !cursor.moveToFirst()) {
            return Optional.absent()
        }
        return try {
            Optional.of(cursorToRecord(cursor))
        } catch (exception: IOException) {
            Optional.absent()
        } finally {
            cursor.close()
        }
    }

    @Throws(IOException::class)
    private fun cursorToRecord(cursor: Cursor): Record {
        val key = cursor.getString(1)
        val jsonOfFields = cursor.getString(2)
        return Record.builder(key).addFields(recordFieldAdapter.from(jsonOfFields)).build()
    }

    private fun clearCurrentCache() {
        deleteAllRecordsStatement.execute()
    }

    override fun performMerge(apolloRecord: Record, cacheHeaders: CacheHeaders): Set<String> {
        val optionalOldRecord = selectRecordForKey(apolloRecord.key())
        val changedKeys: Set<String>
        if (!optionalOldRecord.isPresent) {
            createRecord(apolloRecord.key(), recordFieldAdapter.toJson(apolloRecord.fields()))
            changedKeys = Collections.emptySet()
        } else {
            val oldRecord = optionalOldRecord.get()
            changedKeys = oldRecord.mergeWith(apolloRecord)
            if (!changedKeys.isEmpty()) {
                updateRecord(oldRecord.key(), recordFieldAdapter.toJson(oldRecord.fields()))
            }
        }
        return changedKeys
    }
}