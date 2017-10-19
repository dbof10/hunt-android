package com.ctech.eaty.linking

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ctech.eaty.R
import com.ctech.eaty.di.Injectable
import javax.inject.Inject

class UniversalLinkActivity : AppCompatActivity(), Injectable {

    companion object {

        fun newIntent(context: Context, url: String): Intent {
            val intent = Intent(context, UniversalLinkActivity::class.java)
            intent.data = Uri.parse(url)
            return intent
        }
    }

    @Inject
    lateinit var universalLinkDispatcher: UniversalLinkDispatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        universalLinkDispatcher.dispatch(intent.dataString)
    }


}