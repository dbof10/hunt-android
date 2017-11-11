package com.ctech.eaty.error

import com.ctech.eaty.R
import com.ctech.eaty.util.ResourceProvider
import javax.inject.Inject
import javax.inject.Singleton
import javax.net.ssl.SSLHandshakeException

@Singleton
class ErrorInterpreter @Inject constructor(val resourceProvider: ResourceProvider) {

    fun getErrorMessage(throwable: Throwable): ErrorMessage {
        return when (throwable) {
            is NoConnectionException -> ErrorMessage(resourceProvider.getString(R.string.error_no_network))
            is SSLHandshakeException -> ErrorMessage(resourceProvider.getString(R.string.error_cannot_connect_server),
                    resourceProvider.getString(R.string.explain_cannot_connect_server))
            else -> ErrorMessage(resourceProvider.getString(R.string.something_wrong))
        }
    }
}