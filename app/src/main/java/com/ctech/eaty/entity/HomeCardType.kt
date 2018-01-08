package com.ctech.eaty.entity

import android.support.annotation.StringDef

@Retention(AnnotationRetention.SOURCE)
@StringDef(UPCOMING_PAGE, NEW_POSTS, SUGGESTED_TOPIC)
annotation class HomeCardType

const val UPCOMING_PAGE = "UpcomingPagesCard"
const val NEW_POSTS = "NewPostsCard"
const val SUGGESTED_PRODUCTS = "TopicCard"
const val SUGGESTED_TOPIC = "TopicsCard"
const val JOBS = "JobsCard"
const val COLLECTION = "CollectionCard"