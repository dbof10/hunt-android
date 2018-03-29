package com.ctech.eaty.network.adapter

import com.apollographql.apollo.response.CustomTypeAdapter
import com.apollographql.apollo.response.CustomTypeValue
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat


object ApolloTypedAdapterFactory {


    val TYPED_JSON: CustomTypeAdapter<String> = object : CustomTypeAdapter<String> {
        override fun decode(value: CustomTypeValue<*>): String {
            return value.value as String
        }


        override fun encode(value: String): CustomTypeValue<*> {
            return CustomTypeValue.GraphQLString(value)
        }
    }


    val TYPED_DATE_TIME: CustomTypeAdapter<DateTime> = object : CustomTypeAdapter<DateTime> {
        override fun decode(value: CustomTypeValue<*>): DateTime {
            val fmt = ISODateTimeFormat.dateTimeParser()
            return fmt.parseDateTime(value.value as String)
        }


        override fun encode(value: DateTime): CustomTypeValue<*> {
            val fmt = ISODateTimeFormat.dateTime()
            return CustomTypeValue.GraphQLString(fmt.print(value))
        }
    }

}

