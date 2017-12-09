package com.ctech.eaty.network.cache

import com.apollographql.apollo.cache.normalized.NormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.RecordFieldJsonAdapter


class SqlNormalizedCacheFactory(private val apolloSql: ApolloSql) : NormalizedCacheFactory<SqlNormalizedCache>() {

    override fun create(recordFieldAdapter: RecordFieldJsonAdapter): SqlNormalizedCache {
        return SqlNormalizedCache(recordFieldAdapter, apolloSql)
    }
}