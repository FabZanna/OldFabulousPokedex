package com.fabulouszanna.pokedex.pokemontypes

object Flying : IWeaknessResistance {
    override fun getWeaknesses(): List<String> = listOf("Electric", "Ice", "Rock")

    override fun getResistances(): List<String>? = listOf("Grass", "Fighting", "Bug")

    override fun getImmunities(): List<String>? = listOf("Ground")
}