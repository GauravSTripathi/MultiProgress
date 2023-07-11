package com.gaurav.multiprogressbar

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.core.view.doOnLayout

class MultiProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var w=0F
    private var h=0F
    private var dataFormat = 0
    private var ProgressRectF = java.util.ArrayList<RectF>()
    private var seconderProgressPaint = Paint()
    private var primaryProgressPaint = Paint()
    private var textPaint = Paint()
    private var textSize = 5F
    private var textColor = Color.WHITE
    private var textSegmentsWidth = 1
    private var startHour = 0F
    private var endHour = 10F
    var progress_radius : Float = 10F
        set(value) {
            if (field != value) {
                field = value
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

    var spacing: Float = 0f
        set(value) {
            if (field != value) {
                field = value
                invalidate()
            }
        }

    @FloatRange(from = 0.0, to = 1.0)
    var segmentAlpha: Float = 1f
        set(value) {
            if (field != value) {
                field = value.coerceIn(0f, 1f)
                invalidate()
            }
        }

    @FloatRange(from = 0.0, to = 1.0)
    var progressAlpha: Float = 1f
        set(value) {
            if (field != value) {
                field = value.coerceIn(0f, 1f)
                invalidate()
            }
        }

    var segmentCount: Int = 1

    var progressDuration: Long = 300L

    var progressInterpolator: LinearInterpolator = LinearInterpolator()

    private val segmentPaints: MutableList<Paint> = mutableListOf()
    private val segmentPaths: MutableList<Path> = mutableListOf()


    init {
        context.obtainStyledAttributes(attrs, R.styleable.MultiProgressBar, defStyleAttr, 0).run {
            segmentCount = getInteger(R.styleable.MultiProgressBar_gst_count, segmentCount)
            startHour = getFloat(R.styleable.MultiProgressBar_gst_start_hour, startHour)
            endHour = getFloat(R.styleable.MultiProgressBar_gst_end_hour, endHour)
            progress_radius = getFloat(R.styleable.MultiProgressBar_gst_progress_radius,progress_radius)

            segmentAlpha = getFloat(R.styleable.MultiProgressBar_gst_segmentAlpha, segmentAlpha)
            progressAlpha = getFloat(R.styleable.MultiProgressBar_gst_progressAlpha, progressAlpha)
            seconderProgressColor = getColor(
                R.styleable.MultiProgressBar_gst_seconder_progress_color,
                seconderProgressColor
            )
            primaryProgressColor = getColor(
                R.styleable.MultiProgressBar_gst_primary_progress_color,
                primaryProgressColor
            )
            textSize = getFloat(R.styleable.MultiProgressBar_gst_textSize, textSize)
            textColor = getColor(R.styleable.MultiProgressBar_gst_textColor, textColor)
            spacing = getDimension(R.styleable.MultiProgressBar_gst_spacing, spacing)
            progressDuration = getInteger(
                R.styleable.MultiProgressBar_gst_duration,
                progressDuration.toInt()
            ).toLong()

            dataFormat = this.getInt(R.styleable.MultiProgressBar_gst_data_format, dataFormat)

            textPaint.setColor(textColor)
            textPaint.textSize = textSize
            textPaint.isAntiAlias = true

            seconderProgressPaint.setColor(seconderProgressColor)
            seconderProgressPaint.isAntiAlias = true
            seconderProgressPaint.style = Paint.Style.FILL


            primaryProgressPaint.setColor(primaryProgressColor)
            primaryProgressPaint.isAntiAlias = true
            primaryProgressPaint.style = Paint.Style.FILL

            if (endHour<startHour)
            {
                endHour = 24 + endHour
            }
            recycle()
        }

    }

    fun setHours(sleepStartHour:Float,sleepEndHour:Float)
    {
        startHour=sleepStartHour
        endHour=sleepEndHour
    }

    fun setProgress(progress: ArrayList<Pair<Float, Float>>) {
        if (endHour<startHour)
        {
            endHour = 24 + endHour
        }

        var isNightSleep = progress.filter { it.second < it.first }

        if (isNightSleep!=null && isNightSleep.size>0) {
            progress.forEachIndexed { index, item ->

                if (item.first >= startHour) {
                    progress.set(index, Pair(item.first - 24, progress[index].second))
//                newPair.first = item.first - 24
                }
                if (item.second >= startHour) {
                    progress.set(index, Pair(progress[index].first, item.second - 24))
                }
            }
        }

        doOnLayout {
            ProgressRectF = ArrayList()
            (0 until progress.size).forEach {
                ProgressRectF.add(getProgressRect(progress.get(it)))
            }
            invalidate()
        }

    }

    fun getProgressRect(pair: Pair<Float, Float>): RectF {
        var rectF: RectF
        w = width.toFloat()
        h = height.toFloat()

        var first = pair.first
        var second = pair.second
        if(endHour>23)
        {
            first +=24
            second +=24
        }

            val left = ((first - startHour) / (endHour - startHour)) * w
            val right = ((second - startHour) / (endHour - startHour)) * w
            rectF = RectF(left, 5F, right, (h * 0.40F))
//        }
        return rectF
    }


    fun generateHours(startHour: Float, endHour: Float, segment: Int): MutableList<Int> {

        val range: Int = Math.abs(startHour - endHour).toInt() // calculate the range

        val interval = range / segment // calculate the interval size

        val numbers = arrayListOf<Int>() // create an array to store the segments


        numbers.add((startHour + interval).toInt()) // calculate the first number
        for (i in 1..segment) {
            numbers.add(numbers.get(i - 1) + interval) // calculate the remaining segments
        }

        return numbers

    }


    override fun onDraw(canvas: Canvas) {
        w = width.toFloat()
        h = height.toFloat()



        if (dataFormat == 0) {
            textSegmentsWidth = Math.abs((w / segmentCount).toInt())


            var hours = 0
            canvas.drawText(
                startHour.toInt().toString() + ":00",
                0F,
                h,
                textPaint
            )

            canvas.drawLine(
                0F + textSize,
                h * 0.40F,
                0F + textSize,
                h - textSize,
                textPaint
            )
            hours = endHour.toInt()

            if (hours > 23) {
                hours = Math.abs(24 - endHour).toInt()
            }

            canvas.drawText(
                hours.toString() + ":00",
                w - (textSize*2),
                h,
                textPaint
            )

            canvas.drawLine(
                w - textSize,
                h * 0.40F,
                w - textSize,
                h - textSize,
                textPaint
            )


            if (segmentCount > 2) {
                var generateHoursSegment = generateHours(startHour, endHour, segmentCount)
                (0 until generateHoursSegment.size).forEach {
                    hours = generateHoursSegment.get(it)
                    if (generateHoursSegment.get(it) > 23) {
                        hours = Math.abs(24 - generateHoursSegment.get(it))
                    }

                    var actualEndHour = if(endHour > 23) {
                        endHour.toInt()-24
                    }else{
                        endHour
                    }

                    if(hours!=actualEndHour){
                        canvas.drawLine(
                            ((generateHoursSegment.get(it) - startHour) / (endHour - startHour)) * w,
                            h * 0.40F,
                            ((generateHoursSegment.get(it) - startHour) / (endHour - startHour)) * w,
                            h - textSize,
                            textPaint
                        )

                        canvas.drawText(
                            hours.toString() + ":00",
                            ((generateHoursSegment.get(it) - startHour) / (endHour - startHour)) * w - textSize ,
                            h,
                            textPaint
                        )
                    }
                }

            }
            canvas.drawRoundRect(5F, 5F, w, h * 0.40F, 50F, 50F, primaryProgressPaint)
            ProgressRectF?.let {
                (0 until it.size).forEach { index ->
                    canvas.drawRoundRect(it.get(index), progress_radius, progress_radius, seconderProgressPaint)
                }

            }
        } else {

        }

    }
}