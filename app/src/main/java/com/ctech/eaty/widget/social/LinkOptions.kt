package com.ctech.eaty.widget.social;

import android.support.annotation.IntDef

const val HASHTAG = 1
const val MENTION = 2
const val PHONE = 4
const val EMAIL = 8
const val URL = 16

@Retention(AnnotationRetention.RUNTIME)
@IntDef(HASHTAG.toLong(), MENTION.toLong(), PHONE.toLong(), EMAIL.toLong(), URL.toLong())
annotation class LinkOptions