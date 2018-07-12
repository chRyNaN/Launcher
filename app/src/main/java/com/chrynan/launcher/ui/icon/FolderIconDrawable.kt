package com.chrynan.launcher.ui.icon

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.support.annotation.ColorInt
import com.chrynan.launcher.R
import kotlin.math.min
import kotlin.math.sqrt

class FolderIconDrawable(context: Context) : Drawable() {

    companion object {

        private const val DEFAULT_MAX_SIZE_FACTOR = 0.75f
        private const val DEFAULT_SIZE_DISSIPATION_FACTOR = 0.15f
        private const val DEFAULT_OFFSET_FACTOR = 0.10f
        private const val DEFAULT_OUTLINE_FACTOR = 0.10f
        private const val COUNT_STACKED = 3
        private const val COUNT_GRID = 4
    }

    var style: Style = Style.GridCutoff()
        set(value) = setAndRefresh { field = value }

    var drawables: List<Drawable> = emptyList()
        set(value) = setAndRefresh { field = value }

    @ColorInt
    var backgroundColor = context.getColor(R.color.launcher_item_view_default_folder_background_color)
        set(value) {
            field = value
            backgroundDrawable.setColorFilter(value, PorterDuff.Mode.SRC_IN)
            invalidateSelf()
        }

    @ColorInt
    private val defaultOutlineColor = context.getColor(R.color.white)

    private val width: Int
        get() = bounds.let { it.right - it.left }

    private val height: Int
        get() = bounds.let { it.bottom - it.top }

    private val centerX: Float
        get() = width / 2f

    private val centerY: Float
        get() = height / 2f

    private val radius: Float
        get() = min(width, height) / 2f

    private val backgroundDrawable by lazy {
        GradientDrawable().apply {
            shape = GradientDrawable.OVAL
        }
    }

    private val outlineDrawable by lazy {
        GradientDrawable().apply {
            shape = GradientDrawable.OVAL
        }
    }

    private val bitmapPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)

        backgroundDrawable.setBounds(left, top, right, bottom)
        outlineDrawable.setBounds(left, top, right, bottom)

        style.let {
            when (it) {
                is Style.Stacked -> setStackedBounds(left, top, right, bottom)
                is Style.GridInset -> setGridInsetBounds(left, top, right, bottom)
                is Style.GridCutoff -> setGridCutoffBounds(left, top, right, bottom, it.outlineColor, it.outlineSizeFactor)
            }
        }
    }

    override fun draw(canvas: Canvas?) {
        if ((style is Style.GridCutoff) or (style == Style.GridInset)) {
            val bitmap = Bitmap.createBitmap(width.coerceAtLeast(1), height.coerceAtLeast(1), Bitmap.Config.ARGB_8888)

            val offscreenCanvas = Canvas(bitmap)

            backgroundDrawable.draw(offscreenCanvas)

            drawables.reversed().forEach { it.draw(offscreenCanvas) }

            if (style is Style.GridCutoff) outlineDrawable.draw(offscreenCanvas)

            bitmapPaint.shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

            canvas?.drawCircle(centerX, centerY, radius, bitmapPaint)
        } else {
            backgroundDrawable.draw(canvas)

            drawables.reversed().forEach { it.draw(canvas) }
        }
    }

    override fun setAlpha(alpha: Int) {
        backgroundDrawable.alpha = alpha
        outlineDrawable.alpha = alpha

        drawables.forEach { it.alpha = alpha }
    }

    override fun getOpacity() = PixelFormat.TRANSLUCENT

    override fun setColorFilter(colorFilter: ColorFilter?) {
        backgroundDrawable.colorFilter = colorFilter

        drawables.forEach { it.colorFilter = colorFilter }
    }

    private fun setStackedBounds(left: Int, top: Int, right: Int, bottom: Int) {
        val width = right - left
        val height = bottom - top
        val centerX = (left + right) / 2

        drawables.take(COUNT_STACKED)
                .forEachIndexed { index, drawable ->
                    val childWidth = width * (DEFAULT_MAX_SIZE_FACTOR - (index * DEFAULT_SIZE_DISSIPATION_FACTOR))
                    val childHeight = height * (DEFAULT_MAX_SIZE_FACTOR - (index * DEFAULT_SIZE_DISSIPATION_FACTOR))
                    val maxChildHeight = height * DEFAULT_MAX_SIZE_FACTOR
                    val verticalOffset = index * DEFAULT_OFFSET_FACTOR * maxChildHeight

                    val childLeft = (centerX - (childWidth / 2)).toInt()
                    val childTop = (bottom - maxChildHeight - verticalOffset).toInt()
                    val childRight = (childLeft + childWidth).toInt()
                    val childBottom = (childTop + childHeight).toInt()

                    drawable.setBounds(childLeft, childTop, childRight, childBottom)
                }
    }

    private fun setGridInsetBounds(left: Int, top: Int, right: Int, bottom: Int) {
        val drawablesToDraw = drawables.take(COUNT_GRID)
        val smallSize = drawablesToDraw.size > 2

        val internalSquareSize = ((right - left) / sqrt(2f)).toInt()

        val childWidth = internalSquareSize / 2
        val childHeight = internalSquareSize / 2

        val centerX = (right - left) / 2
        val centerY = (bottom - top) / 2
        val startX = if (smallSize) centerX - childWidth else centerX - childWidth / 2
        val startY = if (smallSize) centerY - childHeight else centerY - childWidth / 2

        drawablesToDraw.forEachIndexed { index, drawable ->
            val childLeft = startX + (childWidth * (index % 2))
            val childTop = startY + (childHeight * (index / 2))
            val childRight = childLeft + childWidth
            val childBottom = childTop + childHeight

            drawable.setBounds(childLeft, childTop, childRight, childBottom)
        }
    }

    private fun setGridCutoffBounds(left: Int, top: Int, right: Int, bottom: Int, outlineColor: Int?, outlineSizeFactor: Float) {
        val childWidth = (right - left) / 2
        val childHeight = (bottom - top) / 2
        val outlineBorderSize = (childWidth * outlineSizeFactor).toInt()

        drawables.take(COUNT_GRID)
                .forEachIndexed { index, drawable ->
                    val childLeft = childWidth * (index % 2)
                    val childTop = childHeight * (index / 2)
                    val childRight = childLeft + childWidth
                    val childBottom = childTop + childHeight

                    drawable.setBounds(childLeft, childTop, childRight, childBottom)
                }

        outlineDrawable.setStroke(outlineBorderSize, outlineColor ?: defaultOutlineColor)
    }

    private fun setAndRefresh(setter: () -> Unit) {
        setter()
        setBounds(bounds.left, bounds.top, bounds.right, bounds.bottom)
        invalidateSelf()
    }

    sealed class Style {

        data class Stacked(
                val maxSizeFactor: Float = DEFAULT_MAX_SIZE_FACTOR,
                val sizeDissipationFactor: Float = DEFAULT_SIZE_DISSIPATION_FACTOR,
                val offsetFactor: Float = DEFAULT_OFFSET_FACTOR
        ) : Style()

        object GridInset : Style()

        data class GridCutoff(
                @ColorInt
                val outlineColor: Int? = null,
                val outlineSizeFactor: Float = DEFAULT_OUTLINE_FACTOR
        ) : Style()
    }
}