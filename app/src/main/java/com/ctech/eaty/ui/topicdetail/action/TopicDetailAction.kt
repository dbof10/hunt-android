package com.ctech.eaty.ui.topicdetail.action

import com.ctech.eaty.base.redux.Action

sealed class TopicDetailAction : Action() {

    data class Load(val id: Int) : TopicDetailAction()

    data class LoadMore(val id: Int) : TopicDetailAction()

}