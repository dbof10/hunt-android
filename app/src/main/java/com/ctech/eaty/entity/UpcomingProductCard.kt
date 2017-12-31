package com.ctech.eaty.entity

data class UpcomingProductCard(val products: List<UpcomingProduct>) : HomeCard{

    override val type: String
        get() = UPCOMING_PAGE

}