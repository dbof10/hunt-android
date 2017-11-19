package com.ctech.eaty.ui.onboarding.view

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageView
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseActivity
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.ui.home.view.HomeActivity
import kotlinx.android.synthetic.main.activity_onboarding.*
import javax.inject.Inject


class OnboardingActivity : BaseActivity(), Injectable {

    override fun getScreenName() = "Onboarding"

    @Inject
    lateinit var appSettingsManager: AppSettingsManager
    private val adapter by lazy {
        ViewPagerAdapter(supportFragmentManager)
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        setupListener()
        setupScene()

    }

    private fun setupScene() {

        val scenes = ArrayList<Fragment>(3)
        val page1 = OnboardingFragment.newInstance(R.string.onboarding_first_scene, R.drawable.onboarding_scene_1, R.string.onboarding_first_scene_title)
        val page2 = OnboardingFragment.newInstance(R.string.onboarding_second_scene, R.drawable.onboarding_scene_2, R.string.onboarding_second_scene_title)
        val page3 = OnboardingFragment.newInstance(R.string.onboarding_third_scene, R.drawable.onboarding_scene_3, R.string.onboarding_third_scene_title)

        scenes.run {
            add(page1)
            add(page2)
            add(page3)
        }
        viewPager.adapter = adapter
        adapter.setData(scenes)
        indicator.setViewPager(viewPager)
        viewPager.setPageTransformer(false, PageTransformer())
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                val count = adapter.count
                if (position == count - 1) {
                    btDone.visibility = View.VISIBLE
                    btSkip.visibility = View.GONE
                } else {
                    btDone.visibility = View.GONE
                    btSkip.visibility = View.VISIBLE
                }
            }

        })
    }

    private fun setupListener() {
        btSkip.setOnClickListener {
            goHome()
        }
        btDone.setOnClickListener {
            goHome()
        }
    }


    override fun onBackPressed() {

    }

    private fun goHome() {
        appSettingsManager.setDidSeeOnboarding()
        val intent = HomeActivity.newIntent(this)
        startActivity(intent)
        finish()
    }

    class PageTransformer : ViewPager.PageTransformer {

        override fun transformPage(page: View, position: Float) {
            val absPosition = Math.abs(position)

            if (position < -1 || position > 1) { // [-Infinity,-1)
                page.alpha = 0F

            } else if (position <= 1) { // [-1,1]

                val image = page.findViewById<ImageView>(R.id.ivImage)

                image.scaleX = 1.0f - absPosition * 2
                image.scaleY = 1.0f - absPosition * 2
                image.alpha = 1.0f - absPosition * 2
            }
        }

    }
}