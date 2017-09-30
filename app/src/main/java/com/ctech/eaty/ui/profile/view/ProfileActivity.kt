package com.ctech.eaty.ui.profile.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseActivity
import com.ctech.eaty.entity.UserDetail
import com.ctech.eaty.tracking.FirebaseTrackManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_profile.*
import javax.inject.Inject

class ProfileActivity : BaseActivity(), HasSupportFragmentInjector {
    override fun getScreenName(): String = "Profile"

    @Inject
    lateinit var trackingManager: FirebaseTrackManager

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    companion object {
        private val KEY_USER = "user"

        fun newIntent(context: Context, user: UserDetail): Intent {
            val intent = Intent(context, ProfileActivity::class.java)
            intent.putExtra(KEY_USER, user)
            return intent
        }

        fun newIntent(context: Context): Intent {
            return Intent(context, ProfileActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupToolbar()

        var fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (fragment == null) {
            fragment = intent.hasExtra(KEY_USER).run {
                if (this) {
                    ProfileFragment.newInstance(intent.getParcelableExtra(KEY_USER))
                } else {
                    ProfileFragment.newInstance()

                }
            }
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit()
        }
        trackingManager.trackScreenView(getScreenName())
    }

    private fun setupToolbar() {
        toolbar.inflateMenu(R.menu.menu_profile)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }


    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

}
