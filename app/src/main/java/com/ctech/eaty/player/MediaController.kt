package com.ctech.eaty.player

import android.net.Uri
import io.reactivex.Completable


interface MediaController<View> {
    fun takeView(view: View)
    fun play(uri: Uri): Completable
    fun resume(): Completable
    fun pause(): Completable
    fun release()
}