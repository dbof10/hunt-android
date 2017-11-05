package com.ctech.eaty.ui.home.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.ctech.eaty.R
import com.ctech.eaty.util.setPaddingBottom
import com.facebook.ads.*
import kotlinx.android.synthetic.main.item_horizontal_ads.view.*
import timber.log.Timber

class HorizontalAdsView(context: Context) : FrameLayout(context) {

    private var itemId = -1

    init {
        LayoutInflater.from(context).inflate(R.layout.item_horizontal_ads, this, true)
        setPaddingBottom(context.resources.getDimensionPixelSize(R.dimen.divider_space))
    }

    fun with(id: Int): HorizontalAdsView {
        this.itemId = id
        return this
    }

    fun bind(adsManager: NativeAdsManager) {
        adsManager.setListener(object : NativeAdsManager.Listener {
            override fun onAdsLoaded() {
                progressBar.visibility = View.GONE

                val adsScrollView = findViewById<NativeAdScrollView>(itemId)
                if (adsScrollView == null) {
                    val view = NativeAdScrollView(context, adsManager, NativeAdView.Type.HEIGHT_300)
                    view.id = itemId
                    flAdsContainer.addView(view)
                }
            }

            override fun onAdError(adError: AdError) {
                Timber.e(Throwable(adError.errorMessage))
            }
        })

        adsManager.loadAds(NativeAd.MediaCacheFlag.ALL)

        if (!adsManager.isLoaded) {
            progressBar.visibility = View.VISIBLE
        }
    }

}