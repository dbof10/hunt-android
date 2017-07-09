package com.ctech.eaty.ui.radio.state

import com.ctech.eaty.entity.TrackStatus

data class MediaPlayerState(private val trackStatus: TrackStatus, private val title: String) {
    val status: TrackStatus get() = trackStatus
    val trackTitle: String get() = title
}