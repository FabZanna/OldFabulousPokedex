package com.fabulouszanna.pokedex.utilities

import android.content.Context
import com.google.gson.Gson
import org.json.JSONArray

class JSONParser(private val context: Context) {
    val gson = Gson()

    fun getJsonDataFromAssets(fileName: String): String =
        context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }

    inline fun <reified T> extractJsonData(fileName: String): List<T> {
        val jsonList = JSONArray(getJsonDataFromAssets(fileName))
        val extractedList = mutableListOf<T>()
        for (i in 0 until jsonList.length()) {
            val jsonObject = jsonList.getJSONObject(i).toString()
            val model = gson.fromJson(jsonObject, T::class.java)
            extractedList.add(model)
        }
        return extractedList
    }
}