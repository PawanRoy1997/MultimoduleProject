package com.example.common.progressbars

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.example.common.R.styleable.*

enum class IndicatorType {
    LINE, CIRCLE, SQUARE
}

private const val DEFAULT_INDICATOR_HEIGHT = 10f
private const val DEFAULT_INDICATOR_THICKNESS = 5f
private const val DEFAULT_BAR_THICKNESS = 4f
private const val DEFAULT_GOAL_REACHED_COLOR = Color.BLUE
private const val DEFAULT_GOAL_NOT_REACHED_COLOR = Color.BLACK
private const val DEFAULT_UNFILLED_SECTION_COLOR = Color.RED

private const val PROGRESS_KEY = "progress"
private const val GOAL_KEY = "goal"
private const val SUPER_STATE_KEY = "superState"

class GoalProgressBar(context: Context, attrs: AttributeSet) : View(context, attrs) {
    // Current progress which is complete
    var progress = 0
        set(value) {
            setProgress(value, true)
        }

    // Goal or intermediate where we want to put marker
    var goal = 0
        set(value) {
            field = value
            postInvalidate()
        }

    // Dimensions of Progressbar and marker
    private var goalIndicatorHeight = 0f
        set(value) {
            field = value
            postInvalidate()
        }
    private var goalIndicatorThickness = 0f
        set(value) {
            field = value
            postInvalidate()
        }
    private var goalReachedColor = 0
        set(value) {
            field = value
            postInvalidate()
        }
    private var goalNotReachedColor = 0
        set(value) {
            field = value
            postInvalidate()
        }
    private var unfilledSectionColor = 0
        set(value) {
            field = value
            postInvalidate()
        }
    private var barThickness = 0f
        set(value) {
            field = value
            postInvalidate()
        }

    private var progressPaint: Paint = Paint()
    private var indicatorType: IndicatorType = IndicatorType.SQUARE
        set(value) {
            field = value
            postInvalidate()
        }
    private lateinit var barAnimator: ValueAnimator

    init {
        progressPaint = Paint().apply {
            style = Paint.Style.FILL_AND_STROKE
        }
        val array = context.theme.obtainStyledAttributes(attrs, GoalProgressBar, 0, 0)
        try {
            goalIndicatorHeight = array.getDimension(
                GoalProgressBar_goalIndicatorHeight, DEFAULT_INDICATOR_HEIGHT
            )
            goalIndicatorThickness = array.getDimension(
                GoalProgressBar_goalIndicatorThickness, DEFAULT_INDICATOR_THICKNESS
            )
            goalReachedColor = array.getColor(
                GoalProgressBar_goalReachedColor, DEFAULT_GOAL_REACHED_COLOR
            )
            goalNotReachedColor =
                array.getColor(GoalProgressBar_goalNotReachedColor, DEFAULT_GOAL_NOT_REACHED_COLOR)
            unfilledSectionColor =
                array.getColor(GoalProgressBar_unfilledSectionColor, DEFAULT_UNFILLED_SECTION_COLOR)
            barThickness = array.getDimension(GoalProgressBar_barThickness, DEFAULT_BAR_THICKNESS)
            val index = array.getInt(GoalProgressBar_indicatorType, 0)
            indicatorType = IndicatorType.values()[index]
        } finally {
            array.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        val halfHeight = height / 2f
        val progressEndX: Float = (width * progress / 100f)

        // draw the filled portion of the bar
        progressPaint.strokeWidth = barThickness
        progressPaint.color = progressBarColor()
        canvas.drawLine(0f, halfHeight, progressEndX, halfHeight, progressPaint)

        // draw unfilled portion
        progressPaint.color = unfilledSectionColor
        canvas.drawLine(progressEndX, halfHeight, width.toFloat(), halfHeight, progressPaint)

        //draw goal indicator
        val indicatorPosition = (width * goal / 100f)
        progressPaint.apply {
            this.color = goalReachedColor
            this.strokeWidth = goalIndicatorThickness
        }
        when (indicatorType) {
            IndicatorType.LINE -> canvas.drawLine(
                indicatorPosition,
                halfHeight - goalIndicatorHeight / 2,
                indicatorPosition,
                halfHeight - goalIndicatorHeight / 2,
                progressPaint
            )
            IndicatorType.SQUARE -> canvas.drawRect(
                indicatorPosition - goalIndicatorHeight / 2, 0f,
                indicatorPosition + goalIndicatorHeight / 2,
                goalIndicatorHeight,
                progressPaint
            )
            IndicatorType.CIRCLE -> canvas.drawCircle(
                indicatorPosition, halfHeight, halfHeight, progressPaint
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)
        val height: Int = when (MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.EXACTLY -> specHeight
            MeasureSpec.AT_MOST -> goalIndicatorHeight.toInt().coerceAtMost(specHeight)
            else -> specHeight
        }

        setMeasuredDimension(width, height)
    }

    private fun progressBarColor() = if (progress >= goal) goalReachedColor else goalNotReachedColor

    override fun onSaveInstanceState(): Parcelable {
        return Bundle().apply {
            putInt(PROGRESS_KEY, progress)
            putInt(GOAL_KEY, goal)
            putParcelable(SUPER_STATE_KEY, super.onSaveInstanceState())
        }
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val parcelableState = if (state is Bundle) {
            progress = state.getInt(PROGRESS_KEY)
            goal = state.getInt(GOAL_KEY)
            state.getParcelable(SUPER_STATE_KEY)
        } else state
        super.onRestoreInstanceState(parcelableState)
    }

    fun setProgress(progress: Int, animate: Boolean) {
        if (animate) {
            barAnimator = ValueAnimator.ofFloat(0f, 1f)
            barAnimator.duration = 700

            // reset Progress without animating
            setProgress(0, false)
            barAnimator.interpolator = DecelerateInterpolator()
            barAnimator.addUpdateListener { animator ->
                val interpolation = animator.animatedValue as Float
                setProgress((interpolation * progress).toInt(), false)
            }
            if (!barAnimator.isStarted) {
                barAnimator.start()
            }
        } else {
            this.progress = progress
            postInvalidate()
        }
    }
}