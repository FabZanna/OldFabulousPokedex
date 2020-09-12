package com.fabulouszanna.pokedex.repo.pokemon

import androidx.room.*
import com.fabulouszanna.pokedex.model.PokemonModel
import com.fabulouszanna.pokedex.model.PokemonStats
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "pokemon_table")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "pokemon_name")
    val name: String,
    @ColumnInfo(name = "pokemon_id")
    val pokemon_id: String,
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
    @Embedded
    val stats: PokemonStats?,
//    val hp: String,
//    val attack: String,
//    val defense: String,
//    val sp_atk: String,
//    val sp_def: String,
//    val speed: String,
    val abilities: List<String>,
    val hidden_ability: String? = null,
    val evolutions: List<String>,
    val evolution_cause: String? = null,
    val split_evolution: Boolean
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
            id = id,
            name = name,
            pokemonId = pokemon_id,
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
            stats = stats,
//            hp = hp,
//            attack = attack,
//            defense = defense,
//            specialAtk = sp_atk,
//            specialDef = sp_def,
//            speed = speed,
            abilities = abilities,
            hiddenAbility = hidden_ability,
            evolutions = evolutions,
            evolutionCause = evolution_cause,
            splitEvolution = split_evolution
        )
    }

    @Dao
    interface PokemonDAO {
        //@Query("SELECT * FROM pokemon_table WHERE NOT is_variation")
        @Query("SELECT * FROM pokemon_table")
        fun all(): Flow<List<PokemonEntity>>

        @Query("SELECT * FROM pokemon_table WHERE pokemon_name LIKE '%' || :pokemonName || '%'")
        fun filterByName(pokemonName: String): Flow<List<PokemonEntity>>

        @Query("SELECT * FROM pokemon_table WHERE gen = :gen")
        fun filterByGen(gen: String): Flow<List<PokemonEntity>>

        @Query("SELECT * FROM pokemon_table WHERE (type1 = :type OR type2 = :type)")
        fun filterByType(type: String): Flow<List<PokemonEntity>>

        @Query("SELECT * FROM pokemon_table WHERE gen = :gen AND (type1 = :type OR type2 = :type)")
        fun filtered(gen: String, type: String): Flow<List<PokemonEntity>>

        @Query("SELECT * FROM pokemon_table WHERE id = :id")
        fun findPokemon(id: Int): Flow<PokemonEntity>

        @Query("SELECT * FROM pokemon_table WHERE pokemon_name IN (:pokemonNames)")
        fun getPokemonsByName(pokemonNames: List<String>): Flow<List<PokemonEntity>>

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun addAll(entities: List<PokemonEntity>)
    }
}