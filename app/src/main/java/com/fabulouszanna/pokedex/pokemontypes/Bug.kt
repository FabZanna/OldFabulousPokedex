package com.fabulouszanna.pokedex.pokemontypes

object Bug : IWeaknessResistance {
    override fun getWeaknesses(): List<String> = listOf("Fire", "Flying", "Rock")

    override fun getResistances(): List<String>? = listOf("Grass", "Fighting", "Ground")

    override fun getImmunities(): List<String>? = null
}