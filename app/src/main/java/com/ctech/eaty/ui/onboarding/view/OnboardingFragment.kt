package com.ctech.eaty.ui.onboarding.view

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_onboarding.*

class OnboardingFragment : BaseFragment() {

    companion object {

        private val KEY_TITLE = "title"
        private val KEY_IMAGE = "image"
        private val KEY_DESCRIPTION = "description"

        fun newInstance(@StringRes descriptionRes: Int, @DrawableRes imageRes: Int, @StringRes titleRes: Int): Fragment {

            val args = Bundle()

            val fragment = OnboardingFragment()
            args.putInt(KEY_TITLE, titleRes)
            args.putInt(KEY_IMAGE, imageRes)
            args.putInt(KEY_DESCRIPTION, descriptionRes)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleRes = arguments!!.getInt(KEY_TITLE)
        val imageRes = arguments!!.getInt(KEY_IMAGE)
        val descriptionRes = arguments!!.getInt(KEY_DESCRIPTION)

        tvTitle.setText(titleRes)
        ivImage.setImageResource(imageRes)
        tvDescription.setText(descriptionRes)
    }
}