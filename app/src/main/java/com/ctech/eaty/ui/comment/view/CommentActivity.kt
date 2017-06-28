package com.ctech.eaty.ui.comment.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseActivity
import com.ctech.eaty.tracking.FirebaseTrackManager
import com.ctech.eaty.widget.ElasticDragDismissFrameLayout
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_comments.*
import javax.inject.Inject

class CommentActivity : BaseActivity(), HasSupportFragmentInjector {
    override fun getScreenName(): String = "Comment"

    @Inject
    lateinit var trackingManager: FirebaseTrackManager

    private lateinit var chromeFader: ElasticDragDismissFrameLayout.SystemChromeFader

    companion object {
        val PRODUCT_ID = "productId"

        fun newIntent(context: Context, id: Int): Intent {
            val intent = Intent(context, CommentActivity::class.java)
            intent.putExtra(PRODUCT_ID, id)
            return intent
        }
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        chromeFader = object : ElasticDragDismissFrameLayout.SystemChromeFader(this) {
            override fun onDragDismissed() {
                finishAfterTransition()
            }
        }


        var fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (fragment == null) {
            fragment = CommentFragment.newInstance(intent.getIntExtra(PRODUCT_ID, 0))
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit()
        }
    }

    override fun onStart() {
        super.onStart()
        trackingManager.trackScreenView(getScreenName())
    }

    override fun onResume() {
        super.onResume()
        flDraggable.addListener(chromeFader)
    }

    override fun onPause() {
        flDraggable.removeListener(chromeFader)
        super.onPause()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

}
