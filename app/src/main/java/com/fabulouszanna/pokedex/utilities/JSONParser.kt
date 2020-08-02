package com.fabulouszanna.pokedex.utilities

import android.content.Context
import com.fabulouszanna.pokedex.repo.PokemonEntity
import com.google.gson.Gson
import org.json.JSONArray

class JSONParser(private val context: Context) {
    private fun getJsonDataFromAssets(fileName: String): String =
        context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }

    fun extractJsonData(fileName: String): List<PokemonEntity> {
        val jsonList = JSONArray(getJsonDataFromAssets(fileName))
        val gson = Gson()
        val extractedList = mutableListOf<PokemonEntity>()
        for (i in 0 until jsonList.length()) {
            val jsonObject = jsonList.getJSONObject(i).toString()
            val pokemonModel = gson.fromJson(jsonObject, PokemonEntity::class.java)
            extractedList.add(pokemonModel)
        }
        return extractedList
    }
}