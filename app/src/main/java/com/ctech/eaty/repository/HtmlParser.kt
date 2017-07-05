package com.ctech.eaty.repository

import android.net.Uri
import com.ctech.eaty.util.substringBetween
import io.reactivex.Single
import org.jsoup.Jsoup
import javax.inject.Inject

class HtmlParser @Inject constructor() {

    fun getProductIdBy(url: String): Single<String> {
        return getProductIdUri(url)
                .map {
                    Uri.parse(it).lastPathSegment
                }
    }

    fun getProductIdUri(url: String): Single<String> {
        return Single.create { e ->
            val document = Jsoup.connect(url).get()
            val matchedTag = document.select("script").map {
                it.toString()
            }.filter {
                it.contains("mobileAppUrl")
            }.firstOrNull()

            e.onSuccess(matchedTag?.substringBetween(matchedTag.toString(), "\'", "\'") ?: "")
        }
    }
}