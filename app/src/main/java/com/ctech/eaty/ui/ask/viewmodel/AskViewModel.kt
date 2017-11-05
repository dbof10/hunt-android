package com.ctech.eaty.ui.ask.viewmodel

import com.ctech.eaty.ui.ask.navigator.AskNavigation
import com.ctech.eaty.util.rx.Functions
import timber.log.Timber
import javax.inject.Inject

class AskViewModel @Inject constructor(private val navigator: AskNavigation) {
    fun shareUrl(url: String) {
        navigator.toShare(url)
                .subscribe(Functions.EMPTY, Timber::e)
    }

    fun navigateProduct(id: Int) {
        navigator.toProduct(id)
                .subscribe(Functions.EMPTY, Timber::e)

    }


}