package com.ctech.eaty.base

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import com.facebook.react.modules.core.PermissionAwareActivity
import com.facebook.react.modules.core.PermissionListener

abstract class BaseReactActivity : BaseActivity(), ReactNativeHost, DefaultHardwareBackBtnHandler, PermissionAwareActivity {

    protected val delegate by lazy {
        ReactActivityDelegate(this, getMainComponentName())
    }


    abstract fun getMainComponentName(): String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegate.onCreate(savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
        delegate.onPause()
    }

    override fun onResume() {
        super.onResume()
        delegate.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        delegate.onDestroy()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        delegate.onActivityResult(requestCode, resultCode, data)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        return delegate.onKeyUp(keyCode, event) || super.onKeyUp(keyCode, event)
    }

    override fun onBackPressed() {
        if (!delegate.onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun invokeDefaultOnBackPressed() {
        super.onBackPressed()
    }

    public override fun onNewIntent(intent: Intent) {
        if (!delegate.onNewIntent(intent)) {
            super.onNewIntent(intent)
        }
    }

    override fun requestPermissions(
            permissions: Array<String>,
            requestCode: Int,
            listener: PermissionListener) {
        delegate.requestPermissions(permissions, requestCode, listener)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray) {
        delegate.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}