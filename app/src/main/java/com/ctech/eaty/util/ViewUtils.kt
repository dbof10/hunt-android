package com.ctech.eaty.util;

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Outline
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import android.support.v7.graphics.Palette
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewOutlineProvider
import android.view.WindowManager


object ViewUtils {

    fun isNavBarOnBottom(context: Context): Boolean {
        val res = context.resources
        val cfg = context.resources.configuration
        val dm = res.displayMetrics
        val canMove = (dm.widthPixels != dm.heightPixels &&
                cfg.smallestScreenWidthDp < 600)
        return (!canMove || dm.widthPixels < dm.heightPixels)
    }

    val CIRCULAR_OUTLINE: ViewOutlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setOval(view.paddingLeft,
                    view.paddingTop,
                    view.width - view.paddingRight,
                    view.height - view.paddingBottom)
        }
    }

    fun setLightStatusBar(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = view.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            view.systemUiVisibility = flags
        }
    }

    fun createRipple(palette: Palette?,
                     @FloatRange(from = 0.0, to = 1.0) darkAlpha: Float,
                     @FloatRange(from = 0.0, to = 1.0) lightAlpha: Float,
                     @ColorInt fallbackColor: Int,
                     bounded: Boolean): RippleDrawable {
        var rippleColor = fallbackColor
        if (palette != null) {
            // try the named swatches in preference order
            when {
                palette.vibrantSwatch != null -> rippleColor = ColorUtils.modifyAlpha(palette.vibrantSwatch!!.rgb, darkAlpha)
                palette.lightVibrantSwatch != null -> rippleColor = ColorUtils.modifyAlpha(palette.lightVibrantSwatch!!.rgb,
                        lightAlpha)
                palette.darkVibrantSwatch != null -> rippleColor = ColorUtils.modifyAlpha(palette.darkVibrantSwatch!!.rgb,
                        darkAlpha)
                palette.mutedSwatch != null -> rippleColor = ColorUtils.modifyAlpha(palette.mutedSwatch!!.rgb, darkAlpha)
                palette.lightMutedSwatch != null -> rippleColor = ColorUtils.modifyAlpha(palette.lightMutedSwatch!!.rgb,
                        lightAlpha)
                palette.darkMutedSwatch != null -> rippleColor = ColorUtils.modifyAlpha(palette.darkMutedSwatch!!.rgb, darkAlpha)
            }
        }
        return RippleDrawable(ColorStateList.valueOf(rippleColor), null,
                if (bounded) ColorDrawable(Color.WHITE) else null)
    }

    fun getScreenWidth(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    fun getScreenHeight(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

}
