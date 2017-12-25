package com.ctech.eaty.network.adapter

import com.apollographql.apollo.CustomTypeAdapter


object ApolloTypedAdapterFactory {

    var TYPED_JSON: CustomTypeAdapter<String> = object : CustomTypeAdapter<String> {
        override fun decode(value: String): String {
            return value
        }

        override fun encode(value: String): String {
            return value
        }
    }
}

