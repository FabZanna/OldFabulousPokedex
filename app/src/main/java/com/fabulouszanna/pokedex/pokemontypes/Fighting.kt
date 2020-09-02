package com.fabulouszanna.pokedex.pokemontypes

object Fighting : IWeaknessResistance {
    override fun getWeaknesses(): List<String> = listOf("Flying", "Psychic", "Fairy")

    override fun getResistances(): List<String>? = listOf("Bug", "Rock", "Dark")

    override fun getImmunities(): List<String>? = null
}