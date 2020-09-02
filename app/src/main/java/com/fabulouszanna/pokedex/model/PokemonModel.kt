package com.fabulouszanna.pokedex.model

data class PokemonModel(
    val name: String,
    val id: String,
    val imgUrl: String,
    val type1: String,
    val type2: String? = null,
    val gen: String,
    val isVariation: Boolean,
    val species: String,
    val description: String,
    val height: String,
    val weight: String,
    val malePercentage: String,
    val femalePercentage: String,
    val hp: String,
    val attack: String,
    val defense: String,
    val specialAtk: String,
    val specialDef: String,
    val speed: String,
    val abilities: List<String>,
    val hiddenAbility: String? = null
)