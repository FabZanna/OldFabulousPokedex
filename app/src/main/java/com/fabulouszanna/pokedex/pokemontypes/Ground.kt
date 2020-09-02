package com.fabulouszanna.pokedex.pokemontypes

object Ground : IWeaknessResistance {
    override fun getWeaknesses(): List<String> = listOf("Water", "Grass", "Ice")

    override fun getResistances(): List<String>? = listOf("Poison", "Rock")

    override fun getImmunities(): List<String>? = listOf("Electric")
}