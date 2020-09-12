package com.fabulouszanna.pokedex.repo.pokemonmove

import androidx.room.Embedded
import androidx.room.Relation
import com.fabulouszanna.pokedex.model.PokemonAndMoveModel
import com.fabulouszanna.pokedex.repo.move.MoveEntity

data class PokemonAndMoveEntity(
    @Embedded
    val pokemonMove: PokemonMoveEntity,
    @Relation(
        parentColumn = "move_name",
        entityColumn = "name"
    )
    val move: MoveEntity
) {
    fun toModel(): PokemonAndMoveModel = PokemonAndMoveModel(
        pokemonMove = pokemonMove.toModel(),
        move = move.toModel()
    )
}