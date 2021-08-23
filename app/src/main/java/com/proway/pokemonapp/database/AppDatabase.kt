package com.proway.pokemonapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.proway.pokemonapp.database.dao.PokemonDAO
import com.proway.pokemonapp.model.Pokemon

@Database(entities = [Pokemon::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pokemonDAO(): PokemonDAO

    companion object {

        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "pokemon_app_db"
            )
                .allowMainThreadQueries()
                .build()
        }
    }

}
