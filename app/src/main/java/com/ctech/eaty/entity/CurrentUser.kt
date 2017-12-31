package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class CurrentUser(@SerializedName("voted_for_post") var liked: Boolean = false,
                       @SerializedName("commented_on_post") var commented: Boolean = false) : RealmModel{

    override fun toString(): String {
        return "CurrentUser(liked=$liked, commented=$commented)"
    }
}