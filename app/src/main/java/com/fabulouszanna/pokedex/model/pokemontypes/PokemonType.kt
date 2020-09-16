package com.fabulouszanna.pokedex.model.pokemontypes

sealed class PokemonType(
    val weaknesses: List<String>,
    val resistances: List<String> = emptyList(),
    val immunities: List<String> = emptyList()
) {
    object Bug :
        PokemonType(listOf("Fire", "Flying", "Rock"), listOf("Grass", "Fighting", "Ground"))

    object Dark :
        PokemonType(listOf("Bug", "Fairy", "Fighting"), listOf("Dark", "Ghost"), listOf("Psychic"))

    object Dragon :
        PokemonType(listOf("Dragon", "Fairy", "Ice"), listOf("Fire", "Water", "Electric", "Grass"))

    object Electric : PokemonType(listOf("Ground"), listOf("Electric", "Flying", "Steel"))

    object Fairy :
        PokemonType(listOf("Poison", "Steel"), listOf("Fighting", "Bug", "Dark"), listOf("Dragon"))

    object Fighting :
        PokemonType(listOf("Flying", "Psychic", "Fairy"), listOf("Bug", "Rock", "Dark"))

    object Fire : PokemonType(
        listOf("Water", "Ground", "Rock"),
        listOf("Fire", "Grass", "Ice", "Bug", "Steel", "Fairy")
    )

    object Flying : PokemonType(
        listOf("Electric", "Ice", "Rock"),
        listOf("Grass", "Fighting", "Bug"),
        listOf("Ground")
    )

    object Ghost :
        PokemonType(listOf("Ghost", "Dark"), listOf("Poison", "Bug"), listOf("Normal", "Fighting"))

    object Grass : PokemonType(
        listOf("Fire", "Ice", "Poison", "Flying", "Bug"),
        listOf("Water", "Electric", "Grass", "Ground")
    )

    object Ground :
        PokemonType(listOf("Water", "Grass", "Ice"), listOf("Poison", "Rock"), listOf("Electric"))

    object Ice : PokemonType(listOf("Fire", "Fighting", "Rock", "Steel"), listOf("Ice"))

    object Normal : PokemonType(listOf("Fighting"), immunities = listOf("Ghost"))

    object Poison : PokemonType(
        listOf("Ground", "Psychic"),
        listOf("Grass", "Fighting", "Poison", "Bug", "Fairy")
    )

    object Psychic : PokemonType(listOf("Bug", "Ghost", "Dark"), listOf("Psychic", "Fighting"))

    object Rock : PokemonType(
        listOf("Water", "Grass", "Fighting", "Ground", "Steel"),
        listOf("Normal", "Fire", "Poison", "Flying")
    )

    object Steel : PokemonType(
        listOf("Fire", "Fighting", "Ground"),
        listOf(
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

    object Water : PokemonType(listOf("Electric", "Grass"), listOf("Fire", "Water", "Ice", "Steel"))

}