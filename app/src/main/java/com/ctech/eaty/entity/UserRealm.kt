package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class UserRealm(
        var id: Int = -1,
        var name: String = "",
        var username: String = "",
        var headline: String? = "",
        @SerializedName("image_url")
        var imageUrl: ImageUrl = ImageUrl()) : RealmModel {


    companion object {
        val ANONYMOUS = UserRealm()
    }

    fun makeUser(): User {
        return User(id, name, headline, username, imageUrl)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (id != other.id) return false
        if (name != other.name) return false
        if (username != other.username) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + username.hashCode()
        return result
    }


}