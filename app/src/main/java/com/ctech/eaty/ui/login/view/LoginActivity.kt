package com.ctech.eaty.ui.login.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseActivity
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.tracking.FirebaseTrackManager
import com.ctech.eaty.ui.login.action.LoginAction
import com.ctech.eaty.ui.login.state.LoginState
import com.ctech.eaty.ui.login.viewmodel.LoginViewModel
import com.ctech.eaty.util.MorphTransform
import com.ctech.eaty.util.setHeight
import com.ctech.eaty.util.setWidth
import com.ctech.eaty.widget.transition.CircularTransform
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_login.*
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

    private var isDismissing = false


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

        if (!CircularTransform.setup(this, container)) {
            MorphTransform.setup(this, container,
                    ContextCompat.getColor(this, R.color.gray_50),
                    resources.getDimensionPixelSize(R.dimen.dialog_corners))
        }
        trackingManager.trackScreenView(getScreenName())
    }

    override fun onStart() {
        super.onStart()
        store.startBinding()
    }

    fun doLogin(view: View) {
        showLoading()
    }

    private fun showLoading() {
        TransitionManager.beginDelayedTransition(container)
        tvMessage.visibility = View.GONE
        btLogin.visibility = View.GONE
        vLogin.visibility = View.VISIBLE
        container.setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
        container.setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
        container.setPadding(0, 0, 0, 0)
    }

    fun dismiss(view: View) {
        isDismissing = true
        finishAfterTransition()
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
