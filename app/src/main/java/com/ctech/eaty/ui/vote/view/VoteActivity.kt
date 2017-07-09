package com.ctech.eaty.ui.vote.view

import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
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


class VoteActivity : BaseActivity(), HasSupportFragmentInjector, FragmentContractor {
    override fun getScreenName(): String = "Vote"

    companion object {
        val POST_ID_KEY = "postId"
        val VOTE_COUNT_KEY = "voteCount"

        fun newIntent(context: Context, id: Int, voteCount: Int): Intent {
            val intent = Intent(context, VoteActivity::class.java)
            intent.putExtra(POST_ID_KEY, id)
            intent.putExtra(VOTE_COUNT_KEY, voteCount)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_votes)

        var fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (fragment == null) {
            fragment = VoteFragment.newInstance(intent.getIntExtra(POST_ID_KEY, 0))
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
        tvTitle.text = getString(R.string.vote_count, intent.getIntExtra(VOTE_COUNT_KEY, 0).toString())
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
