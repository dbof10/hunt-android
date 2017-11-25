package com.ctech.eaty.util

val INDEX_NOT_FOUND = -1


fun String.substringBetween(str: String, tag: String): String = substringBetween(str, tag, tag)

fun String.substringBetween(str: String, open: String, close: String): String {
    val start = str.indexOf(open)
    if (start != INDEX_NOT_FOUND) {
        val end = str.indexOf(close, start + open.length)
        if (end != INDEX_NOT_FOUND) {
            return str.substring(start + open.length, end)
        }
    }
    return ""
}