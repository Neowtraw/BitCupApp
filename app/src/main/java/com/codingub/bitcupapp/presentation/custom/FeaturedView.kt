package com.codingub.bitcupapp.presentation.custom

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import com.codingub.bitcupapp.R
import com.codingub.bitcupapp.common.FeaturedType
import com.codingub.bitcupapp.utils.DrawableUtil
import com.codingub.bitcupapp.utils.Font
import com.codingub.bitcupapp.utils.Resource
import com.codingub.bitcupapp.utils.extension.asFloat
import com.codingub.bitcupapp.utils.extension.dp
import com.codingub.bitcupapp.utils.extension.mixWith

@SuppressLint("ViewConstructor")
class FeaturedView(
    context: Context,
    val category: FeaturedType
) : AppCompatTextView(context) {

    var isChecked: Boolean = false
        private set
    private var checkProgress: Float = isChecked.asFloat()
        set(value) {
            field = value
            background = DrawableUtil.rect(
                color = colorBg.mixWith(colorBgChecked, ratio = value),
                corner = 24f.dp
            )
            setTextColor(colorText.mixWith(colorTextChecked, ratio = value))
        }

    private val colorBg: Int = Resource.color(R.color.background_add)
    private val colorBgChecked: Int = Resource.color(R.color.contrast)
    private val colorText: Int = Resource.color(R.color.add_text)
    private val colorTextChecked: Int = Resource.color(R.color.text_color_checked)


    init {
        setPadding(20.dp, 10.dp, 20.dp, 10.dp)
        typeface = Font.REGULAR
        isSingleLine = true
        ellipsize = TextUtils.TruncateAt.END
        gravity = Gravity.CENTER
        text = category.name
        setTextColor(Resource.color(R.color.text_color))
        textSize = 5f.dp
        checkProgress = isChecked.asFloat()
    }

    fun setChecked(checked: Boolean, animated: Boolean) {
        //  typeface = if (checked) Font.SEMIBOLD else Font.REGULAR

        if (animated) {
            ObjectAnimator.ofFloat(this, "checkProgress", checkProgress, checked.asFloat()).apply {
                duration = 200
            }.also { it.start() }
        } else {
            checkProgress = checked.asFloat()
        }

        isChecked = checked
    }

}