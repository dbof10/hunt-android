package com.ctech.eaty.ui.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.ctech.eaty.R
import com.ctech.eaty.ui.home.viewmodel.SingleAdItemViewModel
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.NativeExpressAdView
import timber.log.Timber
import vn.tiki.noadapter2.AbsViewHolder

class SingleAdViewHolder(view: View) : AbsViewHolder(view) {

    @BindView(R.id.progressBar)
    lateinit var progressBar: View

    @BindView(R.id.adView)
    lateinit var nativeAdView: NativeExpressAdView

    init {
        ButterKnife.bind(this, view)
    }

    companion object {
        fun create(parent: ViewGroup): AbsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_single_ad, parent, false)
            return SingleAdViewHolder(view)
        }
    }

    override fun bind(item: Any?) {
        super.bind(item)
        val singleAd = item as SingleAdItemViewModel
        with(singleAd) {

            nativeAdView.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    super.onAdLoaded()
                    progressBar.visibility = View.GONE
                }

                override fun onAdFailedToLoad(errorCode: Int) {
                    Timber.e("Error load ad " + errorCode)
                }
            }
        }
        nativeAdView.loadAd(
                AdRequest.Builder()
                        .addTestDevice("575651CFAC043764A621FB2B547B1B7B")
                        .build())

    }
}