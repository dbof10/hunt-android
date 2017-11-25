package com.ctech.eaty.util

object LinkMatcher {

    private val COLLECTION_MATCHER = Regex("@\\w+\\/collections")
    private val USER_MATCHER = Regex("@\\w+\$")

    fun matchCollections(url: String): Boolean = COLLECTION_MATCHER.containsMatchIn(url)

    fun matchUser(url: String): Boolean = USER_MATCHER.containsMatchIn(url)
}