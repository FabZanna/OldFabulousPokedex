package com.fabulouszanna.pokedex.pokemontypes

object Dark : IWeaknessResistance {
    override fun getWeaknesses(): List<String> = listOf("Bug", "Fairy", "Fighting")

    override fun getResistances(): List<String>? = listOf("Dark", "Ghost")

    override fun getImmunities(): List<String>? = listOf("Psychic")
}