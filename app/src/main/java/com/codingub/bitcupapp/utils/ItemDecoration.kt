package com.codingub.bitcupapp.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.codingub.bitcupapp.utils.extension.dp

object ItemDecoration {

    fun createItemDecoration(space: Int): RecyclerView.ItemDecoration {
        return object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val spacing = space.dp
                outRect.apply {
                    left = spacing
                    top = spacing
                    right = spacing
                    bottom = spacing
                }
            }
        }
    }
}