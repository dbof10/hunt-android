package com.ctech.eaty.util

import android.net.Uri

object ResizeImageUrlProvider {

    private val PROXY_DOMAIN = ".rsz.io"
    private val YOUTUBE_PLACEHOLDER = "https://img.youtube.com/vi/%s/hqdefault.jpg"

    fun getThumbnailUrl(videoId: String): String{
        return String.format(YOUTUBE_PLACEHOLDER, videoId)
    }

    fun overrideUrl(url: String?, size: Int): String {
        if (url.isNullOrEmpty()) {
            return ""
        }
        val uri = Uri.parse(url)
        return StringBuilder("http://")
                .append(uri.host)
                .append(uri.path)
                .append("?")
                .append(UrlEncodedQueryString.parse(uri).set("w", size).remove("h"))
                .toString()

    }

    fun getNewUrl(url: String?, size: Int): String {
        if (url.isNullOrEmpty()) {
            return ""
        }
        val uri = Uri.parse(url)
        val inlineUrl = uri.getQueryParameter("url")
        if (inlineUrl != null) {
            return StringBuilder("http://")
                    .append(uri.host)
                    .append(PROXY_DOMAIN)
                    .append(uri.path)
                    .append("?")
                    .append(UrlEncodedQueryString.parse(uri))
                    .append("?")
                    .append(UrlEncodedQueryString.parse(Uri.parse(inlineUrl)).set("w", size))
                    .toString()
        }
        return StringBuilder("http://")
                .append(uri.host)
                .append(PROXY_DOMAIN)
                .append(uri.path)
                .append("?")
                .append(UrlEncodedQueryString.parse(uri).set("w", size))
                .toString()
    }

}