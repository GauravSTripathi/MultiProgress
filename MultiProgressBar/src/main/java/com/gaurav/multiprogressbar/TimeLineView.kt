package com.gaurav.multiprogressbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.view.doOnLayout
import java.text.DecimalFormat
import java.text.NumberFormat

class TimeLineView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var w = 0F
    private var h = 0F
    private var dataFormat = 0
    private var ProgressRectF = java.util.ArrayList<RectF>()
    private var seconderProgressPaint = Paint()
    private var primaryProgressPaint = Paint()
    private var textPaint = Paint()

    var progress_radius: Float = 10F
        set(value) {
            if (field != value) {
                field = value
                invalidate()
            }
        }

    private var textSize = 5F
        get() = field
        set(value) {
            if (field != value) {
                field = value
                invalidate()
            }
        }
    private var textColor = Color.WHITE
        get() = field
        set(value) { if (field != value) {
            field = value
            invalidate()
        }
        }
    private var textSegmentsWidth = 1
    private var startTimeMilli = 0L
        get() = field
        set(value) { if (field != value) {
            field = value
            invalidate()
        }
        }
    private var endTimeMilli = 10L
        get() = field
        set(value) { if (field != value) {
            field = value
            invalidate()
        }}
    private var progressAlpha: Float = 1f
        set(value) {
            if (field != value) {
                field = value.coerceIn(0f, 1f)
                invalidate()
            }
        }

    @get:ColorInt
    var seconderProgressColor: Int = Color.GREEN
        set(value) {
            if (field != value) {
                field = value
                invalidate()
            }
        }

    @get:ColorInt
    var primaryProgressColor: Int = Color.GRAY
        set(value) {
            if (field != value) {
                field = value
                invalidate()
            }
        }
    var startTimeDisplay: String = ""
        set(value) {
            if (field != value) {
                field = value
                invalidate()
            }
        }


    var endTimeDisplay: String = ""
        set(value) {
            if (field != value) {
                field = value
                invalidate()
            }
        }


    var showHours: Boolean = true
        set(value) {
            if (field != value) {
                field = value
                invalidate()
            }
        }

    var is24Hour: Boolean = true
        set(value) {
            if (field != value) {
                field = value
                invalidate()
            }
        }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.TimeLineView, defStyleAttr, 0).run {
            startTimeMilli =
                getString(R.styleable.TimeLineView_tlv_start_time_in_milli)?.toLong() ?: 0
            endTimeMilli = getString(R.styleable.TimeLineView_tlv_end_time_in_milli)?.toLong() ?: 0

            progress_radius =
                getFloat(R.styleable.TimeLineView_tlv_progress_radius, progress_radius)

            progressAlpha = getFloat(R.styleable.TimeLineView_tlv_progressAlpha, progressAlpha)
            seconderProgressColor = getColor(
                R.styleable.TimeLineView_tlv_seconder_progress_color,
                seconderProgressColor
            )
            primaryProgressColor = getColor(
                R.styleable.TimeLineView_tlv_primary_progress_color,
                primaryProgressColor
            )
            textSize = getFloat(R.styleable.TimeLineView_tlv_textSize, textSize)
            textColor = getColor(R.styleable.TimeLineView_tlv_textColor, textColor)


            dataFormat = this.getInt(R.styleable.TimeLineView_tlv_data_format, dataFormat)


            startTimeDisplay =
                getString(R.styleable.TimeLineView_tlv_startTimeDisplay).toString()


            endTimeDisplay =
                getString(R.styleable.TimeLineView_tlv_endTimeDisplay).toString()

            showHours = getBoolean(R.styleable.TimeLineView_tlv_showHours, true)
            is24Hour = getBoolean(R.styleable.TimeLineView_tlv_is24Hours, false)

            textPaint.setColor(textColor)
            textPaint.textSize = textSize
            textPaint.isAntiAlias = true

            seconderProgressPaint.setColor(seconderProgressColor)
            seconderProgressPaint.isAntiAlias = true
            seconderProgressPaint.style = Paint.Style.FILL


            primaryProgressPaint.setColor(primaryProgressColor)
            primaryProgressPaint.isAntiAlias = true
            primaryProgressPaint.style = Paint.Style.FILL

            recycle()


        }
    }

    fun setTime(sleepStartTimeInMilli: Long, sleepEndTimeInMilli: Long) {
        startTimeMilli = sleepStartTimeInMilli
        endTimeMilli = sleepEndTimeInMilli
        invalidate()
    }

    fun setProgress(progress: ArrayList<Pair<Long, Long>>) {


        doOnLayout {
            ProgressRectF = ArrayList()
            (0 until progress.size).forEach {
                ProgressRectF.add(getProgressRect(progress.get(it)))
            }
            invalidate()
        }

    }

    fun getProgressRect(pair: Pair<Long, Long>): RectF {
        var rectF: RectF
        w = width.toFloat()
        h = height.toFloat()

        var first = pair.first
        var second = pair.second

        if((pair.second - pair.first) < 180000){
            second = (pair.first + ((endTimeMilli*3)/width))
        }
        if((pair.second - pair.first) < 180000){
            second = (pair.first + 180000)
        }


        val left = ((first.toFloat() - startTimeMilli.toFloat()) / (endTimeMilli.toFloat() - startTimeMilli.toFloat())).toFloat() * w
        val right = ((second.toFloat() - startTimeMilli.toFloat()) / (endTimeMilli.toFloat() - startTimeMilli.toFloat())) * w
        rectF = RectF(left, 5F, right, (h * 0.40F))
//        }
        return rectF
    }


    override fun onDraw(canvas: Canvas) {
        w = width.toFloat()
        h = height.toFloat()



        if (dataFormat == 0) {


            if (showHours) {
                if (is24Hour) {
                    canvas.drawText(
                        startTimeMilli.toInt().toString() + ":00",
                        0F,
                        h,
                        textPaint
                    )
                } else {

                    canvas.drawText(
                        if (!startTimeDisplay.isNullOrEmpty()) {
                            startTimeDisplay
                        } else {
                            startTimeDisplay
                        },
                        0F,
                        h,
                        textPaint
                    )
                }


                canvas.drawLine(
                    0F + textSize,
                    h * 0.40F,
                    0F + textSize,
                    h - textSize,
                    textPaint
                )

                if (is24Hour) {
                    canvas.drawText(
                        endTimeMilli.toInt().toString() + ":00",
                        0F,
                        h,
                        textPaint
                    )
                } else {

                    canvas.drawText(
                        if (!endTimeDisplay.isNullOrEmpty()) {
                            endTimeDisplay
                        } else {
                            endTimeDisplay
                        },
                        w - (endTimeDisplay.length * 12),
                        h,
                        textPaint
                    )
                }


                canvas.drawLine(
                    w - textSize,
                    h * 0.40F,
                    w - textSize,
                    h - textSize,
                    textPaint
                )
            }

            canvas.drawRoundRect(5F, 5F, w, h * 0.40F, 50F, 50F, primaryProgressPaint)
            ProgressRectF?.let {
                (0 until it.size).forEach { index ->
                    canvas.drawRoundRect(
                        it.get(index),
                        progress_radius,
                        progress_radius,
                        seconderProgressPaint
                    )
                }

            }
        } else {

        }

    }


}