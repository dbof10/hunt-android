package com.ctech.eaty.ui.login.navigation

import android.app.Activity
import android.content.Intent
import com.ctech.eaty.entity.UserDetail
import com.ctech.eaty.ui.login.view.LoginActivity
import io.reactivex.Completable
import javax.inject.Inject

class LoginNavigation @Inject constructor(private val context: LoginActivity) {
    companion object {
        val USER_KEY = "user"
    }

    fun toHome(user: UserDetail): Completable {
        return Completable.fromAction {
            val intent = Intent()
            intent.putExtra(USER_KEY, user)
            context.setResult(Activity.RESULT_OK, intent)
            context.finish()
        }
    }
}