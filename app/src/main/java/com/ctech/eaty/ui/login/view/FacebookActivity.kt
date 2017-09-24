package com.ctech.eaty.ui.login.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ctech.eaty.ui.login.result.FacebookLoginResult
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import io.reactivex.Observable
import timber.log.Timber
import java.util.*
import java.util.concurrent.CancellationException

class FacebookActivity : Activity() {

    private val callbackManager = CallbackManager.Factory.create()

    companion object {
        val KEY_LOGIN = "login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFacebook()

        LoginManager.getInstance().logInWithReadPermissions(this,
                Arrays.asList("public_profile", "user_friends", "user_likes"))
    }

    private fun setupFacebook() {
        Observable.create<String> { emitter ->
            LoginManager.getInstance().registerCallback(callbackManager,
                    object : FacebookCallback<LoginResult> {
                        override fun onSuccess(loginResult: LoginResult) {
                            emitter.onNext(loginResult.accessToken.token)
                            emitter.onComplete()
                        }

                        override fun onCancel() {
                            emitter.onError(CancellationException("Login cancelled"))
                        }

                        override fun onError(exception: FacebookException) {
                            emitter.onError(exception)
                        }
                    })
        }.subscribe({
            val intent = Intent()
            intent.putExtra(KEY_LOGIN, FacebookLoginResult(token = it))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }, {
            Timber.e(it)
            val intent = Intent()
            intent.putExtra(KEY_LOGIN, FacebookLoginResult(error = it.message ?: "Unknown error occurred"))
            setResult(Activity.RESULT_OK, intent)
            finish()
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        LoginManager.getInstance().unregisterCallback(callbackManager)
        super.onDestroy()
    }
}