package com.ctech.eaty.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Property;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

class ViewUtils private constructor() {

    companion object {
        fun isNavBarOnBottom(context: Context): Boolean {
            val res = context.resources
            val cfg = context.resources.configuration
            val dm = res.displayMetrics
            val canMove = (dm.widthPixels != dm.heightPixels &&
                    cfg.smallestScreenWidthDp < 600)
            return (!canMove || dm.widthPixels < dm.heightPixels)
        }
    }

}
