package com.ctech.eaty.ui.upcomingdetail.state

import com.ctech.eaty.entity.UpcomingDetail


data class UpcomingProductState(val loading: Boolean = false,
                                val loadError: Throwable? = null,
                                val subscribing: Boolean = false,
                                val subscribed: Boolean = false,
                                val subscribeError: Throwable? = null,
                                val content: UpcomingDetail? = null
                                )
