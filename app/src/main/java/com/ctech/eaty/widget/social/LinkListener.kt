package com.ctech.eaty.widget.social

import android.view.View
import android.widget.TextView

typealias OnLinkClickListener = (TextView, String) -> Boolean
typealias OnLinkLongClickListener = (TextView, String) -> Boolean
typealias OnImageClickListener = (View, String) -> Unit