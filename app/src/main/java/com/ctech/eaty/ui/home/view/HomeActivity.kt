package com.ctech.eaty.ui.home.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity.START
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseActivity
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.entity.UserDetail
import com.ctech.eaty.tracking.FirebaseTrackManager
import com.ctech.eaty.ui.home.action.HomeAction
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.ui.home.viewmodel.HomeViewModel
import com.ctech.eaty.util.GlideImageLoader
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_home_content.*
import javax.inject.Inject


class HomeActivity : BaseActivity(), HasSupportFragmentInjector {

    override fun getScreenName() = "Home"

    @Inject
    lateinit var trackingManager: FirebaseTrackManager

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var imageLoader: GlideImageLoader

    @Inject
    lateinit var store: Store<HomeState>

    @Inject
    lateinit var viewModel: HomeViewModel

    private val ivHeaderAvatar: ImageView by lazy {
        navigation.getHeaderView(0)
                .findViewById<ImageView>(R.id.ivAvatar)
    }

    private val tvHeaderUserName: TextView by lazy {
        navigation.getHeaderView(0)
                .findViewById<TextView>(R.id.tvUserName)
    }

    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, HomeActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        setupViewModel()
        var fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (fragment == null) {
            fragment = HomeFragment.newInstance()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit()
        }

        trackingManager.trackScreenView(getScreenName())
    }

    private fun setupViewModel() {
        viewModel.user().subscribe { renderNavigationHeader(it) }
    }

    override fun onStart() {
        super.onStart()
        store.dispatch(HomeAction.LOAD_USER)
    }

    private fun renderNavigationHeader(user: UserDetail) {

        with(user) {
            imageLoader.downloadInto(imageUrl.px48, ivHeaderAvatar)
            tvHeaderUserName.text = username
            tvIndicator.text = notification.unseen.toString()
        }

    }

    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener {
            drawer.openDrawer(START)
        }
        navigation.getHeaderView(0)
                .findViewById<View>(R.id.llUser)
                .setOnClickListener {
                    viewModel.userNavigation(ivHeaderAvatar)
                }
        navigation.setNavigationItemSelectedListener {
            viewModel.navigationClick(it.itemId)
            false
        }
        ivNoti.setOnClickListener {
            viewModel.notificationNavigation()
        }
        ivSearch.setOnClickListener {
            viewModel.navigateSearch(it)
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    override fun onLowMemory() {
        super.onLowMemory()
        imageLoader.clearMemory()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        store.dispatch(HomeAction.CHECK_RESULT(requestCode, resultCode, data))
    }

}
