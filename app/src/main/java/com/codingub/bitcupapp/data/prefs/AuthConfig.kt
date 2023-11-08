package com.codingub.bitcupapp.data.prefs

import android.content.Context
import android.content.SharedPreferences
import com.codingub.bitcupapp.App
import javax.inject.Singleton

@Singleton
object AuthConfig {

    private val prefs: SharedPreferences by lazy {
        App.getInstance().getSharedPreferences(
            "${App.getInstance().packageName}_${this::class.java.simpleName}",
            Context.MODE_PRIVATE
        )
    }
    private val editor: SharedPreferences.Editor get() = prefs.edit()

    private var key: String = "Q81tm36QnR3mZpRhOdAXBHyFkYRrytsQPJGIflwd0m5eelvgOOM77DYx"
    fun getKey() = key
}