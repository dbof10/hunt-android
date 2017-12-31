package com.ctech.eaty.entity

data class TopicsCard(val topics: List<Topic>) : HomeCard {
    override val type: String
        get() = SUGGESTED_TOPIC
}