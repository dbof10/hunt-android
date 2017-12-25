package com.ctech.eaty.entity

import android.util.Log
import android.webkit.MimeTypeMap
import com.ctech.eaty.util.Constants
import com.ctech.eaty.util.ResizeImageUrlProvider
import com.google.gson.annotations.SerializedName
import com.jaredrummler.android.util.HtmlBuilder

data class UpcomingBodyMessage(
        @SerializedName("kind") val kind: String,
        @SerializedName("document") val payload: MessagePayload) {


    private fun toNode(): List<DocumentNode> {
        if (payload.type == TextNodeType.DOCUMENT) {
            val formatted = ArrayList<DocumentNode>()
            payload.nodes.forEach {

                when {
                    it.docType == DisplayNodeType.IMAGE -> formatted.add(DocumentNode(meta = it.meta))
                    it.docType == DisplayNodeType.VIDEO -> formatted.add(DocumentNode(meta = it.meta))
                    else -> {
                        it.nodes.forEach {

                            if (it.type == TextNodeType.TEXT) {
                                it.ranges.forEach {
                                    val marks = it.marks
                                    val attr = if (marks.isEmpty()) {
                                        null
                                    } else {
                                        val mark = it.marks.first()
                                        if (mark.value == DisplayNodeType.BOLD) {
                                            TextAttribute.BOLD
                                        } else {
                                            null
                                        }
                                    }
                                    formatted.add(DocumentNode(it.text, null, attr))
                                }
                            } else if (it.type == TextNodeType.INLINE) {
                                val url = it.meta!!.url

                                it.nodes?.first()?.ranges?.forEach {
                                    formatted.add(DocumentNode(it.text, NodeMeta(url = url), null))
                                }
                            }
                        }
                    }

                }
            }
            return formatted

        } else {
            return emptyList()
        }
    }

    fun getDisplayNode(): String {
        val html = HtmlBuilder()
        toNode().forEachIndexed { index, node ->
            if (node.meta != null) {
                when {
                    node.meta.imageUrl != null -> {
                        if (index != 0) {
                            html.br().br()
                        }
                        html.img(
                                StringBuilder(Constants.PRODUCT_CDN_URL)
                                        .append("/")
                                        .append(node.meta.imageUrl)
                                        .append("?w=")
                                        .append(node.meta.width)
                                        .append("&h=")
                                        .append(node.meta.height)
                                        .append("&type=")
                                        .append(DisplayNodeType.IMAGE)
                                        .toString()

                        )
                                .br()
                    }
                    node.meta.videoId != null -> {
                        if (index != 0) {
                            html.br().br()
                        }
                        html.img(StringBuilder(ResizeImageUrlProvider.getThumbnailUrl(node.meta.videoId))
                                .append("?w=")
                                .append(480)
                                .append("&h=")
                                .append(360)
                                .append("&type=")
                                .append(DisplayNodeType.VIDEO)
                                .append("&payload=")
                                .append(node.meta.videoId)
                                .toString()
                        )
                                .br()
                    }
                    node.meta.url != null -> html.a(node.meta.url, node.content)
                }
            } else if (node.content != null) {

                when {
                    node.attr == TextAttribute.BOLD -> html.b(node.content)
                    else -> html.append(node.content)
                }
            }

        }

        return html.toString()
    }
}