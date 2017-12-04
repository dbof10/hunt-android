package com.ctech.eaty.ui.topiclist.action

import com.ctech.eaty.base.redux.Action

sealed class TopicList : Action() {

    data class Load(val id: Int) : TopicList()

    data class LoadMore(val id: Int) : TopicList()

}