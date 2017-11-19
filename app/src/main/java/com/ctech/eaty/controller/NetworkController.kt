package com.ctech.eaty.controller

import android.content.Context
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkController @Inject constructor(private val context: Context){

    fun observeNetworkConnectivity() = ReactiveNetwork.observeNetworkConnectivity(context)

}