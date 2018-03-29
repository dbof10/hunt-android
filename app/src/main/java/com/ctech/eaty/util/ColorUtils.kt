package com.ctech.eaty.util

import android.support.annotation.CheckResult
import android.support.annotation.ColorInt
import android.support.annotation.IntRange
import android.support.v7.graphics.Palette
import com.ctech.eaty.annotation.IS_DARK
import com.ctech.eaty.annotation.IS_LIGHT
import com.ctech.eaty.annotation.LIGHTNESS_UNKNOWN
import com.ctech.eaty.annotation.Lightness
import android.support.annotation.FloatRange
import android.support.v4.graphics.ColorUtils.HSLToColor
import android.graphics.Bitmap
import android.support.v4.graphics.ColorUtils.colorToHSL


class ColorUtils private constructor() {

    companion object {
        @CheckResult
        @ColorInt
        fun modifyAlpha(@ColorInt color: Int, @IntRange(from = 0, to = 255) alpha: Int): Int {
            return color and 0x00ffffff or (alpha shl 24)
        }

        @CheckResult @ColorInt fun modifyAlpha(@ColorInt color: Int,
                                               @FloatRange(from = 0.0, to = 1.0) alpha: Float): Int {
            return modifyAlpha(color, (255f * alpha).toInt())
        }

        @Lightness fun isDark(palette: Palette): Int {
            val mostPopulous = getMostPopulousSwatch(palette) ?: return LIGHTNESS_UNKNOWN
            return if (isDark(mostPopulous.hsl)) IS_DARK else IS_LIGHT
        }

        fun isDark(hsl: FloatArray): Boolean { // @Size(3)
            return hsl[2] < 0.5f
        }

        fun isDark(bitmap: Bitmap, backupPixelX: Int, backupPixelY: Int): Boolean {
            // first try palette with a small color quant size
            val palette = Palette.from(bitmap).maximumColorCount(3).generate()
            return if (palette.swatches.size > 0) {
                isDark(palette) == IS_DARK
            } else {
                // if palette failed, then check the color of the specified pixel
                isDark(bitmap.getPixel(backupPixelX, backupPixelY))
            }
        }

        fun isDark(@ColorInt color: Int): Boolean {
            val hsl = FloatArray(3)
            colorToHSL(color, hsl)
            return isDark(hsl)
        }

        fun getMostPopulousSwatch(palette: Palette?): Palette.Swatch? {
            var mostPopulous: Palette.Swatch? = null
            if (palette != null) {
                for (swatch in palette.swatches) {
                    if (mostPopulous == null || swatch.population > mostPopulous.population) {
                        mostPopulous = swatch
                    }
                }
            }
            return mostPopulous
        }


        @ColorInt fun scrimify(@ColorInt color: Int, isDark: Boolean,
                               @FloatRange(from = 0.0, to = 1.0) lightnessMultiplier: Float): Int {
            var multiplier = lightnessMultiplier
            val hsl = FloatArray(3)
            android.support.v4.graphics.ColorUtils.colorToHSL(color, hsl)

            if (!isDark) {
                multiplier += 1f
            } else {
                multiplier = 1f - multiplier
            }

            hsl[2] = MathUtils.constrain(0f, 1f, hsl[2] * multiplier)
            return HSLToColor(hsl)
        }

    }

}
