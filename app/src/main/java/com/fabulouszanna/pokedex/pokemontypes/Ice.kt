package com.fabulouszanna.pokedex.pokemontypes

object Ice : IWeaknessResistance {
    override fun getWeaknesses(): List<String> = listOf("Fire", "Fighting", "Rock", "Steel")

    override fun getResistances(): List<String>? = listOf("Ice")

    override fun getImmunities(): List<String>? = null
}