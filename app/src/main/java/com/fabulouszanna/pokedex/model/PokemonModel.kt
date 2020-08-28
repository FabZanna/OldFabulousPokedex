package com.fabulouszanna.pokedex.model

data class PokemonModel (
    val name: String,
    val id: String,
    val imgUrl: String,
    val type1: String,
    val type2: String? = null,
    val gen: String,
    val isVariation: Boolean,
    val species: String
)