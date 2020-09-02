package com.fabulouszanna.pokedex.pokemontypes

object Ghost : IWeaknessResistance {
    override fun getWeaknesses(): List<String> = listOf("Ghost", "Dark")

    override fun getResistances(): List<String>? = listOf("Poison", "Bug")

    override fun getImmunities(): List<String>? = listOf("Normal", "Fighting")
}