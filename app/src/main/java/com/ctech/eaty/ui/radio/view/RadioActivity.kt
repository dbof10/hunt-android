package com.ctech.eaty.ui.radio.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseActivity
import com.ctech.eaty.entity.TrackStatus
import com.ctech.eaty.tracking.FirebaseTrackManager
import com.ctech.eaty.player.MediaController
import com.ctech.eaty.ui.radio.state.MediaPlayerState
import com.ctech.eaty.ui.radio.viewmodel.RadioViewModel
import com.ctech.eaty.util.GlideImageLoader
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_radio.*
import javax.inject.Inject


class RadioActivity : BaseActivity(), HasSupportFragmentInjector {

    override fun getScreenName(): String = "Radio"

    @Inject
    lateinit var trackingManager: FirebaseTrackManager

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var imageLoader: GlideImageLoader

    @Inject
    lateinit var viewModel: RadioViewModel

    @Inject
    lateinit var radioController: MediaController<SimpleExoPlayerView>

    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, RadioActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radio)
        setupToolbar()
        var fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (fragment == null) {
            fragment = RadioFragment.newInstance()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit()
        }
        setupViewModel()
        setupMediaPlayer()
        trackingManager.trackScreenView(getScreenName())
    }

    private fun setupViewModel() {
        viewModel.header().subscribe { imageLoader.downloadInto(it, ivArtwork) }
        viewModel.mediaController().subscribe { renderMediaPlayer(it) }
    }

    private fun setupMediaPlayer() {
        ivPlay.setOnClickListener {
            viewModel.pauseOrResume()
        }
    }

    private fun renderMediaPlayer(props: MediaPlayerState) {
        llMedia.visibility = View.VISIBLE
        with(props) {
            tvTrackTitle.text = trackTitle
            ivPlay.setImageResource(if (status == TrackStatus.PLAYING) R.drawable.ic_pause_black else R.drawable.ic_play_arrow_black)
        }
    }


    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener {
            finishAfterTransition()
        }
    }

    override fun onDestroy() {
        radioController.release()

        super.onDestroy()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

}
