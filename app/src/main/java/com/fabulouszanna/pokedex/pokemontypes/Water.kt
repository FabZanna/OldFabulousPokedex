package com.fabulouszanna.pokedex.pokemontypes

object Water : IWeaknessResistance {
    override fun getWeaknesses(): List<String> = listOf("Electric", "Grass")

    override fun getResistances(): List<String>? = listOf("Fire", "Water", "Ice", "Steel")

    override fun getImmunities(): List<String>? = null
}