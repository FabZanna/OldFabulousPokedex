package com.fabulouszanna.pokedex.pokemontypes

object Dragon : IWeaknessResistance {
    override fun getWeaknesses(): List<String> = listOf("Dragon", "Fairy", "Ice")

    override fun getResistances(): List<String>? = listOf("Fire", "Water", "Electric", "Grass")

    override fun getImmunities(): List<String>? = null
}