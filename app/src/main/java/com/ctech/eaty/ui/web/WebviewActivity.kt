package com.ctech.eaty.ui.web

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseActivity
import kotlinx.android.synthetic.main.activity_webview.webview


class WebviewActivity : BaseActivity() {
    override fun getScreenName(): String = "Webview"

    companion object {
        val EXTRA_URL = "extra.url"
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        val url = intent.getStringExtra(EXTRA_URL)
        webview.webViewClient = object: WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {

                if(request.url.toString().contains( "facebook.com" , true)){
                    val launchBrowser = Intent(Intent.ACTION_VIEW, request.url)
                    startActivity(launchBrowser)
                    finish()
                    return false
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        webview.settings.javaScriptEnabled = true
        title = url
        webview.loadUrl(url)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}