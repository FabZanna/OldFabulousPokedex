package com.fabulouszanna.pokedex.pokemontypes

object Normal : IWeaknessResistance {
    override fun getWeaknesses(): List<String> = listOf("Fighting")

    override fun getResistances(): List<String>? = null

    override fun getImmunities(): List<String>? = listOf("Ghost")
}