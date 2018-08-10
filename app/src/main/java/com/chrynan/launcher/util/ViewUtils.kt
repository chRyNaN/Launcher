package com.chrynan.launcher.util

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewConfiguration
import android.widget.EditText
import android.widget.TextView
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import java.util.concurrent.TimeUnit

val Context.hasVirtualNavigationBar
    get() = !ViewConfiguration.get(this).hasPermanentMenuKey()

val Context.virtualNavigationBarHeight: Int
    get() =
        if (hasVirtualNavigationBar) {
            val orientation = resources.configuration.orientation

            val resId = resources.getIdentifier(
                    if (orientation == Configuration.ORIENTATION_PORTRAIT) "navigation_bar_height" else "navigation_bar_height_landscape",
                    "dimen",
                    "android"
            )

            if (resId > 0) resources.getDimensionPixelSize(resId) else 0
        } else 0

fun Drawable.toBitmap(): Bitmap {
    if (this is BitmapDrawable) {
        return bitmap
    }

    val bitmap = Bitmap.createBitmap(
            intrinsicWidth.coerceAtLeast(1),
            intrinsicHeight.coerceAtLeast(1),
            Bitmap.Config.ARGB_8888)

    val canvas = Canvas(bitmap)
    setBounds(0, 0, canvas.width, canvas.height)
    draw(canvas)

    return bitmap
}

fun TextView.setTextOrGone(textToSet: String?) {
    text = textToSet
    setVisibleOrGone(textToSet != null)
}

fun measureSpec(size: MeasureSpecSize, mode: MeasureSpecMode) = View.MeasureSpec.makeMeasureSpec(size, mode)

val View.horizontalPadding: Int
    get() = paddingStart + paddingEnd

val View.verticalPadding: Int
    get() = paddingTop + paddingBottom

fun View.centerXWithRespectToPadding(width: Int) =
        paddingLeft + ((width - horizontalPadding) / 2)

fun View.centerYWithRespectToPadding(height: Int) =
        paddingTop + ((height - verticalPadding) / 2)

fun RectF.copy() = RectF(left, top, right, bottom)

fun EditText?.handleTextChanges() = this?.textChanges()?.debounce(250L, TimeUnit.MILLISECONDS)

fun View?.handleClicks() = this?.clicks()?.throttleFirst(1000L, TimeUnit.MILLISECONDS)

fun View.setVisibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}