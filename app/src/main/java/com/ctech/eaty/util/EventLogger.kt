package com.ctech.eaty.util

import android.os.SystemClock
import android.util.Log
import android.view.Surface
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.audio.AudioRendererEventListener
import com.google.android.exoplayer2.decoder.DecoderCounters
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager
import com.google.android.exoplayer2.metadata.Metadata
import com.google.android.exoplayer2.metadata.MetadataRenderer
import com.google.android.exoplayer2.metadata.emsg.EventMessage
import com.google.android.exoplayer2.metadata.id3.*
import com.google.android.exoplayer2.source.AdaptiveMediaSourceEventListener
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroup
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.MappingTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.video.VideoRendererEventListener
import java.io.IOException
import java.text.NumberFormat
import java.util.*
import com.google.android.exoplayer2.Player
import timber.log.Timber


class EventLogger(private val trackSelector: MappingTrackSelector) : Player.EventListener,
        AudioRendererEventListener, VideoRendererEventListener, AdaptiveMediaSourceEventListener,
        ExtractorMediaSource.EventListener, DefaultDrmSessionManager.EventListener,
        MetadataRenderer.Output {

    override fun onAudioSinkUnderrun(bufferSize: Int, bufferSizeMs: Long, elapsedSinceLastFeedMs: Long) {
        printInternalError("audioTrackUnderrun [" + bufferSize + ", " + bufferSizeMs + ", "
                + elapsedSinceLastFeedMs + "]", null);    }

    private fun getDiscontinuityReasonString(@Player.DiscontinuityReason reason: Int): String {
        return when (reason) {
            Player.DISCONTINUITY_REASON_PERIOD_TRANSITION -> "PERIOD_TRANSITION"
            Player.DISCONTINUITY_REASON_SEEK -> "SEEK"
            Player.DISCONTINUITY_REASON_SEEK_ADJUSTMENT -> "SEEK_ADJUSTMENT"
            Player.DISCONTINUITY_REASON_INTERNAL -> "INTERNAL"
            else -> "?"
        }
    }

    override fun onPositionDiscontinuity(reason: Int) {
       Timber.d("positionDiscontinuity [%s]", getDiscontinuityReasonString(reason))
    }

    override fun onSeekProcessed() {
        Timber.d("seekProcessed")
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
        Log.d(TAG, "shuffleModeEnabled [" + shuffleModeEnabled + "]");
    }

    private val TAG = "EventLogger"
    private val MAX_TIMELINE_ITEM_LINES = 3
    private val TIME_FORMAT = NumberFormat.getInstance(Locale.US)


    private var window: Timeline.Window = Timeline.Window()
    private var period: Timeline.Period = Timeline.Period()
    private var startTimeMs: Long = SystemClock.elapsedRealtime()

    init {
        TIME_FORMAT.minimumFractionDigits = 2
        TIME_FORMAT.maximumFractionDigits = 2
        TIME_FORMAT.isGroupingUsed = false
    }


    // ExoPlayer.EventListener

    override fun onRepeatModeChanged(repeatMode: Int) {
        Log.d(TAG, "repeating [$repeatMode]")
    }

    override fun onLoadingChanged(isLoading: Boolean) {
        Log.d(TAG, "loading [$isLoading]")
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, state: Int) {
        Log.d(TAG, "state [" + getSessionTimeString() + ", " + playWhenReady + ", "
                + getStateString(state) + "]")
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
        Log.d(TAG, "playbackParameters " + String.format(
                "[speed=%.2f, pitch=%.2f]", playbackParameters.speed, playbackParameters.pitch))
    }

    override fun onTimelineChanged(timeline: Timeline, manifest: Any) {
        val periodCount = timeline.periodCount
        val windowCount = timeline.windowCount
        Log.d(TAG, "sourceInfo [periodCount=$periodCount, windowCount=$windowCount")
        for (i in 0 until Math.min(periodCount, MAX_TIMELINE_ITEM_LINES)) {
            timeline.getPeriod(i, period)
            Log.d(TAG, "  " + "period [" + getTimeString(period.durationMs) + "]")
        }
        if (periodCount > MAX_TIMELINE_ITEM_LINES) {
            Log.d(TAG, "  ...")
        }
        for (i in 0 until Math.min(windowCount, MAX_TIMELINE_ITEM_LINES)) {
            timeline.getWindow(i, window)
            Log.d(TAG, "  " + "window [" + getTimeString(window.durationMs) + ", "
                    + window.isSeekable + ", " + window.isDynamic + "]")
        }
        if (windowCount > MAX_TIMELINE_ITEM_LINES) {
            Log.d(TAG, "  ...")
        }
        Log.d(TAG, "]")
    }

    override fun onPlayerError(e: ExoPlaybackException) {
        Log.e(TAG, "playerFailed [" + getSessionTimeString() + "]", e)
    }

    override fun onTracksChanged(ignored: TrackGroupArray, trackSelections: TrackSelectionArray) {
        val mappedTrackInfo = trackSelector.currentMappedTrackInfo
        if (mappedTrackInfo == null) {
            Timber.d("Tracks []")
            return
        }
        Timber.d("Tracks [")
        // Log tracks associated to renderers.
        for (rendererIndex in 0..mappedTrackInfo.length - 1) {
            val rendererTrackGroups = mappedTrackInfo.getTrackGroups(rendererIndex)
            val trackSelection = trackSelections.get(rendererIndex)
            if (rendererTrackGroups.length > 0) {
                Log.d(TAG, "  Renderer:$rendererIndex [")
                for (groupIndex in 0 until rendererTrackGroups.length) {
                    val trackGroup = rendererTrackGroups.get(groupIndex)
                    val adaptiveSupport = getAdaptiveSupportString(trackGroup.length,
                            mappedTrackInfo.getAdaptiveSupport(rendererIndex, groupIndex, false))
                    Log.d(TAG, "    Group:$groupIndex, adaptive_supported=$adaptiveSupport [")
                    for (trackIndex in 0 until trackGroup.length) {
                        val status = getTrackStatusString(trackSelection, trackGroup, trackIndex)
                        val formatSupport = getFormatSupportString(
                                mappedTrackInfo.getTrackFormatSupport(rendererIndex, groupIndex, trackIndex))
                        Log.d(TAG, "      " + status + " Track:" + trackIndex + ", "
                                + Format.toLogString(trackGroup.getFormat(trackIndex))
                                + ", supported=" + formatSupport)
                    }
                    Timber.d("    ]")
                }
                // Log metadata for at most one of the tracks selected for the renderer.
                if (trackSelection != null) {
                    for (selectionIndex in 0..trackSelection.length() - 1) {
                        val metadata = trackSelection.getFormat(selectionIndex).metadata
                        if (metadata != null) {
                            Timber.d("    Metadata [")
                            printMetadata(metadata, "      ")
                            Timber.d("    ]")
                            break
                        }
                    }
                }
                Timber.d("  ]")
            }
        }
        // Log tracks not associated with a renderer.
        val unassociatedTrackGroups = mappedTrackInfo.unassociatedTrackGroups
        if (unassociatedTrackGroups.length > 0) {
            Timber.d("  Renderer:None [")
            for (groupIndex in 0..unassociatedTrackGroups.length - 1) {
                Log.d(TAG, "    Group:$groupIndex [")
                val trackGroup = unassociatedTrackGroups.get(groupIndex)
                for (trackIndex in 0..trackGroup.length - 1) {
                    val status = getTrackStatusString(false)
                    val formatSupport = getFormatSupportString(
                            RendererCapabilities.FORMAT_UNSUPPORTED_TYPE)
                    Log.d(TAG, "      " + status + " Track:" + trackIndex + ", "
                            + Format.toLogString(trackGroup.getFormat(trackIndex))
                            + ", supported=" + formatSupport)
                }
                Timber.d("    ]")
            }
            Timber.d("  ]")
        }
        Timber.d("]")
    }

    // MetadataRenderer.Output

    override fun onMetadata(metadata: Metadata) {
        Timber.d("onMetadata [")
        printMetadata(metadata, "  ")
        Timber.d("]")
    }

    // AudioRendererEventListener

    override fun onAudioEnabled(counters: DecoderCounters) {
        Log.d(TAG, "audioEnabled [" + getSessionTimeString() + "]")
    }

    override fun onAudioSessionId(audioSessionId: Int) {
        Log.d(TAG, "audioSessionId [$audioSessionId]")
    }

    override fun onAudioDecoderInitialized(decoderName: String, elapsedRealtimeMs: Long,
                                           initializationDurationMs: Long) {
        Log.d(TAG, "audioDecoderInitialized [" + getSessionTimeString() + ", " + decoderName + "]")
    }

    override fun onAudioInputFormatChanged(format: Format) {
        Log.d(TAG, "audioFormatChanged [" + getSessionTimeString() + ", " + Format.toLogString(format)
                + "]")
    }

    override fun onAudioDisabled(counters: DecoderCounters) {
        Log.d(TAG, "audioDisabled [" + getSessionTimeString() + "]")
    }

    // VideoRendererEventListener

    override fun onVideoEnabled(counters: DecoderCounters) {
        Log.d(TAG, "videoEnabled [" + getSessionTimeString() + "]")
    }

    override fun onVideoDecoderInitialized(decoderName: String, elapsedRealtimeMs: Long,
                                           initializationDurationMs: Long) {
        Log.d(TAG, "videoDecoderInitialized [" + getSessionTimeString() + ", " + decoderName + "]")
    }

    override fun onVideoInputFormatChanged(format: Format) {
        Log.d(TAG, "videoFormatChanged [" + getSessionTimeString() + ", " + Format.toLogString(format)
                + "]")
    }

    override fun onVideoDisabled(counters: DecoderCounters) {
        Log.d(TAG, "videoDisabled [" + getSessionTimeString() + "]")
    }

    override fun onDroppedFrames(count: Int, elapsed: Long) {
        Log.d(TAG, "droppedFrames [" + getSessionTimeString() + ", " + count + "]")
    }

    override fun onVideoSizeChanged(width: Int, height: Int, unappliedRotationDegrees: Int,
                                    pixelWidthHeightRatio: Float) {
        // Do nothing.
    }

    override fun onRenderedFirstFrame(surface: Surface) {
        Log.d(TAG, "renderedFirstFrame [$surface]")
    }

    // DefaultDrmSessionManager.EventListener

    override fun onDrmSessionManagerError(e: Exception) {
        printInternalError("drmSessionManagerError", e)
    }

    override fun onDrmKeysRestored() {
        Log.d(TAG, "drmKeysRestored [" + getSessionTimeString() + "]")
    }

    override fun onDrmKeysRemoved() {
        Log.d(TAG, "drmKeysRemoved [" + getSessionTimeString() + "]")
    }

    override fun onDrmKeysLoaded() {
        Log.d(TAG, "drmKeysLoaded [" + getSessionTimeString() + "]")
    }

    // ExtractorMediaSource.EventListener

    override fun onLoadError(error: IOException) {
        printInternalError("loadError", error)
    }

    // AdaptiveMediaSourceEventListener

    override fun onLoadStarted(dataSpec: DataSpec, dataType: Int, trackType: Int, trackFormat: Format,
                               trackSelectionReason: Int, trackSelectionData: Any, mediaStartTimeMs: Long,
                               mediaEndTimeMs: Long, elapsedRealtimeMs: Long) {
        // Do nothing.
    }

    override fun onLoadError(dataSpec: DataSpec, dataType: Int, trackType: Int, trackFormat: Format,
                             trackSelectionReason: Int, trackSelectionData: Any, mediaStartTimeMs: Long,
                             mediaEndTimeMs: Long, elapsedRealtimeMs: Long, loadDurationMs: Long, bytesLoaded: Long,
                             error: IOException, wasCanceled: Boolean) {
        printInternalError("loadError", error)
    }

    override fun onLoadCanceled(dataSpec: DataSpec, dataType: Int, trackType: Int, trackFormat: Format,
                                trackSelectionReason: Int, trackSelectionData: Any, mediaStartTimeMs: Long,
                                mediaEndTimeMs: Long, elapsedRealtimeMs: Long, loadDurationMs: Long, bytesLoaded: Long) {
        // Do nothing.
    }

    override fun onLoadCompleted(dataSpec: DataSpec, dataType: Int, trackType: Int, trackFormat: Format,
                                 trackSelectionReason: Int, trackSelectionData: Any, mediaStartTimeMs: Long,
                                 mediaEndTimeMs: Long, elapsedRealtimeMs: Long, loadDurationMs: Long, bytesLoaded: Long) {
        // Do nothing.
    }

    override fun onUpstreamDiscarded(trackType: Int, mediaStartTimeMs: Long, mediaEndTimeMs: Long) {
        // Do nothing.
    }

    override fun onDownstreamFormatChanged(trackType: Int, trackFormat: Format, trackSelectionReason: Int,
                                           trackSelectionData: Any, mediaTimeMs: Long) {
        // Do nothing.
    }

    // Internal methods

    private fun printInternalError(type: String, e: Exception?) {
        Log.e(TAG, "internalError [" + getSessionTimeString() + ", " + type + "]", e)
    }

    private fun printMetadata(metadata: Metadata, prefix: String) {
        (0 until metadata.length())
                .map { metadata.get(it) }
                .forEach {
                    when (it) {
                        is TextInformationFrame -> {
                            val textInformationFrame = it
                            Log.d(TAG, prefix + String.format("%s: value=%s", textInformationFrame.id,
                                    textInformationFrame.value))
                        }
                        is UrlLinkFrame -> {
                            val urlLinkFrame = it
                            Log.d(TAG, prefix + String.format("%s: url=%s", urlLinkFrame.id, urlLinkFrame.url))
                        }
                        is PrivFrame -> {
                            val privFrame = it
                            Log.d(TAG, prefix + String.format("%s: owner=%s", privFrame.id, privFrame.owner))
                        }
                        is GeobFrame -> {
                            val geobFrame = it
                            Log.d(TAG, prefix + String.format("%s: mimeType=%s, filename=%s, description=%s",
                                    geobFrame.id, geobFrame.mimeType, geobFrame.filename, geobFrame.description))
                        }
                        is ApicFrame -> {
                            val apicFrame = it
                            Log.d(TAG, prefix + String.format("%s: mimeType=%s, description=%s",
                                    apicFrame.id, apicFrame.mimeType, apicFrame.description))
                        }
                        is CommentFrame -> {
                            val commentFrame = it
                            Log.d(TAG, prefix + String.format("%s: language=%s, description=%s", commentFrame.id,
                                    commentFrame.language, commentFrame.description))
                        }
                        is Id3Frame -> {
                            val id3Frame = it
                            Log.d(TAG, prefix + String.format("%s", id3Frame.id))
                        }
                        is EventMessage -> {
                            val eventMessage = it
                            Log.d(TAG, prefix + String.format("EMSG: scheme=%s, id=%d, value=%s",
                                    eventMessage.schemeIdUri, eventMessage.id, eventMessage.value))
                        }
                    }
                }
    }

    private fun getSessionTimeString(): String {
        return getTimeString(SystemClock.elapsedRealtime() - startTimeMs)
    }

    private fun getTimeString(timeMs: Long): String {
        return if (timeMs == C.TIME_UNSET) "?" else TIME_FORMAT.format(timeMs / 1000f)
    }

    private fun getStateString(state: Int): String {
        return when (state) {
            Player.STATE_BUFFERING -> "B"
            Player.STATE_ENDED -> "E"
            Player.STATE_IDLE -> "I"
            Player.STATE_READY -> "R"
            else -> "?"
        }
    }

    private fun getFormatSupportString(formatSupport: Int): String {
        return when (formatSupport) {
            RendererCapabilities.FORMAT_HANDLED -> "YES"
            RendererCapabilities.FORMAT_EXCEEDS_CAPABILITIES -> "NO_EXCEEDS_CAPABILITIES"
            RendererCapabilities.FORMAT_UNSUPPORTED_SUBTYPE -> "NO_UNSUPPORTED_TYPE"
            RendererCapabilities.FORMAT_UNSUPPORTED_TYPE -> "NO"
            else -> "?"
        }
    }

    private fun getAdaptiveSupportString(trackCount: Int, adaptiveSupport: Int): String {
        if (trackCount < 2) {
            return "N/A"
        }
        return when (adaptiveSupport) {
            RendererCapabilities.ADAPTIVE_SEAMLESS -> "YES"
            RendererCapabilities.ADAPTIVE_NOT_SEAMLESS -> "YES_NOT_SEAMLESS"
            RendererCapabilities.ADAPTIVE_NOT_SUPPORTED -> "NO"
            else -> "?"
        }
    }

    private fun getTrackStatusString(selection: TrackSelection?, group: TrackGroup,
                                     trackIndex: Int): String {
        return getTrackStatusString(selection != null && selection.trackGroup === group
                && selection.indexOf(trackIndex) != C.INDEX_UNSET)
    }

    private fun getTrackStatusString(enabled: Boolean): String {
        return if (enabled) "[X]" else "[ ]"
    }
}