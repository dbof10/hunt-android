package com.ctech.eaty.ui.collectiondetail.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseActivity
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.tracking.FirebaseTrackManager
import com.ctech.eaty.ui.collectiondetail.action.CollectionDetailAction
import com.ctech.eaty.ui.collectiondetail.state.CollectionDetailState
import com.ctech.eaty.ui.collectiondetail.viewmodel.CollectionDetailViewModel
import com.ctech.eaty.util.ResizeImageUrlProvider
import com.ctech.eaty.widget.drawable.CircleProgressBarDrawable
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.request.ImageRequest
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_collection_detail.*
import javax.inject.Inject


class CollectionDetailActivity : BaseActivity(), HasSupportFragmentInjector {

    override fun getScreenName(): String = "Collection Detail"

    @Inject
    lateinit var trackingManager: FirebaseTrackManager

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var store: Store<CollectionDetailState>

    @Inject
    lateinit var viewModel: CollectionDetailViewModel


    companion object {
        val COLLECTION_ID_KEY = "collectionId"

        fun newIntent(context: Context, id: Int): Intent {
            val intent = Intent(context, CollectionDetailActivity::class.java)
            intent.putExtra(COLLECTION_ID_KEY, id)
            return intent
        }
    }

    private val collectionId by lazy {
        intent.getIntExtra(COLLECTION_ID_KEY, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection_detail)
        setupToolbar()
        setupViewModel()

        var fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (fragment == null) {
            fragment = CollectionDetailFragment.newInstance()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit()
        }
        trackingManager.trackScreenView(getScreenName())
    }

    override fun onStart() {
        super.onStart()
        store.dispatch(CollectionDetailAction.Load(collectionId))
    }

    private fun setupViewModel() {
        viewModel.header().subscribe { renderHeader(it) }
    }

    private fun renderHeader(imageUrl: String) {
        val thumbNailSize = resources.getDimensionPixelSize(R.dimen.thumbnail_preview_size)
        val controller = Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(ImageRequest.fromUri(ResizeImageUrlProvider.overrideUrl(imageUrl, thumbNailSize)))
                .setImageRequest(ImageRequest.fromUri(imageUrl))
                .build()
        ivCollectionBackground.hierarchy.setProgressBarImage(CircleProgressBarDrawable(this))
        ivCollectionBackground.controller = controller
    }

    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener {
            finish()
        }

    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

}
