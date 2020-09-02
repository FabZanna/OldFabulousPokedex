package com.fabulouszanna.pokedex.pokemontypes

object Psychic : IWeaknessResistance {
    override fun getWeaknesses(): List<String> = listOf("Bug", "Ghost", "Dark")

    override fun getResistances(): List<String>? = listOf("Psychic", "Fighting")

    override fun getImmunities(): List<String>? = null
}