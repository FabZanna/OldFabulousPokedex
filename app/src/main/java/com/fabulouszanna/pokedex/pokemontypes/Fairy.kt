package com.fabulouszanna.pokedex.pokemontypes

object Fairy : IWeaknessResistance {
    override fun getWeaknesses(): List<String> = listOf("Poison", "Steel")

    override fun getResistances(): List<String>? = listOf("Fighting", "Bug", "Dark")

    override fun getImmunities(): List<String>? = listOf("Dragon")
}