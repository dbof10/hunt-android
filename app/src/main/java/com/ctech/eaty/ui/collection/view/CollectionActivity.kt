package com.ctech.eaty.ui.collection.view

import android.os.Bundle
import android.support.v4.app.Fragment
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseActivity
import com.ctech.eaty.widget.ElasticDragDismissFrameLayout
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_collections.*
import kotlinx.android.synthetic.main.activity_comments.*
import javax.inject.Inject

class CollectionActivity : BaseActivity(), HasSupportFragmentInjector {


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collections)
        setupToolbar()
        var fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (fragment == null) {
            fragment = CollectionFragment.newInstance()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit()
        }
    }

    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener {
            finishAfterTransition()
        }
    }


    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

}
