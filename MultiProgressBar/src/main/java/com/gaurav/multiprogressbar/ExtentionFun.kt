package com.gaurav.multiprogressbar

import androidx.annotation.FloatRange

object  ExtentionFun {
    fun Float.lerp(
        end: Float,
        @FloatRange(from = 0.0, to = 1.0) amount: Float
    ): Float =
        this * (1 - amount.coerceIn(0f, 1f)) + end * amount.coerceIn(0f, 1f)


    fun Float.toAlphaPaint(): Int = (this * 255).toInt()

}