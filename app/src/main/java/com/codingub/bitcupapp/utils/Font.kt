package com.codingub.bitcupapp.utils

import android.graphics.Typeface
import com.codingub.bitcupapp.App

object Font {

    private val fonts: MutableMap<String, Typeface> = mutableMapOf()

    private fun font(style: String): Typeface {
        if (!fonts.containsKey(style)) {
            fonts[style] = Typeface.createFromAsset(
                App.getInstance().assets, "font/mulish_$style.ttf"
            )
        }

        return fonts[style]!!
    }

    val REGULAR: Typeface get() = font("regular")
    val LIGHT: Typeface get() = font("light")
    val BOLD: Typeface get() = font("bold")
}