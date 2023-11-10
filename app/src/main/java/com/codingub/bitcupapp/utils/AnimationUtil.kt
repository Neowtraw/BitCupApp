package com.codingub.bitcupapp.utils

import android.view.animation.AlphaAnimation




object AnimationUtil {

    fun clickAnimation(): AlphaAnimation? {
        return AlphaAnimation(1f, 0.2f)
    }
}