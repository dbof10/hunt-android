package com.ctech.eaty.ui.onboarding.view

import android.graphics.Color
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageView
import com.ctech.eaty.R
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.ui.home.view.HomeActivity
import com.github.paolorotolo.appintro.AppIntro2
import com.github.paolorotolo.appintro.AppIntroFragment
import com.github.paolorotolo.appintro.model.SliderPage
import javax.inject.Inject


class OnboardingActivity : AppIntro2(), Injectable {

    @Inject
    lateinit var appSettingsManager: AppSettingsManager

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val page1 = SliderPage()
        page1.description = getString(R.string.onboarding_first_scene)
        page1.imageDrawable = R.drawable.onboarding_scene_1
        page1.bgColor = Color.WHITE
        page1.descColor = ContextCompat.getColor(this, R.color.text_secondary_dark)
        page1.title = getString(R.string.onboarding_first_scene_title)
        page1.titleColor = ContextCompat.getColor(this, R.color.text_primary_dark)
        addSlide(AppIntroFragment.newInstance(page1))

        val page2 = SliderPage()
        page2.description = getString(R.string.onboarding_second_scene)
        page2.imageDrawable = R.drawable.onboarding_scene_2
        page2.bgColor = Color.WHITE
        page2.descColor = ContextCompat.getColor(this, R.color.text_secondary_dark)

        page2.title = getString(R.string.onboarding_second_scene_title)
        page2.titleColor = ContextCompat.getColor(this, R.color.text_primary_dark)
        addSlide(AppIntroFragment.newInstance(page2))


        val page3 = SliderPage()
        page3.description = getString(R.string.onboarding_third_scene)
        page3.imageDrawable = R.drawable.onboarding_scene_3
        page3.bgColor = Color.WHITE
        page3.descColor = ContextCompat.getColor(this, R.color.text_secondary_dark)

        page3.title = getString(R.string.onboarding_third_scene_title)
        page3.titleColor = ContextCompat.getColor(this, R.color.text_primary_dark)
        addSlide(AppIntroFragment.newInstance(page3))

        setCustomTransformer(PageTransformer())

        setBarColor(ContextCompat.getColor(this, R.color.colorPrimary))

    }

    override fun onSkipPressed(currentFragment: Fragment) {
        super.onSkipPressed(currentFragment)
        goHome()
    }

    private fun goHome() {
        appSettingsManager.setDidSeeOnboarding()
        val intent = HomeActivity.newIntent(this)
        startActivity(intent)
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment) {
        super.onDonePressed(currentFragment)
        goHome()
    }


    class PageTransformer : ViewPager.PageTransformer {

        override fun transformPage(page: View, position: Float) {
            val absPosition = Math.abs(position)

            if (position < -1 || position > 1) { // [-Infinity,-1)
                page.alpha = 0F

            } else if (position <= 1) { // [-1,1]

                val image = page.findViewById<ImageView>(R.id.image)

                image.scaleX = 1.0f - absPosition * 2
                image.scaleY = 1.0f - absPosition * 2
                image.alpha = 1.0f - absPosition * 2
            }
        }

    }
}