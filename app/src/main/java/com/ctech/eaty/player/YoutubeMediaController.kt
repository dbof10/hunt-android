package com.ctech.eaty.player

import android.net.Uri
import com.ctech.eaty.util.Constants
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import io.reactivex.Completable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber


class YoutubeMediaController : MediaController<YouTubePlayerView>, YouTubePlayer.OnInitializedListener, YouTubePlayer.PlayerStateChangeListener, YouTubePlayer.PlaybackEventListener {


    private lateinit var player: YouTubePlayer
    private val initSuccessSignal = PublishSubject.create<YouTubePlayer>()

    override fun takeView(view: YouTubePlayerView) {
        view.initialize(Constants.YOUTUBE_API_KEY, this)
    }

    override fun play(uri: Uri): Completable {
        return initSuccessSignal.flatMapCompletable {
            Completable.fromAction {
                val videoId = uri.lastPathSegment
                player.loadVideo(videoId)
            }
        }
    }

    override fun resume(): Completable {
        return Completable.fromAction {
            player.play()
        }
    }

    override fun pause(): Completable {
        return Completable.fromAction {
            player.pause()
        }
    }

    override fun release() {

    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer, wasRestored: Boolean) {
        this.player = player
        player.setPlayerStateChangeListener(this)
        player.setPlaybackEventListener(this)
        initSuccessSignal.onNext(player)
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider, errorReason: YouTubeInitializationResult) {
        Timber.e(Throwable("Error init youtube $errorReason"))
    }

    override fun onAdStarted() {
    }

    override fun onLoading() {
    }

    override fun onVideoStarted() {
    }

    override fun onLoaded(p0: String?) {
    }

    override fun onVideoEnded() {
    }

    override fun onError(reason: YouTubePlayer.ErrorReason?) {
        Timber.e(Throwable("Error load video $reason"))
    }

    override fun onSeekTo(p0: Int) {
    }

    override fun onBuffering(p0: Boolean) {
    }

    override fun onPlaying() {
    }

    override fun onStopped() {
    }

    override fun onPaused() {
    }

}