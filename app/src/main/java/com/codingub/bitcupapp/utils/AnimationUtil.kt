package com.codingub.bitcupapp.utils

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.TranslateAnimation


object AnimationUtil {

    fun clickAnimation(): AlphaAnimation? {
        return AlphaAnimation(1f, 0.2f)
    }

    fun animateNavBar(view: View, show: Boolean, onAnimationEnd: () -> Unit = {}) {
        val animation = TranslateAnimation(
            Animation.RELATIVE_TO_PARENT, 0.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f,
            Animation.RELATIVE_TO_PARENT, if(show) 1.0f else 0.0f,
            Animation.RELATIVE_TO_PARENT, if(show) 0.0f else 1.0f
        ).apply {
            duration = 600L
            fillAfter = true
        }
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                if(show) view.visibility = View.VISIBLE
            }
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                if(!show) view.visibility = View.GONE
                onAnimationEnd()
            }
        })

        view.startAnimation(animation)
    }
}