package com.ctech.eaty.ui.radio.controller

import android.net.Uri
import android.os.Handler
import com.ctech.eaty.ui.radio.view.RadioActivity
import com.ctech.eaty.util.EventLogger
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.util.Util
import io.reactivex.Completable


class RadioController(private val context: RadioActivity) {

    private var player: SimpleExoPlayer
    private val bandwidthMeter = DefaultBandwidthMeter()
    private val mediaDataSourceFactory: DataSource.Factory = buildDataSourceFactory(bandwidthMeter)
    private val mainHandler: Handler
    private val eventLogger: EventLogger
    private var currentTrackUri = Uri.EMPTY

    init {
        val trackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector = DefaultTrackSelector(trackSelectionFactory)
        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
        mainHandler = Handler()
        eventLogger = EventLogger(trackSelector)
    }

    fun play(uri: Uri): Completable {
        return Completable.fromAction {
            if (currentTrackUri == uri) {
                player.playWhenReady = !player.playWhenReady
            } else {
                val source = buildMediaSource(uri)
                player.run {
                    stop()
                    playWhenReady = true
                    prepare(source)
                }
            }
            currentTrackUri = uri
        }
    }

    fun resume(): Completable {
        return Completable.fromAction {
            player.playWhenReady = true
        }
    }
    
    fun pause(): Completable {
        return Completable.fromAction {
            player.playWhenReady = false
        }
    }

    fun release() {
        player.release()
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val type = Util.inferContentType(uri)
        when (type) {
            C.TYPE_SS -> return SsMediaSource(uri, buildDataSourceFactory(bandwidthMeter),
                    DefaultSsChunkSource.Factory(mediaDataSourceFactory), mainHandler, eventLogger)

            C.TYPE_DASH -> return DashMediaSource(uri, buildDataSourceFactory(bandwidthMeter),
                    DefaultDashChunkSource.Factory(mediaDataSourceFactory), mainHandler, eventLogger)

            C.TYPE_HLS -> return HlsMediaSource(uri, mediaDataSourceFactory, mainHandler, eventLogger)

            C.TYPE_OTHER -> return ExtractorMediaSource(uri, mediaDataSourceFactory, DefaultExtractorsFactory(), mainHandler, eventLogger)

            else -> {
                throw IllegalStateException("Unsupported type: " + type)
            }
        }
    }

    fun buildDataSourceFactory(bandwidthMeter: DefaultBandwidthMeter): DataSource.Factory {
        return DefaultDataSourceFactory(context, bandwidthMeter,
                buildHttpDataSourceFactory(bandwidthMeter))
    }

    fun buildHttpDataSourceFactory(bandwidthMeter: DefaultBandwidthMeter): HttpDataSource.Factory {
        return DefaultHttpDataSourceFactory(Util.getUserAgent(context, "EatyRadio"), bandwidthMeter)
    }
}