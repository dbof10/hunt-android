package com.ctech.eaty.entity

data class UpcomingBody(
        val backgroundUrl: String,
        val foregroundUrl: String,
        val kind: String,
        val brandColor: String,
        val whoText: UpcomingBodyMessage,
        val whatText: UpcomingBodyMessage,
        val whyText: UpcomingBodyMessage)