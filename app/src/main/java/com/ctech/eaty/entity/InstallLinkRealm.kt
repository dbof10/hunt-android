package com.ctech.eaty.entity

import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class InstallLinkRealm(var url: String = "",
                            var platform: String? = null) : RealmModel {

    fun makeInstallLink(): InstallLink {
        return InstallLink(url,
                if (!platform.isNullOrEmpty())
                    Platform.valueOf(platform!!)
                else
                    null)
    }
}