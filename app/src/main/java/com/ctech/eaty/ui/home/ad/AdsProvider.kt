package com.ctech.eaty.ui.home.ad

import com.ctech.eaty.ui.home.view.HomeActivity
import com.facebook.ads.NativeAdsManager
import javax.inject.Inject

class AdsProvider @Inject constructor(private val activity: HomeActivity) {


    private val adsManager: NativeAdsManager by lazy {
        NativeAdsManager(activity, "1966287263602613_1966287926935880", 5)
    }

    fun getAdsManager(adId: String) = NativeAdsManager(activity, adId, 5)

}