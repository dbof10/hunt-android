package com.ctech.eaty.entity

data class UpcomingDetail(
        val id: String,
        val name: String,
        val facebook_url: String?,
        val twitterUrl: String?,
        val angellistUrl: String?,
        val body: UpcomingBody,
        val topSubscribers: List<User>,
        val upcomingProducts: List<UpcomingProduct>,
        val successMessage: UpcomingBodyMessage,
        val user: User,
        val subscriberCount: Int)