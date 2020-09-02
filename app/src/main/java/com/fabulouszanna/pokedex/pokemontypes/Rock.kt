package com.fabulouszanna.pokedex.pokemontypes

object Rock : IWeaknessResistance {
    override fun getWeaknesses(): List<String> = listOf("Water", "Grass", "Fighting", "Ground", "Steel")

    override fun getResistances(): List<String>? = listOf("Normal", "Fire", "Poison", "Flying")

    override fun getImmunities(): List<String>? = null
}