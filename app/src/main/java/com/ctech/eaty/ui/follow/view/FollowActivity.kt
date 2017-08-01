package com.ctech.eaty.ui.follow.view

import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseActivity
import com.ctech.eaty.tracking.FirebaseTrackManager
import com.ctech.eaty.util.AnimUtils.getLinearOutSlowInInterpolator
import com.ctech.eaty.widget.BottomSheet
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_votes.*
import javax.inject.Inject


class FollowActivity : BaseActivity(), HasSupportFragmentInjector, FragmentContractor {

    override fun getScreenName(): String = "Follow"

    companion object {
        private val USER_ID_KEY = "userId"
        private val RELATIONSHIP_KEY = "relationship"
        private val FOLLOW_COUNT_KEY = "follow"

        fun newIntent(context: Context, id: Int, count: Int, type: Relationship): Intent {
            val intent = Intent(context, FollowActivity::class.java)
            intent.putExtra(USER_ID_KEY, id)
            intent.putExtra(RELATIONSHIP_KEY, type)
            intent.putExtra(FOLLOW_COUNT_KEY, count)
            return intent
        }
    }

    private val DISMISS_DOWN = 0
    private val DISMISS_CLOSE = 1
    private var dismissState = DISMISS_DOWN

    @Inject
    lateinit var trackingManager: FirebaseTrackManager

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private val relationship by lazy {
        intent.getSerializableExtra(RELATIONSHIP_KEY) as Relationship
    }

    private val count by lazy {
        intent.getIntExtra(FOLLOW_COUNT_KEY, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follow)

        var fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (fragment == null) {
            fragment = FollowFragment.newInstance(intent.getIntExtra(USER_ID_KEY, 0),
                    relationship)
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit()
        }
        setupHeader()
        setupBottomSheet()
        trackingManager.trackScreenView(getScreenName())

    }

    private fun setupHeader() {
        tvTitle.text = relationship.run {
            if (this == Relationship.FOLLOWER)
                getString(R.string.follower, count)
            else
                getString(R.string.following, count)
        }
        ivClose.setOnClickListener { close(it) }
        bottomSheet.setOnClickListener { close(it) }

    }

    private fun close(view: View) {
        if (view.visibility != View.VISIBLE) return
        bottomSheet.dismiss()
    }

    private fun setupBottomSheet() {
        bottomSheet.registerCallback(object : BottomSheet.Callbacks {
            override fun onSheetDismissed() {
                finishAfterTransition()
            }

            override fun onSheetPositionChanged(sheetTop: Int, interacted: Boolean) {
                if (interacted && ivClose.visibility != View.VISIBLE) {
                    ivClose.visibility = View.VISIBLE
                    ivClose.alpha = 0f
                    ivClose.animate()
                            .alpha(1f)
                            .setDuration(400L)
                            .setInterpolator(getLinearOutSlowInInterpolator(applicationContext))
                            .start()
                }
                if (sheetTop == 0) {
                    showClose()
                } else {
                    showDown()
                }
            }

        })
    }

    private fun showClose() {
        if (dismissState == DISMISS_CLOSE) return
        dismissState = DISMISS_CLOSE
        val downToClose = ContextCompat.getDrawable(this, R.drawable.avd_down_to_close) as AnimatedVectorDrawable
        ivClose.setImageDrawable(downToClose)
        downToClose.start()
    }

    private fun showDown() {
        if (dismissState == DISMISS_DOWN) return
        dismissState = DISMISS_DOWN
        val closeToDown = ContextCompat.getDrawable(this, R.drawable.avd_close_to_down) as AnimatedVectorDrawable
        ivClose.setImageDrawable(closeToDown)
        closeToDown.start()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    override fun getTitleBar(): View = llTitleBar
}
