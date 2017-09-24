package com.ctech.eaty.ui.login.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.ctech.eaty.entity.TwitterToken
import com.ctech.eaty.ui.login.result.TwitterLoginResult
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterAuthClient
import io.reactivex.Observable
import timber.log.Timber

class TwitterActivity : Activity() {

    companion object {
        val KEY_LOGIN = "login"
    }

    private val authClient: TwitterAuthClient by lazy {
        TwitterAuthClient()
    }

    private lateinit var callback: Callback<TwitterSession>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupTwitter()
        authClient.authorize(this, callback)

    }

    private fun setupTwitter() {
        Observable.create<TwitterToken> { emitter ->
            callback = object : Callback<TwitterSession>() {

                override fun success(result: Result<TwitterSession>) {
                    val auth = result.data.authToken
                    emitter.onNext(TwitterToken(auth.token, auth.secret))
                    emitter.onComplete()
                }

                override fun failure(exception: TwitterException) {
                    emitter.onError(exception)
                }

            }
        }.subscribe({
            val intent = Intent()
            intent.putExtra(KEY_LOGIN, TwitterLoginResult(token = it))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }, {
            Timber.e(it)
            val intent = Intent()
            intent.putExtra(KEY_LOGIN, TwitterLoginResult(error = it.message ?: "Unknown error occurred"))
            setResult(Activity.RESULT_OK, intent)
            finish()
        })

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == authClient.requestCode) {
            authClient.onActivityResult(requestCode, resultCode, data)
        }
    }
}