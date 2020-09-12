package com.fabulouszanna.pokedex.repo.move

import androidx.room.*
import com.fabulouszanna.pokedex.model.MoveModel

@Entity(tableName = "move_table")
data class MoveEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val type: String,
    val category: String,
    val power: String,
    val accuracy: String,
    val pp: String,
    val description: String,
    val effect: String
) {
    fun toModel(): MoveModel = MoveModel(
        moveName = name,
        moveType = type,
        category = category,
        power = power,
        accuracy = accuracy,
        pp = pp,
        description = description,
        effect = effect
    )

    @Dao
    interface moveDAO {
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun addAll(entities: List<MoveEntity>)
    }
}