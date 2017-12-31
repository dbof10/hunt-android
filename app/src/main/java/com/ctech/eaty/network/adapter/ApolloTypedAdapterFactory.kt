package com.ctech.eaty.network.adapter

import com.apollographql.apollo.CustomTypeAdapter
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat


object ApolloTypedAdapterFactory {

    val TYPED_JSON: CustomTypeAdapter<String> = object : CustomTypeAdapter<String> {
        override fun decode(value: String): String {
            return value
        }

        override fun encode(value: String): String {
            return value
        }
    }


    val TYPED_DATE_TIME: CustomTypeAdapter<DateTime> = object : CustomTypeAdapter<DateTime> {
        override fun decode(value: String): DateTime {
            val fmt = ISODateTimeFormat.dateTimeParser()
            return fmt.parseDateTime(value)
        }

        override fun encode(value: DateTime): String {
            val fmt = ISODateTimeFormat.dateTime()
            return fmt.print(value)
        }
    }

}

