package com.fabulouszanna.pokedex.utilities

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromStringToList(string: String?): List<String?>? {
        val objectType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(string, objectType)
    }

    @TypeConverter
    fun fromListToString(list: List<String?>?): String {
        val objectType = object : TypeToken<List<String>>() {}.type
        return Gson().toJson(list, objectType)
    }
}