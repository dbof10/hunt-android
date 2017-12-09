package com.ctech.eaty.network.cache

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class ApolloSql : SQLiteOpenHelper {

    private constructor(context: Context) : super(context, DATABASE_NAME, null, DATABASE_VERSION)

    constructor(context: Context, name: String) : super(context, name, null, DATABASE_VERSION)

    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL(DATABASE_CREATE)
        database.execSQL(CREATE_KEY_INDEX)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORDS)
        onCreate(db)
    }

    companion object {

        val TABLE_RECORDS = "records"
        val COLUMN_ID = "_id"
        val COLUMN_RECORD = "record"
        val COLUMN_KEY = "key"

        private val DATABASE_NAME = "apollo_cache.db"
        private val DATABASE_VERSION = 1

        private val DATABASE_CREATE = java.lang.String.format(
                "create table %s( %s integer primary key autoincrement, %s text not null, %s text not null);",
                TABLE_RECORDS, COLUMN_ID, COLUMN_KEY, COLUMN_RECORD)

        private val IDX_RECORDS_KEY = "idx_records_key"
        private val CREATE_KEY_INDEX = java.lang.String.format("CREATE INDEX %s ON %s (%s)", IDX_RECORDS_KEY, TABLE_RECORDS, COLUMN_KEY)

        fun create(context: Context): ApolloSql {
            return ApolloSql(context)
        }

        fun create(context: Context, name: String): ApolloSql {
            return ApolloSql(context, name)
        }
    }
}