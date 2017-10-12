package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName

data class InstallLink(@SerializedName("redirect_url") val url: String,
                       val platform: Platform?) {

    fun makeInstallLinkRealm(): InstallLinkRealm {
        return InstallLinkRealm(url, platform?.name)
    }
}