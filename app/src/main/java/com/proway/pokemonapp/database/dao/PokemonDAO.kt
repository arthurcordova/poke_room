package com.proway.pokemonapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.proway.pokemonapp.model.Pokemon

@Dao
interface PokemonDAO {

    @Query("SELECT * FROM Pokemon ORDER BY poke_name")
    fun all(): List<Pokemon>?

    @Query("SELECT * FROM Pokemon WHERE poke_name = :pokeId")
    fun byId(pokeId: String): Pokemon?

    @Insert
    fun insert(pokemon: Pokemon)

}