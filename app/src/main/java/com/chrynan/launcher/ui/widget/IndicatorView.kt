package com.chrynan.launcher.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.support.annotation.ColorInt
import android.support.annotation.Dimension
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import com.chrynan.launcher.R
import com.chrynan.launcher.delegates.ReconstructOnSetDelegate
import com.chrynan.launcher.util.*
import kotlin.math.min

typealias IndicatorCount = Int
typealias CurrentIndicatorIndex = Int
typealias Factor = Float

@ViewPager.DecorView
class IndicatorView : View,
        ViewPager.OnAdapterChangeListener,
        ViewPager.OnPageChangeListener {

    companion object {

        private const val DEFAULT_PAGE_COUNT = 1
        private const val DEFAULT_CURRENT_PAGE_INDEX = 0
        private const val DEFAULT_SCROLL_X_FACTOR = 0f
        private const val DEFAULT_SCROLL_Y_FACTOR = 0f
    }

    val bottomSheetIndicatorScrollCallback by lazy { BottomSheetIndicatorScrollCallback(this) }

    var indicatorCount: IndicatorCount by ReconstructOnSetDelegate(DEFAULT_PAGE_COUNT)

    private val defaultSelectedIndicatorColor by color(R.color.indicator_view_default_selected_indicator_color)
    private val defaultUnselectedIndicatorColor by color(R.color.indicator_view_default_unselected_indicator_color)
    private val defaultIndicatorWidth by dimensionPixelSize(R.dimen.indicator_view_default_indicator_width)
    private val defaultIndicatorHeight by dimensionPixelSize(R.dimen.indicator_view_default_indicator_height)
    private val defaultIndicatorPadding by dimensionPixelOffset(R.dimen.indicator_view_default_indicator_padding)

    private val selectedIndicatorBounds = RectF()
    private val defaultUnselectedIndicatorBounds = RectF()

    private val selectedIndicatorPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = selectedIndicatorColor
        strokeWidth = indicatorHeight.toFloat()
        strokeCap = Paint.Cap.ROUND
    }
    private val unselectedIndicatorPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = unselectedIndicatorColor
        strokeWidth = indicatorHeight.toFloat()
        strokeCap = Paint.Cap.ROUND
    }

    private val selectedIndicatorPath = Path()
    private val unselectedIndicatorPaths: MutableList<Path> = mutableListOf()

    private var currentIndicatorIndex: CurrentIndicatorIndex = DEFAULT_CURRENT_PAGE_INDEX
        set(value) {
            field = value.coerceAtLeast(0).coerceAtMost(indicatorCount - 1)
        }

    @ColorInt
    private var selectedIndicatorColor = defaultSelectedIndicatorColor
        set(value) {
            field = value

            selectedIndicatorPaint.color = value
        }
    @ColorInt
    private var unselectedIndicatorColor = defaultUnselectedIndicatorColor
        set(value) {
            field = value

            unselectedIndicatorPaint.color = value
        }
    @Dimension
    private var indicatorWidth = defaultIndicatorWidth
    @Dimension
    private var indicatorHeight = defaultIndicatorHeight
        set(value) {
            field = value

            selectedIndicatorPaint.strokeWidth = value.toFloat()
            unselectedIndicatorPaint.strokeWidth = value.toFloat()
        }
    @Dimension
    private var indicatorPadding = defaultIndicatorPadding

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        context.obtainStyledAttributes(attrs, R.styleable.IndicatorView, defStyleAttr, defStyleRes).apply {
            selectedIndicatorColor = getColor(R.styleable.IndicatorView_selectedIndicatorColor, selectedIndicatorColor)
            unselectedIndicatorColor = getColor(R.styleable.IndicatorView_unselectedIndicatorColor, unselectedIndicatorColor)
            indicatorWidth = getDimensionPixelSize(R.styleable.IndicatorView_indicatorWidth, indicatorWidth)
            indicatorHeight = getDimensionPixelSize(R.styleable.IndicatorView_indicatorHeight, indicatorHeight)
            indicatorPadding = getDimensionPixelOffset(R.styleable.IndicatorView_indicatorPadding, indicatorPadding)

            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val unselectedIndicatorCount = indicatorCount.coerceAtLeast(1)
        val selectedWidth = indicatorWidth + (2 * indicatorPadding)
        val unselectedWidth = unselectedIndicatorCount * (indicatorWidth + (2 * indicatorPadding))
        val minWidth = min(MeasureSpec.getSize(widthMeasureSpec), 2 * (selectedWidth + unselectedWidth))

        val indicatorHeight = (2 * indicatorHeight) + (2 * indicatorPadding)
        val minHeight = min(MeasureSpec.getSize(heightMeasureSpec), indicatorHeight)

        super.onMeasure(measureSpec(minWidth, MeasureSpec.EXACTLY), measureSpec(minHeight, MeasureSpec.EXACTLY))
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val centerX = centerXWithRespectToPadding(w)
        val centerY = centerYWithRespectToPadding(h)
        val startX = (centerX - (indicatorWidth / 2)).toFloat()
        val startY = (centerY - (indicatorHeight / 2)).toFloat()
        selectedIndicatorBounds.set(startX, startY, startX + indicatorWidth, startY + indicatorHeight)

        defaultUnselectedIndicatorBounds.set(0f,
                selectedIndicatorBounds.bottom - indicatorHeight,
                indicatorWidth.toFloat(),
                selectedIndicatorBounds.bottom)

        setToCurrentState()
    }

    override fun onDraw(canvas: Canvas) {
        unselectedIndicatorPaths.forEach { canvas.drawPath(it, unselectedIndicatorPaint) }
        canvas.drawPath(selectedIndicatorPath, selectedIndicatorPaint)
    }

    override fun onAdapterChanged(viewPager: ViewPager, oldAdapter: PagerAdapter?, newAdapter: PagerAdapter?) {
        indicatorCount = newAdapter?.count ?: DEFAULT_PAGE_COUNT
        currentIndicatorIndex = viewPager.currentItem

        setToCurrentState()
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        currentIndicatorIndex = position

        updateState(currentIndicatorIndex, positionOffset, DEFAULT_SCROLL_Y_FACTOR)
    }

    override fun onPageSelected(position: Int) {
        currentIndicatorIndex = position

        setToCurrentState()
    }

    fun updateState(currentIndicatorIndex: CurrentIndicatorIndex, scrollXFactor: Factor, scrollYFactor: Factor) {
        this.currentIndicatorIndex = currentIndicatorIndex

        // Create the Selected Indicator Path
        selectedIndicatorPath.createIndicator(bounds = selectedIndicatorBounds, curveFactor = scrollYFactor)

        // Create the Unselected Indicator Paths
        unselectedIndicatorPaths.clear()

        if (indicatorCount > 1) {
            val offset = (indicatorWidth + indicatorPadding) * scrollXFactor
            var startX = selectedIndicatorBounds.left - (currentIndicatorIndex * (indicatorWidth + indicatorPadding)) + offset

            unselectedIndicatorPaths.addAll(
                    (0 until indicatorCount).map {
                        val bounds = defaultUnselectedIndicatorBounds.copy()

                        bounds.offsetTo(startX, defaultUnselectedIndicatorBounds.top)

                        startX += bounds.width() + indicatorPadding

                        val path = Path()

                        path.createIndicator(bounds, if (it == currentIndicatorIndex) scrollYFactor else 0f)

                        path
                    })
        }

        invalidate()
    }

    private fun Path.createIndicator(bounds: RectF, curveFactor: Factor) {
        reset()
        moveTo(bounds.left, bounds.bottom)

        val centerX = bounds.centerX()
        val centerY = bounds.bottom + (curveFactor * 3 * bounds.height())

        quadTo(centerX, centerY, bounds.right, bounds.bottom)
    }

    private fun setToCurrentState() = updateState(currentIndicatorIndex, DEFAULT_SCROLL_X_FACTOR, DEFAULT_SCROLL_Y_FACTOR)

    class BottomSheetIndicatorScrollCallback(private val indicatorView: IndicatorView?) : BottomSheetBehavior.BottomSheetCallback() {

        private var previousSlideOffset = 0f

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            val scrollFactor = if (previousSlideOffset > slideOffset) slideOffset else -slideOffset

            indicatorView?.let { it.updateState(it.currentIndicatorIndex, 0f, scrollFactor) }

            previousSlideOffset = slideOffset
        }

        override fun onStateChanged(bottomSheet: View, newState: Int) {}
    }
}