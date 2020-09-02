package com.fabulouszanna.pokedex.pokemontypes

import com.fabulouszanna.pokedex.model.PokemonModel

class PokemonWeaknesses(private val pokemon: PokemonModel) {
    private val type1 = pokemon.type1
    private val type2 = pokemon.type2
    private val typeClasses = mapOf(
        "Bug" to Bug,
        "Dark" to Dark,
        "Dragon" to Dragon,
        "Electric" to Electric,
        "Fairy" to Fairy,
        "Fighting" to Fighting,
        "Fire" to Fire,
        "Flying" to Flying,
        "Ghost" to Ghost,
        "Grass" to Grass,
        "Ground" to Ground,
        "Ice" to Ice,
        "Normal" to Normal,
        "Poison" to Poison,
        "Psychic" to Psychic,
        "Rock" to Rock,
        "Steel" to Steel,
        "Water" to Water
    )

    private val weaknesses =
        joinLists(typeClasses[type1]?.getWeaknesses(), typeClasses[type2]?.getWeaknesses())
    private val resistances =
        joinLists(typeClasses[type1]?.getResistances(), typeClasses[type2]?.getResistances())
    private val immunities =
        joinLists(typeClasses[type1]?.getImmunities(), typeClasses[type2]?.getImmunities())
    private val weaknessResistanceTable: Map<String, Float>
        get() = getWeaknessTable()

    private fun joinLists(firstList: List<String>?, secondList: List<String>?): List<String> {
        val joined: MutableList<String> = ArrayList(firstList ?: emptyList())
        joined.addAll(secondList ?: emptyList())
        return joined
    }

    private fun getWeaknessTable(): Map<String, Float> {
        val weaknessTable = mutableMapOf<String, Float>()
        weaknesses.forEach {
            weaknessTable[it] = weaknessTable[it]?.times(2f) ?: 2f
        }
        resistances.forEach {
            weaknessTable[it] = weaknessTable[it]?.times(0.5f) ?: 0.5f
        }
        immunities.forEach {
            weaknessTable[it] = weaknessTable[it]?.times(0f) ?: 0f
        }
        return weaknessTable
    }

    fun getWeaknesses(): Map<String, Float> =
        weaknessResistanceTable.filter { (_, value) -> value > 1f }

    fun getResistances(): Map<String, Float> =
        weaknessResistanceTable.filter { (_, value) -> value > 0f && value < 1f }

    fun getImmunities(): Map<String, Float> =
        weaknessResistanceTable.filter { (_, value) -> value == 0f }
}