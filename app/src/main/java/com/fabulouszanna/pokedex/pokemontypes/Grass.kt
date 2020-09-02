package com.fabulouszanna.pokedex.pokemontypes

object Grass : IWeaknessResistance {
    override fun getWeaknesses(): List<String> = listOf("Fire", "Ice", "Poison", "Flying", "Bug")

    override fun getResistances(): List<String>? = listOf("Water", "Electric", "Grass", "Ground")

    override fun getImmunities(): List<String>? = null
}