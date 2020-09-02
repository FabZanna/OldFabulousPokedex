package com.fabulouszanna.pokedex.pokemontypes

object Steel : IWeaknessResistance {
    override fun getWeaknesses(): List<String> = listOf("Fire", "Fighting", "Ground")

    override fun getResistances(): List<String>? = listOf(
        "Normal",
        "Grass",
        "Ice",
        "Flying",
        "Psychic",
        "Bug",
        "Rock",
        "Dragon",
        "Steel",
        "Fairy"
    )

    override fun getImmunities(): List<String>? = null
}