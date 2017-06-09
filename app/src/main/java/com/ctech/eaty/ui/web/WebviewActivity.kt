package com.ctech.eaty.ui.web

import android.os.Bundle
import android.view.MenuItem
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseActivity
import kotlinx.android.synthetic.main.activity_webview.*

class WebviewActivity : BaseActivity() {

    companion object {
        val EXTRA_URL = "extra.url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        val url = intent.getStringExtra(EXTRA_URL)
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