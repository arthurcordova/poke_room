package com.proway.pokemonapp.repository

import android.content.Context
import com.proway.pokemonapp.database.AppDatabase
import com.proway.pokemonapp.model.PokeResponse
import com.proway.pokemonapp.model.Pokemon
import com.proway.pokemonapp.services.RefrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonRepository(private val context: Context) {

    private val database = AppDatabase.getDatabase(context)

    fun fetchAll(onComplete: (PokeResponse?, String?) -> Unit) {
        val service = RefrofitService.getPokeService()
        val call = service.getAll()
        call.enqueue(object : Callback<PokeResponse> {
            override fun onResponse(call: Call<PokeResponse>, response: Response<PokeResponse>) {
                if (response.body() != null) {
                    onComplete(response.body(), null)
                } else {
                    onComplete(null, "Nenhum pokemon encontrado")
                }
            }

            override fun onFailure(call: Call<PokeResponse>, t: Throwable) {
                onComplete(null, t.message)
            }
        })
    }

    fun insertIntoDatabase(items: List<Pokemon>) {
        val dao = database.pokemonDAO()
        items.forEach { poke ->
            dao.insert(pokemon = poke)
        }

    }

    fun fetchAllFromDatabase(): List<Pokemon>? {
        val dao = database.pokemonDAO()
        return dao.all()
    }

}