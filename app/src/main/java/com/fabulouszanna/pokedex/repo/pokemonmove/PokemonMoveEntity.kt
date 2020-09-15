package com.fabulouszanna.pokedex.repo.pokemonmove

import androidx.room.*
import com.fabulouszanna.pokedex.model.PokemonMoveModel
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "pokemon_move")
data class PokemonMoveEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val pokemon_name: String,
    val level_learned: String? = null,
    val move_TM: String? = null,
    val move_name: String,
    val move_learned_by: String
) {
    fun toModel(): PokemonMoveModel {
        return PokemonMoveModel(
            pokemonName = pokemon_name,
            moveLevelLearned = level_learned,
            moveTM = move_TM,
            moveName = move_name,
            learnMethod = move_learned_by
        )
    }


    @Dao
    interface PokemonMoveDAO {
        @Query("SELECT * FROM pokemon_move WHERE pokemon_name = :pokemonName AND move_learned_by = :learnMethod")
        fun getPokemonMoves(pokemonName: String, learnMethod: String): Flow<List<PokemonAndMoveEntity>>

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun addAll(entities: List<PokemonMoveEntity>)
    }
}