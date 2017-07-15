package com.ctech.eaty.ui.radio.viewmodel

import android.net.Uri
import com.ctech.eaty.entity.TrackStatus
import com.ctech.eaty.response.RadioResponse
import com.ctech.eaty.player.MediaController
import com.ctech.eaty.ui.radio.state.MediaPlayerState
import com.ctech.eaty.ui.radio.state.RadioState
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

class RadioViewModel(private val stateDispatcher: Observable<RadioState>, private val radioController: MediaController<SimpleExoPlayerView>) {

    private var body: List<TrackItemViewModel> = emptyList()
    private val bodySubject: PublishSubject<List<TrackItemViewModel>> = PublishSubject.create()

    fun loading(): Observable<RadioState> {
        return stateDispatcher
                .filter { it.loading && it.content == RadioResponse.EMPTY }
    }

    fun loadError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.loadError != null && !it.loading }
                .map { it.loadError!! }
    }

    fun content(): Observable<RadioResponse> {
        return stateDispatcher
                .filter {
                    !it.loading && it.loadError == null
                }
                .flatMap {
                    if (it.content == null)
                        Observable.just(RadioResponse.EMPTY)
                    else
                        Observable.just(it.content)
                }


    }

    fun trackSelection(): Observable<List<TrackItemViewModel>> = bodySubject

    fun mediaController(): Observable<MediaPlayerState> {
        return trackSelection()
                .map {
                    it
                            .filter {
                                it.status == TrackStatus.PAUSED || it.status == TrackStatus.PLAYING
                            }
                            .first()
                }
                .map { MediaPlayerState(it.status, it.title) }
    }


    fun body(): Observable<List<TrackItemViewModel>> {
        return content()
                .filter {
                    it != RadioResponse.EMPTY
                }
                .map {
                    body = it.tracks.map { TrackItemViewModel(it) }
                    body
                }
    }

    fun header(): Observable<String> {
        return content()
                .map {
                    it.imageUrl.replace("large", "t500x500")
                }
    }

    fun selectTrackAt(position: Int) {
        body = body.mapIndexed { index, viewModel ->
            var newViewModel = viewModel
            if (index == position) {
                val currentStatus = viewModel.status
                if (currentStatus == TrackStatus.PLAYING) {
                    radioController.pause()
                            .subscribe({ newViewModel = viewModel.copy(trackStatus = TrackStatus.PAUSED) }, Timber::e)
                } else {
                    radioController.play(Uri.parse(viewModel.streamUrl)
                            .buildUpon()
                            .appendQueryParameter("client_id", "2t9loNQH90kzJcsFCODdigxfp325aq4z")
                            .build())
                            .subscribe({ newViewModel = viewModel.copy(trackStatus = TrackStatus.PLAYING) }, Timber::e)
                }
            } else {
                newViewModel = viewModel.copy(trackStatus = TrackStatus.STOPPED)
            }
            newViewModel

        }
        bodySubject.onNext(body)
    }

    fun pauseOrResume() {

        body = body.mapIndexed { _, viewModel ->
            var newViewModel = viewModel
            val currentStatus = viewModel.status
            if (currentStatus == TrackStatus.PLAYING) {
                radioController.pause()
                        .subscribe({ newViewModel = viewModel.copy(trackStatus = TrackStatus.PAUSED) }, Timber::e)
            } else if (currentStatus == TrackStatus.PAUSED) {
                radioController.resume()
                        .subscribe({ newViewModel = viewModel.copy(trackStatus = TrackStatus.PLAYING) }, Timber::e)
            }
            newViewModel

        }
        bodySubject.onNext(body)
    }


}