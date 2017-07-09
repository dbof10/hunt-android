package com.ctech.eaty.ui.radio.viewmodel

import com.ctech.eaty.entity.Track
import com.ctech.eaty.entity.TrackStatus

data class TrackItemViewModel(private val track: Track, private val trackStatus: TrackStatus = TrackStatus.STOPPED) {
    val id: Int get() = track.id
    val title: String get() = track.title
    val downloadable: Boolean get() = track.downloadable
    val streamable: Boolean get() = track.streamable
    val status: TrackStatus get() = trackStatus
    val imageUrl: String? get() = track.imageUrl
    val streamUrl: String get() = track.streamUrl
}