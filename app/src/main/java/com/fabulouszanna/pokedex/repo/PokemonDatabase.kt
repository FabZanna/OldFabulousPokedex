package com.fabulouszanna.pokedex.repo

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.fabulouszanna.pokedex.utilities.Converters
import com.fabulouszanna.pokedex.utilities.JSONParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Database(entities = [PokemonEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonEntity.PokemonDAO

    companion object {
        private var INSTANCE: PokemonDatabase? = null
        private const val DB_NAME = "pokemon.db"
        fun newInstance(context: Context): PokemonDatabase {
            val db = Room.databaseBuilder(context, PokemonDatabase::class.java, DB_NAME)
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

        suspend fun populateDatabase(database: PokemonDatabase?) {
            val json = JSONParser(context).extractJsonData("pkm_info.json")
            database?.let {
                withContext(Dispatchers.IO) {
                    val pokemonDao = it.pokemonDao()
                    pokemonDao.addAll(json)
                }
            }
        }
    }
}