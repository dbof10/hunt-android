package com.ctech.eaty.ui.upcomingdetail.view

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.support.v4.app.Fragment
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseActivity
import com.ctech.eaty.entity.LoadEvent
import com.ctech.eaty.tracking.FirebaseTrackManager
import com.ctech.eaty.ui.upcomingdetail.state.ImageLoadWatcher
import com.ctech.eaty.ui.upcomingdetail.viewmodel.UpcomingDetailViewModel
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.imagepipeline.image.ImageInfo
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.kotlin.autoDisposable
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_upcoming_detail.ivBack
import kotlinx.android.synthetic.main.activity_upcoming_detail.ivBackground
import timber.log.Timber
import javax.inject.Inject

class UpcomingProductDetailActivity : BaseActivity(), HasSupportFragmentInjector {

    override fun getScreenName(): String = "UpcomingProductDetail"

    @Inject
    lateinit var trackingManager: FirebaseTrackManager


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var customTabActivityHelper: CustomTabActivityHelper

    @Inject
    lateinit var viewModel: UpcomingDetailViewModel

    @Inject
    lateinit var imageLoadWatcher: ImageLoadWatcher

    companion object {

        private val KEY_SLUG = "slug"

        fun newIntent(context: Context, slug: String): Intent {
            val intent = Intent(context, UpcomingProductDetailActivity::class.java)
            intent.putExtra(KEY_SLUG, slug)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_detail)
        setupToolbar()
        setupViewModel()
        var fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val slug = intent.getStringExtra(KEY_SLUG)

        if (fragment == null) {
            fragment = UpcomingDetailFragment.newInstance(slug)
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commitAllowingStateLoss()
        }

        trackingManager.trackScreenView(getScreenName())
    }

    private fun setupViewModel() {
        viewModel.subscribed()
                .autoDisposable(AndroidLifecycleScopeProvider.from(this))
                .subscribe {
                    val fragment = SuccessMessageFragment.newInstance()
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, fragment)
                            .commit()
                }

        viewModel.content()
                .autoDisposable(AndroidLifecycleScopeProvider.from(this))
                .subscribe {
                    setupBackground(it.body.backgroundUrl)
                }
    }

    override fun onStart() {
        super.onStart()
        customTabActivityHelper.bindCustomTabsService(this)

    }

    override fun onStop() {
        customTabActivityHelper.unbindCustomTabsService(this)
        super.onStop()
    }

    private fun setupToolbar() {
        ivBack.setOnClickListener {
            finishAfterTransition()
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    private fun setupBackground(url: String) {
        val listener = object : BaseControllerListener<ImageInfo>() {
            override fun onFinalImageSet(id: String, imageInfo: ImageInfo?, animatable: Animatable?) {
                imageLoadWatcher += LoadEvent.SUCCESS
            }

            override fun onFailure(id: String, throwable: Throwable) {
                imageLoadWatcher += LoadEvent.ERROR
                Timber.e(throwable)
            }
        }

        val controller = Fresco.newDraweeControllerBuilder()
                .setUri(url)
                .setControllerListener(listener)
                .build()
        ivBackground.controller = controller
    }
}