package com.ctech.eaty.linking

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ctech.eaty.R
import com.ctech.eaty.di.Injectable
import javax.inject.Inject

class UniversalLinkActivity : AppCompatActivity(), Injectable {

    @Inject
    lateinit var universalLinkDispatcher: UniversalLinkDispatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        universalLinkDispatcher.dispatch(intent.dataString)
    }


}