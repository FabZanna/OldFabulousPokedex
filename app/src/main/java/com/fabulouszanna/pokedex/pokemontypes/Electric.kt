package com.fabulouszanna.pokedex.pokemontypes

object Electric : IWeaknessResistance {
    override fun getWeaknesses(): List<String> = listOf("Ground")

    override fun getResistances(): List<String>? = listOf("Electric", "Flying", "Steel")

    override fun getImmunities(): List<String>? = null
}