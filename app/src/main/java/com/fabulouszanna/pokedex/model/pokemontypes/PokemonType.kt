package com.fabulouszanna.pokedex.model.pokemontypes

sealed class PokemonType(
    val weaknesses: List<String>,
    val resistances: List<String> = emptyList(),
    val immunities: List<String> = emptyList()
) {
    object Bug :
        PokemonType(
            weaknesses = listOf("Fire", "Flying", "Rock"),
            resistances = listOf("Grass", "Fighting", "Ground")
        )

    object Dark :
        PokemonType(
            weaknesses = listOf("Bug", "Fairy", "Fighting"),
            resistances = listOf("Dark", "Ghost"),
            immunities = listOf("Psychic")
        )

    object Dragon :
        PokemonType(
            weaknesses = listOf("Dragon", "Fairy", "Ice"),
            resistances = listOf("Fire", "Water", "Electric", "Grass")
        )

    object Electric : PokemonType(
        weaknesses = listOf("Ground"),
        resistances = listOf("Electric", "Flying", "Steel")
    )

    object Fairy :
        PokemonType(
            weaknesses = listOf("Poison", "Steel"),
            resistances = listOf("Fighting", "Bug", "Dark"),
            immunities = listOf("Dragon")
        )

    object Fighting :
        PokemonType(
            weaknesses = listOf("Flying", "Psychic", "Fairy"),
            resistances = listOf("Bug", "Rock", "Dark")
        )

    object Fire : PokemonType(
        weaknesses = listOf("Water", "Ground", "Rock"),
        resistances = listOf("Fire", "Grass", "Ice", "Bug", "Steel", "Fairy")
    )

    object Flying : PokemonType(
        weaknesses = listOf("Electric", "Ice", "Rock"),
        resistances = listOf("Grass", "Fighting", "Bug"),
        immunities = listOf("Ground")
    )

    object Ghost :
        PokemonType(
            weaknesses = listOf("Ghost", "Dark"),
            resistances = listOf("Poison", "Bug"),
            immunities = listOf("Normal", "Fighting")
        )

    object Grass : PokemonType(
        weaknesses = listOf("Fire", "Ice", "Poison", "Flying", "Bug"),
        resistances = listOf("Water", "Electric", "Grass", "Ground")
    )

    object Ground :
        PokemonType(
            weaknesses = listOf("Water", "Grass", "Ice"),
            resistances = listOf("Poison", "Rock"),
            immunities = listOf("Electric")
        )

    object Ice : PokemonType(
        weaknesses = listOf("Fire", "Fighting", "Rock", "Steel"),
        resistances = listOf("Ice")
    )

    object Normal : PokemonType(weaknesses = listOf("Fighting"), immunities = listOf("Ghost"))

    object Poison : PokemonType(
        weaknesses = listOf("Ground", "Psychic"),
        resistances = listOf("Grass", "Fighting", "Poison", "Bug", "Fairy")
    )

    object Psychic : PokemonType(
        weaknesses = listOf("Bug", "Ghost", "Dark"),
        resistances = listOf("Psychic", "Fighting")
    )

    object Rock : PokemonType(
        weaknesses = listOf("Water", "Grass", "Fighting", "Ground", "Steel"),
        resistances = listOf("Normal", "Fire", "Poison", "Flying")
    )

    object Steel : PokemonType(
        weaknesses = listOf("Fire", "Fighting", "Ground"),
        resistances = listOf(
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
    )

    object Water : PokemonType(
        weaknesses = listOf("Electric", "Grass"),
        resistances = listOf("Fire", "Water", "Ice", "Steel")
    )

}