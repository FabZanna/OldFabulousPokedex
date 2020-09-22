package com.fabulouszanna.pokedex.model.pokemontypes

import com.fabulouszanna.pokedex.model.PokemonModel

class PokemonWeaknesses(pokemon: PokemonModel) {
    private val type1 = pokemon.type1
    private val type2 = pokemon.type2

    private fun getTypeObject(type: String): PokemonType = when (type) {
        "Bug" -> PokemonType.Bug
        "Dark" -> PokemonType.Dark
        "Dragon" -> PokemonType.Dragon
        "Electric" -> PokemonType.Electric
        "Fairy" -> PokemonType.Fairy
        "Fighting" -> PokemonType.Fighting
        "Fire" -> PokemonType.Fire
        "Flying" -> PokemonType.Flying
        "Ghost" -> PokemonType.Ghost
        "Grass" -> PokemonType.Grass
        "Ground" -> PokemonType.Ground
        "Ice" -> PokemonType.Ice
        "Normal" -> PokemonType.Normal
        "Poison" -> PokemonType.Poison
        "Psychic" -> PokemonType.Psychic
        "Rock" -> PokemonType.Rock
        "Steel" -> PokemonType.Steel
        "Water" -> PokemonType.Water
        else -> throw IllegalArgumentException("Type $type does not exist")
    }

    private val typeWeaknesses =
        type2?.let { getTypeObject(type1).weaknesses + getTypeObject(type2).weaknesses }
            ?: getTypeObject(type1).weaknesses
    private val typeResistances =
        type2?.let { getTypeObject(type1).resistances + getTypeObject(type2).resistances }
            ?: getTypeObject(type1).resistances
    private val typeImmunities =
        type2?.let { getTypeObject(type1).immunities + getTypeObject(type2).immunities }
            ?: getTypeObject(type1).immunities

    private val weaknessResistanceTable: Map<String, Float> by lazy { getWeaknessTable() }


    private fun getWeaknessTable(): Map<String, Float> {
        val weaknessTable = mutableMapOf<String, Float>()
        typeWeaknesses.forEach {
            weaknessTable[it] = weaknessTable[it]?.times(2f) ?: 2f
        }
        typeResistances.forEach {
            weaknessTable[it] = weaknessTable[it]?.times(0.5f) ?: 0.5f
        }
        typeImmunities.forEach {
            weaknessTable[it] = weaknessTable[it]?.times(0f) ?: 0f
        }
        return weaknessTable
    }

    val weaknesses by lazy { weaknessResistanceTable.filter { (_, value) -> value > 1f } }


    val resistances by lazy { weaknessResistanceTable.filter { (_, value) -> value > 0f && value < 1f } }

    val immunities by lazy { weaknessResistanceTable.filter { (_, value) -> value == 0f } }
}