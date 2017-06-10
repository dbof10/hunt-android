package com.ctech.eaty.ui.comment.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject
import com.ctech.eaty.widget.ElasticDragDismissFrameLayout
import butterknife.BindView
import butterknife.ButterKnife

class CommentActivity : BaseActivity(), HasSupportFragmentInjector {


    @BindView(R.id.flDraggable)
    lateinit var draggableFrame: ElasticDragDismissFrameLayout

    private var chromeFader: ElasticDragDismissFrameLayout.SystemChromeFader? = null

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
        ButterKnife.bind(this)

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

    override fun onResume() {
        super.onResume()
        draggableFrame.addListener(chromeFader)
    }

    override fun onPause() {
        draggableFrame.removeListener(chromeFader)
        super.onPause()
    }
    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

}
