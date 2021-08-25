package com.proway.pokemonapp.repository

import android.content.Context
import com.proway.pokemonapp.database.AppDatabase
import com.proway.pokemonapp.model.PokeResponse
import com.proway.pokemonapp.model.Pokemon
import com.proway.pokemonapp.model.PokemonDetails
//import com.proway.pokemonapp.model.PokemonItem
import com.proway.pokemonapp.services.RefrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonRepository(private val context: Context) {

    /**
     * Instancia do database para utilizarmos dentro do repository
     */
    private val database = AppDatabase.getDatabase(context)
    val service = RefrofitService.getPokeService()

    fun fetchAll(onComplete: (PokeResponse?, String?) -> Unit) {
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

    /**
     * Irá retornar os detalhes do pokemon
     * @param pokeId String - id do pokemon que extraimos da url no primeiro service
     */
    fun fetchPokemonDetails(pokeId: String, onComplete: (PokemonDetails?, String?) -> Unit) {
        val call = service.getDetails(pokeId)
        call.enqueue(object : Callback<PokemonDetails> {
            override fun onResponse(
                call: Call<PokemonDetails>,
                response: Response<PokemonDetails>
            ) {
                if (response.body() != null) {
                    onComplete(response.body(), null)
                } else {
                    onComplete(null, "Nenhum pokemon encontrado")
                }
            }

            override fun onFailure(call: Call<PokemonDetails>, t: Throwable) {
                onComplete(null, t.message)
            }
        })
    }

    /**
     * Função que irá receber Pokemon e irá add no database local
     */
    fun insertIntoDatabase(pokemon: Pokemon) {
        val dao = database.pokemonDAO()
        dao.insert(pokemon)
    }

    /**
     * Buscamos todos os Pokemons que já estão dentro do database local
     */
    fun fetchAllFromDatabase(): List<Pokemon>? {
        val dao = database.pokemonDAO()
        return dao.all()
    }

    /**
     * Buscamos todos os Pokemons que já estão dentro do database local
     */
    fun fetchAllFromDatabaseWithFilter(query: String): List<Pokemon>? {
        val dao = database.pokemonDAO()
        return dao.fetchFiltered(query.lowercase())
    }

}