package com.codingub.bitcupapp.data.local

import androidx.room.TypeConverter
import com.codingub.bitcupapp.data.utils.SrcType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object TypeConverter {

    @TypeConverter
    @JvmStatic
    fun srcTypeToString(type: SrcType): String {
        return Gson().toJson(type)
    }

    @TypeConverter
    @JvmStatic
    fun stringToSrcType(str: String): SrcType {
        return Gson().fromJson(str, object : TypeToken<SrcType>() {}.type)
    }
}