package com.ctech.eaty.ui.user.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.SharedElementCallback
import android.view.View
import android.view.ViewGroup
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseActivity
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.entity.User
import com.ctech.eaty.entity.UserDetail
import com.ctech.eaty.ui.user.action.UserAction
import com.ctech.eaty.ui.user.state.UserDetailState
import com.ctech.eaty.ui.user.viewmodel.FollowButtonViewModel
import com.ctech.eaty.ui.user.viewmodel.UserDetailViewModel
import com.ctech.eaty.util.GlideImageLoader
import com.ctech.eaty.util.setPaddingBottom
import com.ctech.eaty.util.setPaddingTop
import com.ctech.eaty.widget.ElasticDragDismissFrameLayout
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_user.*
import timber.log.Timber
import javax.inject.Inject


class UserActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var imageLoader: GlideImageLoader

    @Inject
    lateinit var viewModel: UserDetailViewModel

    @Inject
    lateinit var store: Store<UserDetailState>

    private lateinit var chromeFader: ElasticDragDismissFrameLayout.SystemChromeFader

    private val user: User by lazy {
        if (intent.hasExtra(USER_KEY))
            intent.getParcelableExtra<User>(USER_KEY)
        else
            intent.getParcelableExtra<UserDetail>(USER_DETAIL_KEY)
    }

    override fun getScreenName() = "User"

    companion object {

        private val USER_DETAIL_KEY = "userDetail"
        private val USER_KEY = "user"

        fun newIntent(context: Context, user: UserDetail): Intent {
            val intent = Intent(context, UserActivity::class.java)
            intent.putExtra(USER_DETAIL_KEY, user)
            return intent
        }


        fun newIntent(context: Context, user: User): Intent {
            val intent = Intent(context, UserActivity::class.java)
            intent.putExtra(USER_KEY, user)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        setupViewModel()
        setupBodyFragment()
        setupListener()
        preRenderHeader()
        chromeFader = ElasticDragDismissFrameLayout.SystemChromeFader(this)
        flDraggable.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        flDraggable.setOnApplyWindowInsetsListener { _, insets ->
            val lpFrame = flDraggable.layoutParams as ViewGroup.MarginLayoutParams
            lpFrame.leftMargin += insets.systemWindowInsetLeft    // landscape
            lpFrame.rightMargin += insets.systemWindowInsetRight  // landscape
            (ivAvatar.layoutParams as ViewGroup.MarginLayoutParams).topMargin += insets.systemWindowInsetTop
            container.setPaddingTop(insets.systemWindowInsetTop)
            fBody.setPaddingBottom(insets.systemWindowInsetBottom)
            // clear this listener so insets aren't re-applied
            flDraggable.setOnApplyWindowInsetsListener(null)
            insets
        }
        setExitSharedElementCallback(createSharedElementReenterCallback(this))
    }

    override fun onStart() {
        super.onStart()
        viewModel.checkRelationship().subscribe({
            renderFollowing(it)
        }, Timber::e, {
            store.dispatch(UserAction.LoadRelationship(user.id))
        })
    }

    private fun setupListener() {
        btFollow.setOnClickListener {
             viewModel.followNavigation(btFollow).subscribe()
        }
    }

    private fun preRenderHeader() {
        with(user) {
            imageLoader.downloadInto(imageUrl.px64, ivAvatar)
            tvName.text = name
            tvBio.text = headline
        }
    }

    private fun setupViewModel() {
        viewModel.header().subscribe { renderHeader(it) }
        viewModel.loadingRelationship().subscribe {
            pbFollowing.visibility = View.VISIBLE
            btFollow.visibility = View.GONE
        }
        viewModel.loadRelationshipError().subscribe { Timber.e(it) }
        viewModel.relationship().subscribe {
            renderFollowing(it)
        }
    }

    private fun renderFollowing(btViewModel: FollowButtonViewModel) {
        pbFollowing.visibility = View.GONE
        btFollow.visibility = View.VISIBLE
        btFollow.isActivated = btViewModel.following
        btFollow.text = btViewModel.text
    }

    private fun renderHeader(user: UserDetail) {
        with(user) {
            tvProductCount.text = getString(R.string.product, products.size)
            tvFollowerCount.text = getString(R.string.follower, followerCount)
            tvFollowingCount.text = getString(R.string.following, followingCount)
        }
    }

    private fun setupBodyFragment() {
        var fragment = supportFragmentManager.findFragmentById(R.id.fBody)
        if (fragment == null) {
            fragment = UserDetailFragment.newInstance(user.id)
            supportFragmentManager.beginTransaction()
                    .add(R.id.fBody, fragment)
                    .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        flDraggable.addListener(chromeFader)
    }

    override fun onPause() {
        flDraggable.removeListener(chromeFader)
        super.onPause()
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    fun createSharedElementReenterCallback(context: Context): SharedElementCallback {
        val shotTransitionName = context.getString(R.string.transition_user)
        val shotBackgroundTransitionName = context.getString(R.string.transition_user_background)
        return object : SharedElementCallback() {

            /**
             * We're performing a slightly unusual shared element transition i.e. from one view
             * (image in the grid) to two views (the image & also the background of the details
             * view, to produce the expand effect). After changing orientation, the transition
             * system seems unable to map both shared elements (only seems to map the shot, not
             * the background) so in this situation we manually map the background to the
             * same view.
             */
            override fun onMapSharedElements(names: List<String>, sharedElements: MutableMap<String, View>) {
                if (sharedElements.size != names.size) {
                    // couldn't map all shared elements
                    val sharedShot = sharedElements[shotTransitionName]
                    if (sharedShot != null) {
                        // has shot so add shot background, mapped to same view
                        sharedElements.put(shotBackgroundTransitionName, sharedShot)
                    }
                }
            }
        }
    }
}