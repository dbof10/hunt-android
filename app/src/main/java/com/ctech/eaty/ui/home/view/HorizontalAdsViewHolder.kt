package com.ctech.eaty.ui.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.ui.home.ad.AdsProvider
import com.ctech.eaty.ui.home.viewmodel.HorizontalAdsItemViewModel
import com.facebook.ads.*
import timber.log.Timber
import vn.tiki.noadapter2.AbsViewHolder


class HorizontalAdsViewHolder(private val view: View, private val adsProvider: AdsProvider) : AbsViewHolder(view) {

    @BindView(R.id.flAdsContainer)
    lateinit var flAdsContainer: ViewGroup

    @BindView(R.id.progressBar)
    lateinit var progressBar: View

    private var nativeAdScrollView: View? = null

    init {
        ButterKnife.bind(this, view)
    }

    companion object {
        fun create(parent: ViewGroup, adsProvider: AdsProvider): AbsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_horizontal_ads, parent, false)
            return HorizontalAdsViewHolder(view, adsProvider)
        }
    }

    override fun bind(item: Any?) {
        super.bind(item)
        val horizontalAds = item as HorizontalAdsItemViewModel
        with(horizontalAds) {
            val adsManager = adsProvider.getAdsManager(adId)

            adsManager.setListener(object : NativeAdsManager.Listener {
                override fun onAdsLoaded() {
                    progressBar.visibility = View.GONE

                    flAdsContainer.removeView(itemView.findViewById(id))

                    if (nativeAdScrollView == null) {
                        nativeAdScrollView = NativeAdScrollView(view.context, adsManager, NativeAdView.Type.HEIGHT_300)
                        nativeAdScrollView?.id = id
                    }

                    flAdsContainer.addView(nativeAdScrollView)
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
}