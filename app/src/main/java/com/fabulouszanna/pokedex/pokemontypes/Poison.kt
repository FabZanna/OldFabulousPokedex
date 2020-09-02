package com.fabulouszanna.pokedex.pokemontypes

object Poison : IWeaknessResistance {
    override fun getWeaknesses(): List<String> = listOf("Ground", "Psychic")

    override fun getResistances(): List<String>? = listOf("Grass", "Fighting", "Poison", "Bug", "Fairy")

    override fun getImmunities(): List<String>? = null
}