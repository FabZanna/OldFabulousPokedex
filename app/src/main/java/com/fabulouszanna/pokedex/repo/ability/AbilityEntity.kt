package com.fabulouszanna.pokedex.repo.ability

import androidx.room.*
import com.fabulouszanna.pokedex.model.AbilityModel
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "ability_table")
data class AbilityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val effect: String,
    val game_description: String
) {
    fun toModel(): AbilityModel = AbilityModel(
        abilityName = name,
        effect = effect,
        gameDescription = game_description
    )

    @Dao
    interface abilityDAO {
        @Query("SELECT * FROM ability_table WHERE name = :abilityName")
        fun getAbilityByName(abilityName: String): Flow<AbilityEntity>

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun addAll(entities: List<AbilityEntity>)
    }
}