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
    val type2: String? = null,
    val gen: String,
    val is_variation: Boolean,
    val species: String,
    val description: String,
    val height: String,
    val weight: String,
    val male_perc: String,
    val female_perc: String,
    val hp: String,
    val attack: String,
    val defense: String,
    val sp_atk: String,
    val sp_def: String,
    val speed: String,
    val abilities: List<String>,
    val hidden_ability: String
) {
//    constructor(model: PokemonModel) : this(
//        name = model.name,
//        id = model.id,
//        img_url = model.imgUrl,
//        type1 = model.type1,
//        type2 = model.type2,
//        gen = model.gen,
//        is_variation = model.isVariation,
//        species = model.species,
//        description = model.description
//    )

    fun toModel(): PokemonModel {
        return PokemonModel(
            name = name,
            id = id,
            imgUrl = img_url,
            type1 = type1,
            type2 = type2,
            gen = gen,
            isVariation = is_variation,
            species = species,
            description = description,
            height = height,
            weight = weight,
            malePercentage = male_perc,
            femalePercentage = female_perc,
            hp = hp,
            attack = attack,
            defense = defense,
            specialAtk = sp_atk,
            specialDef = sp_def,
            speed = speed,
            abilities = abilities,
            hiddenAbility = hidden_ability
        )
    }

    @Dao
    interface PokemonDAO {
        @Query("SELECT * FROM pokemon_table WHERE NOT is_variation")
        fun all(): Flow<List<PokemonEntity>>

        @Query("SELECT * FROM pokemon_table WHERE pokemon_name LIKE '%' || :pokemonName || '%'")
        fun filterByName(pokemonName: String): Flow<List<PokemonEntity>>

        @Query("SELECT * FROM pokemon_table WHERE gen = :gen")
        fun filterByGen(gen: String): Flow<List<PokemonEntity>>

        @Query("SELECT * FROM pokemon_table WHERE (type1 = :type OR type2 = :type)")
        fun filterByType(type: String): Flow<List<PokemonEntity>>

        @Query("SELECT * FROM pokemon_table WHERE gen = :gen AND (type1 = :type OR type2 = :type)")
        fun filtered(gen: String, type: String): Flow<List<PokemonEntity>>

        @Query("SELECT * FROM pokemon_table WHERE pokemon_id = :pokemonId AND NOT is_variation")
        fun findPokemon(pokemonId: String): Flow<PokemonEntity>

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun addAll(entities: List<PokemonEntity>)
    }
}