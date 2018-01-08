package com.ctech.eaty.repository.mapper

import com.ctech.eaty.UpcomingShowPageQuery
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.entity.ImageUrl
import com.ctech.eaty.entity.Product
import com.ctech.eaty.entity.ThumbNail
import com.ctech.eaty.entity.UpcomingBody
import com.ctech.eaty.entity.UpcomingBodyMessage
import com.ctech.eaty.entity.UpcomingDetail
import com.ctech.eaty.entity.UpcomingProduct
import com.ctech.eaty.entity.User
import com.ctech.eaty.fragment.CollectionFeedCard
import com.ctech.eaty.fragment.PostItem
import com.ctech.eaty.fragment.TopicCard
import com.ctech.eaty.fragment.UpcomingPageItem
import com.ctech.eaty.fragment.UpcomingShowPageUser
import com.ctech.eaty.fragment.UserImageLink
import com.ctech.eaty.fragment.UserSpotlight
import com.ctech.eaty.util.Constants
import com.google.gson.Gson
import javax.inject.Inject

@ActivityScope
class ProductMapper @Inject constructor(private val gson: Gson) {


    private fun toUpcomingProducts(item: UpcomingShowPageQuery.UpcomingPages): List<UpcomingProduct> {
        val nodes = item.edges()!!.map { it.node()!! }

        return nodes.map {
            val payload = it.fragments().upcomingPageCard()

            UpcomingProduct(payload.id(), payload.name(),
                    payload.tagline() ?: "",
                    payload.background_image_uuid() ?: "",
                    payload.logo_uuid() ?: "")
        }
    }

    fun toUpcomingProduct(item: UpcomingPageItem): UpcomingProduct {
        return UpcomingProduct(item.id(), item.name(), item.tagline() ?: "", item.background_image_uuid() ?: "",
                item.logo_uuid() ?: "", item.subscriber_count().toInt(), item.popular_subscribers()
                .map {
                    val userPayload = it.fragments().userSpotlight()
                    val imageUrl = it.fragments().userSpotlight().fragments().userImageLink()
                    toUser(userPayload, imageUrl!!)
                })
    }

    fun toUpcomingDetail(item: UpcomingShowPageQuery.Data): UpcomingDetail {
        val fragment = item.upcomingPage()!!.fragments().upcomingShowPageContent()

        val upcomings = toUpcomingProducts(item.upcomingPages()!!)

        val variant = fragment.variant()


        val whoText = gson.fromJson<UpcomingBodyMessage>(variant.who_text(), UpcomingBodyMessage::class.java)
        val whatText = gson.fromJson<UpcomingBodyMessage>(variant.what_text(), UpcomingBodyMessage::class.java)
        val whyText = gson.fromJson<UpcomingBodyMessage>(variant.why_text(), UpcomingBodyMessage::class.java)
        val successMessage = gson.fromJson<UpcomingBodyMessage>(fragment.success_text(), UpcomingBodyMessage::class.java)

        val body = UpcomingBody(Constants.PRODUCT_CDN_URL + "/" + variant.background_image_uuid(), Constants.PRODUCT_CDN_URL + "/" + variant.logo_uuid()!!,
                variant.kind(),
                variant.brand_color(), whoText, whatText, whyText)

        val user = fragment.user().fragments().upcomingShowPageUser()
        return UpcomingDetail(fragment.id(), fragment.name(),
                fragment.facebook_url(),
                fragment.twitter_url(),
                fragment.angellist_url(),
                body,
                fragment.popular_subscribers().map {
                    toUser(it.fragments().userImageLink())
                }, upcomings, successMessage,
                toUser(user, user.fragments().userImageLink()!!),
                fragment.subscriber_count().toInt())
    }


    private fun toUser(payload: UpcomingShowPageUser, imageUrl: UserImageLink): User {
        return User(imageUrl.id().toInt(), payload.name(), payload.headline(), imageUrl.username(),
                ImageUrl(px64 = Constants.USER_CDN_URL + "/" + imageUrl.id() + "/original"))
    }


    private fun toUser(payload: UserSpotlight, imageUrl: UserImageLink): User {
        return User(payload.id().toInt(), imageUrl.name(), payload.headline(), imageUrl.username(),
                ImageUrl(px64 = Constants.USER_CDN_URL + "/" + imageUrl.id() + "/original"))
    }

    private fun toUser(payload: UserImageLink): User {
        return User(payload.id().toInt(), payload.name(), "", payload.username(),
                ImageUrl(px64 = Constants.USER_CDN_URL + "/" + payload.id() + "/original"))
    }

    fun toProduct(node: PostItem): Product {
        return with(node) {
            val voteCount = fragments().postVoteButton()!!.votes_count().toInt()
            val rawThumbnail = fragments().postThumbnail()!!.thumbnail()!!.fragments().mediaThumbnail()
            val thumbnail = ThumbNail(id = rawThumbnail.id().toInt(), imageUUID = rawThumbnail.image_uuid())
            Product(id = id().toInt(), name = name(), tagline = tagline(),
                    commentsCount = comments_count().toInt(),
                    votesCount = voteCount,
                    thumbnail = thumbnail)
        }
    }

    fun toProduct(node: TopicCard.Node): Product {

        return with(node) {
            val rawThumbnail = node.thumbnail()!!
            val thumbnail = ThumbNail(id = rawThumbnail.id().toInt(), imageUUID = rawThumbnail.image_uuid())
            Product(id = id().toInt(), name = name(), tagline = tagline(),
                    thumbnail = thumbnail)
        }
    }


    fun toProduct(node: CollectionFeedCard.Post): Product {
        return with(node) {
            val rawThumbnail = node.thumbnail()!!
            val thumbnail = com.ctech.eaty.entity.ThumbNail(id = rawThumbnail.id().toInt(), imageUUID = rawThumbnail.image_uuid())
            Product(id = id().toInt(), name = name(), tagline = tagline(),
                    thumbnail = thumbnail)
        }
    }
}