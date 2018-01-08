package com.ctech.eaty.ui.home.model

import com.ctech.eaty.entity.Job
import com.ctech.eaty.ui.home.viewmodel.StickyItemViewModel

class Jobs(val sticky: StickyItemViewModel, val jobs: List<Job>) : HomeFeed {
    val label get() = sticky.label
}