package com.gaurav.multiprogressbar

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.core.graphics.PathSegment
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

    var progress: Int = 0
        private set


    private val segmentPaints: MutableList<Paint> = mutableListOf()
    private val segmentPaths: MutableList<Path> = mutableListOf()


    init {
        context.obtainStyledAttributes(attrs, R.styleable.MultiProgressBar, defStyleAttr, 0).run {
            segmentCount = getInteger(R.styleable.MultiProgressBar_gst_count, segmentCount)
            startHour = getFloat(R.styleable.MultiProgressBar_gst_start_hour, startHour)
            endHour = getFloat(R.styleable.MultiProgressBar_gst_end_hour, endHour)

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

//            val fontFamilyId: Int = this.getResourceId(R.styleable.MultiProgressBar_android_fontFamily, 0)

            dataFormat = this.getInt(R.styleable.MultiProgressBar_gst_data_format, dataFormat)

            textPaint.setColor(textColor)
            textPaint.textSize = textSize
            textPaint.isAntiAlias = true
//            if (fontFamilyId > 0) {
//                textPaint.typeface = ResourcesCompat.getFont(getContext(), fontFamilyId)
//            }


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


    fun setProgress(progress: ArrayList<Pair<Float, Float>>) {
        doOnLayout {
            ProgressRectF = ArrayList()
            (0 until progress.size).forEach {
                ProgressRectF.add(getProgressRect(progress.get(it)))
//            canvas.drawRoundRect(540F, 5F, w, (h * 0.25F ), 50F, 50F, seconderProgressPaint)
            }
            invalidate()
        }

    }

    fun getProgressRect(pair: Pair<Float, Float>): RectF {
        var rectF: RectF
        w = width.toFloat()
        h = height.toFloat()
//        if (pair.first < startHour || pair.first > endHour) {
//            throw  java.lang.Exception("range should be between the start and end hour")
//        } else if (pair.second < startHour || pair.second > endHour) {
//            throw  java.lang.Exception("range should be between the start and end hour")
//        } else {

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


    fun generateHours(startHour: Float, endHour: Float, segment: Int): ArrayList<Int> {

        val range: Int = Math.abs(startHour - endHour).toInt() // calculate the range

        val interval = range / 4 // calculate the interval size

        val numbers = arrayListOf<Int>() // create an array to store the 5 numbers


        numbers.add((startHour + interval).toInt()) // calculate the first number
        for (i in 1..5) {
            numbers.add(numbers.get(i - 1) + interval) // calculate the remaining 4 numbers
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
                val generateHours = generateHours(startHour, endHour, segmentCount)
                (0 until generateHours.size -1).forEach {

                    canvas.drawLine(
                        ((generateHours.get(it) - startHour) / (endHour - startHour)) * w,
                        h * 0.40F,
                        ((generateHours.get(it) - startHour) / (endHour - startHour)) * w,
                        h - textSize,
                        textPaint
                    )

                    hours = generateHours.get(it)
                    if (generateHours.get(it) > 23) {
                        hours = Math.abs(24 - generateHours.get(it))
                    }

                    canvas.drawText(
                        hours.toString() + ":00",
                        ((generateHours.get(it) - startHour) / (endHour - startHour)) * w - textSize ,
                        h,
                        textPaint
                    )


                }

            }


            canvas.drawRoundRect(5F, 5F, w, h * 0.40F, 50F, 50F, primaryProgressPaint)

            ProgressRectF?.let {
                (0 until it.size).forEach { index ->
                    canvas.drawRoundRect(it.get(index), 50F, 50F, seconderProgressPaint)
                }

            }
        } else {

        }


//
//
//
//        canvas.drawRoundRect(5F, 5F, 105F, (h * 0.25F), 50F, 50F, seconderProgressPaint)
//        canvas.drawRoundRect(205F, 5F, 405F, (h * 0.25F), 50F, 50F, seconderProgressPaint)
//
//        canvas.drawRoundRect(450F, 5F, 525F, (h * 0.25F), 50F, 50F, seconderProgressPaint)
//
//        canvas.drawRoundRect(550F, 5F, w, (h * 0.25F), 50F, 50F, seconderProgressPaint)


//        (0 until segmentCount).forEach { position ->
//            val path = segmentPaths[position]
//            val paint = segmentPaints[position]
//            val segmentCoordinates =
//                segmentCoordinatesComputer.segmentCoordinates(
//                    position,
//                    segmentCount,
//                    w,
//                    h * 0.5F,
//                    spacing
//                )
//
//            drawSegment(canvas, path, paint, segmentCoordinates, segmentColor, segmentAlpha)
//        }
//
//        animatedProgressSegmentCoordinates?.let {
//            drawSegment(
//                canvas,
//                progressPath,
//                progressPaint,
//                it,
//                progressColor,
//                progressAlpha
//            )
//        }
    }
}