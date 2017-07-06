package com.ctech.eaty.ui.home.ad

import com.ctech.eaty.ui.home.view.HomeActivity
import com.facebook.ads.NativeAdsManager
import javax.inject.Inject

class AdsProvider @Inject constructor(private val activity: HomeActivity) {

    fun getAdsManager(adId: String) = NativeAdsManager(activity, adId, 5)

}