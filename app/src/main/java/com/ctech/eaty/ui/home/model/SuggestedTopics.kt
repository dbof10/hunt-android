package com.ctech.eaty.ui.home.model

import com.ctech.eaty.entity.Topic
import com.ctech.eaty.ui.home.viewmodel.StickyItemViewModel

data class SuggestedTopics(val sticky: StickyItemViewModel,
                           val topics: List<Topic>) : HomeFeed {

    val label get() = sticky.label
}