package com.fabulouszanna.pokedex.pokemontypes

object Fire : IWeaknessResistance {
    override fun getWeaknesses(): List<String> = listOf("Water", "Ground", "Rock")

    override fun getResistances(): List<String>? = listOf("Fire", "Grass", "Ice", "Bug", "Steel", "Fairy")

    override fun getImmunities(): List<String>? = null
}