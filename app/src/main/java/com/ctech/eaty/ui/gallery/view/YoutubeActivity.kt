package com.ctech.eaty.ui.gallery.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.ctech.eaty.R
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.player.MediaController
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubePlayerView
import kotlinx.android.synthetic.main.activity_youtube.*
import timber.log.Timber
import javax.inject.Inject

class YoutubeActivity : YouTubeBaseActivity(), Injectable{

    @Inject
    lateinit var mediaController: MediaController<YouTubePlayerView>

    companion object {

        private val VIDEO_URL_KEY = "videoUrlKey"

        fun newIntent(context: Context, videoUrl: Uri): Intent {
            val intent = Intent(context, YoutubeActivity::class.java)
            intent.putExtra(VIDEO_URL_KEY, videoUrl)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)

        val videoUrl = intent.getParcelableExtra<Uri>(VIDEO_URL_KEY)
        mediaController.takeView(youtube)
        mediaController.play(videoUrl)
                .subscribe({}, Timber::e)
    }
}