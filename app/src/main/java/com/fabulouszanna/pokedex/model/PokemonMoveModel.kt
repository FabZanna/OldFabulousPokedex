package com.fabulouszanna.pokedex.model

import androidx.room.Embedded
import androidx.room.Relation

data class PokemonMoveModel (
    val pokemonName: String,
    val moveLevelLearned: String,
    val moveName: String,
    val learnMethod: String
)

data class PokemonAndMoveModel (
    val pokemonMove: PokemonMoveModel,
    val move: MoveModel
)