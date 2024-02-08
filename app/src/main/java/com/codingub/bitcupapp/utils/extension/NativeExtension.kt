package com.codingub.bitcupapp.utils.extension

import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.core.graphics.ColorUtils
import com.codingub.bitcupapp.utils.AndroidUtil

val Int.dp get() = AndroidUtil.dp(this)

@ColorInt
fun Int.mixWith(
    @ColorInt color: Int,
    @FloatRange(from = 0.0, to = 1.0) ratio: Float
): Int = ColorUtils.blendARGB(this, color, ratio)

/*
    Float
 */

val Float.dp get() = AndroidUtil.dp(this)

/*
    Boolean
*/

fun Boolean.asInt(): Int = if (this) 1 else 0

fun Boolean.asFloat(): Float = asInt().toFloat()