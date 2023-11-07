package com.codingub.bitcupapp.data.prefs

import android.content.Context
import android.content.SharedPreferences
import com.codingub.bitcupapp.App
import javax.inject.Singleton

@Singleton
object AuthConfig {

    private val key_user_auth = "auth_token"

    private val prefs: SharedPreferences by lazy {
        App.getInstance().getSharedPreferences(
            "${App.getInstance().packageName}_${this::class.java.simpleName}",
            Context.MODE_PRIVATE
        )
    }
    private val editor: SharedPreferences.Editor get() = prefs.edit()

    private var key: String = ""

    fun getKey(): String = prefs.getString(key_user_auth, "") ?: ""
    fun setKey(value: String) {
        key = value
        editor.putString(key_user_auth, key).commit()
    }
}