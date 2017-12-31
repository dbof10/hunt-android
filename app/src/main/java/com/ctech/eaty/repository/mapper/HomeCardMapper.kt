package com.ctech.eaty.repository.mapper

import com.ctech.eaty.HomeCardsQuery
import com.ctech.eaty.entity.HomeCard
import com.ctech.eaty.entity.HomeCardType
import com.ctech.eaty.entity.NEW_POSTS
import com.ctech.eaty.entity.NewPostCard
import com.ctech.eaty.entity.SUGGESTED_PRODUCTS
import com.ctech.eaty.entity.SUGGESTED_TOPIC
import com.ctech.eaty.entity.Topic
import com.ctech.eaty.entity.TopicCard
import com.ctech.eaty.entity.TopicsCard
import com.ctech.eaty.entity.UPCOMING_PAGE
import com.ctech.eaty.entity.UpcomingProductCard
import com.ctech.eaty.util.Constants
import javax.inject.Inject

class HomeCardMapper @Inject constructor(private val productMapper: ProductMapper) {

    fun toCard(@HomeCardType type: String, data: HomeCardsQuery.Data): HomeCard {
        val edges = data.cards()!!.edges()!!

        val handledNodes = ArrayList<HomeCard>()
        edges.forEach {
            when {
                it.node()!!.__typename() == UPCOMING_PAGE -> handledNodes.add(toUpcomingProducts(it!!))
                it.node()!!.__typename() == NEW_POSTS -> handledNodes.add(toNewPosts(it!!))
                it.node()!!.__typename() == SUGGESTED_TOPIC -> handledNodes.add(toTopics(it!!))
                it.node()!!.__typename() == SUGGESTED_PRODUCTS -> handledNodes.add(toSuggestedProducts(it!!))
            }
        }

        return handledNodes.first {
            it.type == type
        }
    }

    private fun toSuggestedProducts(edge: HomeCardsQuery.Edge): TopicCard {
        val card = edge.node()!!.fragments().feedCards().fragments().topicCard()!!
        return TopicCard(card.topic()!!.posts()!!.edges()!!.map {
            productMapper.toProduct(it.node()!!)
        }
        )
    }

    private fun toTopics(edge: HomeCardsQuery.Edge): TopicsCard {
        val card = edge.node()!!.fragments().feedCards().fragments().topicsCard()!!

        return TopicsCard(
                card.topics()!!.map {
                    val imageUrl = StringBuilder(Constants.PRODUCT_CDN_URL)
                            .append("/")
                            .append(it.fragments().topicImage().image_uuid())
                    Topic(it.id().toInt(), it.name(), imageUrl.toString())
                }
        )
    }

    private fun toNewPosts(edge: HomeCardsQuery.Edge): NewPostCard {
        val card = edge.node()!!.fragments().feedCards().fragments().newPostsCard()!!
        return NewPostCard(card.posts()!!.map {
            val product = it.fragments().postItemList().fragments().postItem()
            productMapper.toProduct(product!!)
        })
    }

    private fun toUpcomingProducts(edge: HomeCardsQuery.Edge): UpcomingProductCard {
        val card = edge.node()!!.fragments().feedCards().fragments().feedUpcomingPagesCard()!!
        return UpcomingProductCard(
                card.upcoming_pages()!!.map {
                    productMapper.toUpcomingProduct(it.fragments().upcomingPageItem())
                }
        )
    }


}