package com.ctech.eaty.ui.radio.state

import com.ctech.eaty.response.RadioResponse

data class RadioState(val loading: Boolean = false,
                      val loadError: Throwable? = null,
                      val content: RadioResponse? = RadioResponse.EMPTY)
