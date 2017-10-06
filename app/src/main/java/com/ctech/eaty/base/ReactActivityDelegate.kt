package com.ctech.eaty.base

import android.annotation.TargetApi
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.widget.Toast
import com.facebook.common.logging.FLog
import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactRootView
import com.facebook.react.bridge.Callback
import com.facebook.react.common.LifecycleState
import com.facebook.react.common.ReactConstants
import com.facebook.react.devsupport.DoubleTapReloadRecognizer
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import com.facebook.react.modules.core.PermissionListener

class ReactActivityDelegate(private val host: ReactNativeHost, private val mainComponentName: String) {
    private val REQUEST_OVERLAY_PERMISSION_CODE = 1111
    private val REDBOX_PERMISSION_GRANTED_MESSAGE = "Overlay permissions have been granted."
    private val REDBOX_PERMISSION_MESSAGE = "Overlay permissions needs to be granted in order for react native apps to run in dev mode"

    private var reactRootView: ReactRootView? = null
    private val doubleTapReloadRecognizer: DoubleTapReloadRecognizer
    private var permissionListener: PermissionListener? = null
    private var permissionsCallback: Callback? = null
    private var reactInstanceManager: ReactInstanceManager? = null

    init {
        reactInstanceManager = createReactInstanceManager()
        doubleTapReloadRecognizer = DoubleTapReloadRecognizer()

    }

    fun hasInstance(): Boolean {
        return reactInstanceManager != null
    }

    fun createRootView(): ReactRootView {
        return ReactRootView(host.getActivity())
    }


    fun createReactInstanceManager(): ReactInstanceManager {
        val builder = ReactInstanceManager.builder()
                .setApplication(host.getBaseApplication())
                .setJSMainModuleName(host.getJSMainModuleName())
                .setUseDeveloperSupport(host.getUseDeveloperSupport())
                .setRedBoxHandler(host.getRedBoxHandler())
                .setUIImplementationProvider(host.getUIImplementationProvider())
                .setInitialLifecycleState(LifecycleState.BEFORE_CREATE)

        for (reactPackage in host.getPackages()) {
            builder.addPackage(reactPackage)
        }

        val jsBundleFile = host.getJSBundleFile()
        if (jsBundleFile != null) {
            builder.setJSBundleFile(jsBundleFile)
        } else {
            builder.setBundleAssetName(host.getBundleAssetName())
        }
        return builder.build()
    }

    fun onCreate(savedInstanceState: Bundle?) {
        if (host.getUseDeveloperSupport() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Get permission to show redbox in dev builds.
            if (!Settings.canDrawOverlays(host.getActivity())) {
                val serviceIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + host.getActivity().packageName))
                FLog.w(ReactConstants.TAG, REDBOX_PERMISSION_MESSAGE)
                Toast.makeText(host.getActivity(), REDBOX_PERMISSION_MESSAGE, Toast.LENGTH_LONG).show()
                host.getActivity().startActivityForResult(serviceIntent, REQUEST_OVERLAY_PERMISSION_CODE)
            }
        }

    }

    fun loadReact() {
        if (host.getUseDeveloperSupport() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(host.getActivity())) {
                loadApp(mainComponentName)
            }
        } else {
            loadApp(mainComponentName)
        }
    }

    private fun loadApp(appKey: String) {
        if (reactRootView != null) {
            throw IllegalStateException("Cannot loadApp while app is already running.")
        }
        reactRootView = createRootView()
        reactRootView?.startReactApplication(
                reactInstanceManager,
                appKey,
                host.getLaunchOptions())
        host.mountReactView(reactRootView!!)
    }

    fun onPause() {
        reactInstanceManager?.onHostPause(host.getActivity())
    }

    fun onResume() {
        if (hasInstance()) {
            reactInstanceManager?.onHostResume(host.getActivity(),
                    host.getActivity() as DefaultHardwareBackBtnHandler)
        }

        if (permissionsCallback != null) {
            permissionsCallback?.invoke()
            permissionsCallback = null
        }
    }

    fun onDestroy() {
        if (reactRootView != null) {
            reactRootView?.unmountReactApplication()
            reactRootView = null
        }
        if (hasInstance()) {
            reactInstanceManager?.onHostDestroy(host.getActivity())
            reactInstanceManager?.destroy()
            reactInstanceManager = null
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (hasInstance()) {
            reactInstanceManager?.onActivityResult(host.getActivity(), requestCode, resultCode, data)
        } else {
            // Did we request overlay permissions?
            if (requestCode == REQUEST_OVERLAY_PERMISSION_CODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(host.getActivity())) {
                    loadApp(mainComponentName)
                    Toast.makeText(host.getActivity(), REDBOX_PERMISSION_GRANTED_MESSAGE, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        if (hasInstance() && host.getUseDeveloperSupport()) {
            if (keyCode == KeyEvent.KEYCODE_MENU) {
                reactInstanceManager?.showDevOptionsDialog()
                return true
            }

            val didDoubleTapR = doubleTapReloadRecognizer.didDoubleTapR(keyCode, host.getActivity().currentFocus)
            if (didDoubleTapR) {
                reactInstanceManager?.devSupportManager?.handleReloadJS()
                return true
            }
        }
        return false
    }

    fun onBackPressed(): Boolean {
        if (hasInstance()) {
            reactInstanceManager?.onBackPressed()
            return true
        }
        return false
    }

    fun onNewIntent(intent: Intent): Boolean {
        if (hasInstance()) {
            reactInstanceManager?.onNewIntent(intent)
            return true
        }
        return false
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissions(
            permissions: Array<String>,
            requestCode: Int,
            listener: PermissionListener) {
        permissionListener = listener
        host.getActivity().requestPermissions(permissions, requestCode)
    }

    fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray) {
        permissionsCallback = Callback {
            if (permissionListener != null && permissionListener!!.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
                permissionListener = null
            }
        }
    }


}