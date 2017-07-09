package com.ctech.eaty.ui.search.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseActivity
import com.ctech.eaty.entity.Topic
import com.ctech.eaty.tracking.FirebaseTrackManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.layout_home_content.*
import javax.inject.Inject


class SearchActivity : BaseActivity(), HasSupportFragmentInjector {
    override fun getScreenName(): String = "Search"

    @Inject
    lateinit var trackingManager: FirebaseTrackManager

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    companion object {
        val TOPIC_ID_KEY = "topicId"

        fun newIntent(context: Context, topic: Topic): Intent {
            val intent = Intent(context, SearchActivity::class.java)
            intent.putExtra(TOPIC_ID_KEY, topic)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val topic = intent.getParcelableExtra<Topic>(TOPIC_ID_KEY)

        setupToolbar(topic.name)
        var fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (fragment == null) {
            fragment = SearchFragment.newInstance(topic)
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit()
        }
        trackingManager.trackScreenView(getScreenName())
    }

    private fun setupToolbar(name: String) {
        toolbar.title = name
        toolbar.setNavigationOnClickListener {
            finish()
        }

    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

}
