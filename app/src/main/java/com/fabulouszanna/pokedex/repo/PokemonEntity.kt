package com.fabulouszanna.pokedex.repo

import androidx.room.*
import com.fabulouszanna.pokedex.model.PokemonModel
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "pokemon_table", primaryKeys = ["pokemon_id", "pokemon_name"])
data class PokemonEntity(
    @ColumnInfo(name = "pokemon_name")
    val name: String,
    @ColumnInfo(name = "pokemon_id")
    val id: String,
    val img_url: String,
    val type1: String,
    val type2: String? = null
) {
    constructor(model: PokemonModel) : this(
        name = model.name,
        id = model.id,
        img_url = model.imgUrl,
        type1 = model.type1,
        type2 = model.type2
    )

    fun toModel(): PokemonModel {
        return PokemonModel(
            name = name,
            id = id,
            imgUrl = img_url,
            type1 = type1,
            type2 = type2
        )
    }

    @Dao
    interface PokemonDAO {
        @Query("SELECT * FROM pokemon_table ORDER BY pokemon_id")
        fun all(): Flow<List<PokemonEntity>>

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun addAll(entities: List<PokemonEntity>)
    }
}