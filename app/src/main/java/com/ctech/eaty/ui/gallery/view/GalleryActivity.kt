package com.ctech.eaty.ui.gallery.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseActivity
import com.ctech.eaty.entity.Media
import com.ctech.eaty.tracking.FirebaseTrackManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_gallery.*
import javax.inject.Inject

class GalleryActivity : BaseActivity(), HasSupportFragmentInjector {

    override fun getScreenName() = "Gallery"

    @Inject
    lateinit var trackingManager: FirebaseTrackManager

    companion object {

        private val MEDIA_KEY = "mediaKey"

        fun newIntent(context: Context, media: ArrayList<Media>): Intent {
            val intent = Intent(context, GalleryActivity::class.java)
            intent.putParcelableArrayListExtra(MEDIA_KEY, media)
            return intent
        }
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        var fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (fragment == null) {
            fragment = GalleryFragment.newInstance(intent.getParcelableArrayListExtra(MEDIA_KEY))
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit()
        }
        trackingManager.trackScreenView(getScreenName())
        setupToolbar()
    }

    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener {
            finish()

        }
    }


}