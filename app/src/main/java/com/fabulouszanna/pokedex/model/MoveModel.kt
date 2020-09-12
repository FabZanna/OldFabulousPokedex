package com.fabulouszanna.pokedex.model

data class MoveModel (
    val moveName: String,
    val moveType: String,
    val category: String,
    val power: String,
    val accuracy: String,
    val pp: String,
    val description: String,
    val effect: String
)