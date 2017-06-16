package com.ctech.eaty.error

import com.ctech.eaty.R
import com.ctech.eaty.util.ResourceProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorMapper @Inject constructor(val resourceProvider: ResourceProvider) {

    fun getErrorMessage(throwable: Throwable): ErrorMessage {
        return when (throwable) {
            is NoConnectionException -> ErrorMessage(resourceProvider.getString(R.string.no_internet))
            else -> ErrorMessage(resourceProvider.getString(R.string.something_wrong))
        }
    }
}