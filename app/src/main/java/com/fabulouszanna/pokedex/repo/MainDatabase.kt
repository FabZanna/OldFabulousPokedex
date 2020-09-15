package com.fabulouszanna.pokedex.repo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.fabulouszanna.pokedex.repo.ability.AbilityEntity
import com.fabulouszanna.pokedex.repo.move.MoveEntity
import com.fabulouszanna.pokedex.repo.pokemon.PokemonEntity
import com.fabulouszanna.pokedex.repo.pokemonmove.PokemonMoveEntity
import com.fabulouszanna.pokedex.utilities.Converters
import com.fabulouszanna.pokedex.utilities.JSONParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Database(
    entities = [PokemonEntity::class, AbilityEntity::class, PokemonMoveEntity::class, MoveEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class MainDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonEntity.PokemonDAO
    abstract fun abilityDao(): AbilityEntity.abilityDAO
    abstract fun pokemonMoveDao(): PokemonMoveEntity.PokemonMoveDAO
    abstract fun moveDao(): MoveEntity.moveDAO

    companion object {
        private var INSTANCE: MainDatabase? = null
        private const val DB_NAME = "pokemon.db"
        fun newInstance(context: Context): MainDatabase {
            val db = Room.databaseBuilder(context, MainDatabase::class.java, DB_NAME)
                .addCallback(PokemonDatabaseCallback(context))
                .fallbackToDestructiveMigration()
                .build()
                .also { INSTANCE = it }
            db.query("select 1", null)

            return db
        }
    }

    private class PokemonDatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            GlobalScope.launch(Dispatchers.IO) { populateDatabase(INSTANCE) }
        }

        suspend fun populateDatabase(database: MainDatabase?) {
            val pkmJSON = JSONParser(context).extractJsonData<PokemonEntity>("pkm_info.json")
            val abilityJSON =
                JSONParser(context).extractJsonData<AbilityEntity>("abilities_info.json")
            val pokemonMoveJSON =
                JSONParser(context).extractJsonData<PokemonMoveEntity>("pkm_move_info.json")
            val moveJSON = JSONParser(context).extractJsonData<MoveEntity>("moves_info.json")
            database?.let {
                withContext(Dispatchers.IO) {
                    val pokemonDao = it.pokemonDao()
                    val abilityDao = it.abilityDao()
                    val pokemonMoveDao = it.pokemonMoveDao()
                    val moveDao = it.moveDao()
                    pokemonDao.addAll(pkmJSON)
                    abilityDao.addAll(abilityJSON)
                    pokemonMoveDao.addAll(pokemonMoveJSON)
                    moveDao.addAll(moveJSON)
                }
            }
        }
    }
}