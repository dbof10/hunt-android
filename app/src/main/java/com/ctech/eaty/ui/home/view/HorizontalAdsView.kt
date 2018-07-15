package com.ctech.eaty.ui.home.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.ctech.eaty.R
import com.ctech.eaty.util.setPaddingBottom
import com.facebook.ads.AdError
import com.facebook.ads.NativeAd
import com.facebook.ads.NativeAdBase
import com.facebook.ads.NativeAdScrollView
import com.facebook.ads.NativeAdView
import com.facebook.ads.NativeAdsManager
import kotlinx.android.synthetic.main.item_horizontal_ads.view.flAdsContainer
import kotlinx.android.synthetic.main.item_horizontal_ads.view.progressBar
import timber.log.Timber

class HorizontalAdsView(context: Context) : FrameLayout(context) {


    private lateinit var adsManager: NativeAdsManager

    init {
        LayoutInflater.from(context).inflate(R.layout.item_horizontal_ads, this, true)
        setPaddingBottom(context.resources.getDimensionPixelSize(R.dimen.divider_space))
    }

    fun bind(adsManager: NativeAdsManager) {
        this.adsManager = adsManager
        adsManager.setListener(object : NativeAdsManager.Listener {
            override fun onAdsLoaded() {
                progressBar.visibility = View.GONE
                flAdsContainer.removeAllViews()
                val view = NativeAdScrollView(context, adsManager, NativeAdView.Type.HEIGHT_300)
                flAdsContainer.addView(view)
            }

            override fun onAdError(adError: AdError) {
                Timber.e(Throwable(adError.errorMessage))
            }
        })

        adsManager.loadAds(NativeAdBase.MediaCacheFlag.ALL)

        if (!adsManager.isLoaded) {
            progressBar.visibility = View.VISIBLE
        }
    }

    fun unbind() {
        adsManager.setListener(null)
    }

}