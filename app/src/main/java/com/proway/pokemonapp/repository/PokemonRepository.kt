package com.proway.pokemonapp.repository

import android.content.Context
import com.proway.pokemonapp.database.AppDatabase
import com.proway.pokemonapp.model.PokeResponse
import com.proway.pokemonapp.model.Pokemon
import com.proway.pokemonapp.model.PokemonDetails
//import com.proway.pokemonapp.model.PokemonItem
import com.proway.pokemonapp.services.RefrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonRepository(private val context: Context) {

    /**
     * Instancia do database para utilizarmos dentro do repository
     */
    private val database = AppDatabase.getDatabase(context)
    val service = RefrofitService.getPokeService()

    suspend fun fetchAll() : List<Pokemon>? {
        return withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
            val response = service.getAll()
            val responsePokemon = processData(response)
            responsePokemon?.results?.forEach {
                fetchPokemonDetails(it.extractIdFromUrl())?.let { details ->
                    it.details = details
                }
            }
            responsePokemon?.results

        }
    }

    private fun <T> processData(response: Response<T>): T? {
        return if (response.isSuccessful) response.body() else null
    }

    /**
     * Irá retornar os detalhes do pokemon
     * @param pokeId String - id do pokemon que extraimos da url no primeiro service
     */
    private suspend fun fetchPokemonDetails(pokeId: String) : PokemonDetails? {
        return withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
            val response = service.getDetails(pokeId)
            processData(response)
        }
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