package com.ctech.eaty.base

import com.ctech.eaty.tracking.FirebaseTrackManager
import javax.inject.Inject

class AppDelegate @Inject constructor(private val firebaseTrackManager: FirebaseTrackManager) {

    fun delegateTracking() {
    }

}