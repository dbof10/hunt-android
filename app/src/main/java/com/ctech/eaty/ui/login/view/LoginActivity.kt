package com.ctech.eaty.ui.login.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseActivity
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.tracking.FirebaseTrackManager
import com.ctech.eaty.ui.login.action.LoginAction
import com.ctech.eaty.ui.login.state.LoginState
import com.ctech.eaty.ui.login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber
import javax.inject.Inject


class LoginActivity : BaseActivity(), Injectable {

    override fun getScreenName() = "Login"

    @Inject
    lateinit var viewModel: LoginViewModel

    @Inject
    lateinit var store: Store<LoginState>

    @Inject
    lateinit var trackingManager: FirebaseTrackManager

    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, LoginActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupWebView()
        setupViewModel()
        trackingManager.trackScreenView(getScreenName())
    }

    override fun onStart() {
        super.onStart()
        store.startBinding()
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }

    private fun setupViewModel() {
        viewModel.loginUrl().subscribe {
            webview.loadUrl(it)
        }
        viewModel.loading().subscribe { renderLoading() }
        viewModel.loadError().subscribe { renderError(it) }
        viewModel.content().subscribe {
            trackingManager.trackLoginSuccess()
        }
        viewModel.tokenGrant().subscribe {
            progressBar.visibility = View.GONE
            store.dispatch(LoginAction.LOAD_USER())
        }
    }

    private fun renderError(throwable: Throwable) {
        Timber.e(throwable)
        progressBar.visibility = View.GONE
        trackingManager.trackLoginFail(throwable.message)

    }

    private fun renderLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun setupWebView() {
        webview.clearCache(true)
        webview.clearCookie()
        webview.setWebViewClient(SupportClient())
    }

    inner class SupportClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest): Boolean {
            store.dispatch(LoginAction.REQUEST_TOKEN(request.url))
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progressBar.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressBar.visibility = View.GONE
        }
    }
}

private fun WebView.clearCookie() {
    CookieManager.getInstance().removeAllCookies(null)
    CookieManager.getInstance().flush()
}
