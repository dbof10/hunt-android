package com.ctech.eaty.ui.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.ui.home.viewmodel.HorizontalAdsItemViewModel
import com.facebook.ads.*
import timber.log.Timber
import vn.tiki.noadapter2.AbsViewHolder


class HorizontalAdsViewHolder(val view: View, val adsManager: NativeAdsManager) : AbsViewHolder(view) {

    @BindView(R.id.flAdsContainer)
    lateinit var flAdsContainer: ViewGroup

    private var nativeAdScrollView: NativeAdScrollView? = null

    init {
        ButterKnife.bind(this, view)
    }

    companion object {
        fun create(parent: ViewGroup, adsManager: NativeAdsManager): AbsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_horizontal_ads, parent, false)
            return HorizontalAdsViewHolder(view, adsManager)
        }
    }

    override fun bind(item: Any?) {
        super.bind(item)
        val horizontalAds = item as HorizontalAdsItemViewModel
        with(horizontalAds) {
            adsManager.setListener(object : NativeAdsManager.Listener {
                override fun onAdsLoaded() {
                    flAdsContainer.removeAllViews()
                    nativeAdScrollView = NativeAdScrollView(view.context, adsManager,
                            NativeAdView.Type.HEIGHT_300)
                    flAdsContainer.addView(nativeAdScrollView)
                }

                override fun onAdError(adError: AdError) {
                    Timber.e(Throwable(adError.errorMessage))
                    // Ad error callback
                }
            })
            adsManager.loadAds(NativeAd.MediaCacheFlag.ALL)
        }


    }
}