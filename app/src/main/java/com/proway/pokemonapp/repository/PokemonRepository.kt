package com.proway.pokemonapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.proway.pokemonapp.database.AppDatabase
import com.proway.pokemonapp.model.Pokemon
import com.proway.pokemonapp.model.PokemonDetails
import com.proway.pokemonapp.services.RefrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class PokemonRepository(val context: Context) {

    /**
     * Instancia do database para utilizarmos dentro do repository
     */
    private val database = AppDatabase.getDatabase(context)
    val service = RefrofitService.getPokeService()

    suspend fun fetchAll() : List<Pokemon>? {
        return withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
            // Busca todos os pokemons (id & name)
            val dataFromDb = fetchAllFromDatabase()
            if (dataFromDb == null ) {
                val response = service.getAll()
                val responsePokemon = processData(response)
                // Percorre todos os results para buscar os details
                responsePokemon?.results?.forEach {
                    // Para a thread para cara pokemon, irá buscar os details
                    fetchPokemonDetails(it.extractIdFromUrl())?.let { details ->
                        // Injeta o detail dentro de cada elemento da lista
                        it.details = details
                    }
                }
                // Retornar a lista de pokemons com os detalhes carregados
                responsePokemon?.results?.let {
                    insertIntoDatabase(it)
                }
                fetchAllFromDatabase()
            } else {
                dataFromDb
            }
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
    suspend fun insertIntoDatabase(pokemon: List<Pokemon>) {
        val dao = database.pokemonDAO()
        dao.insert(pokemon)
    }

    /**
     * Buscamos todos os Pokemons que já estão dentro do database local
     */
    suspend fun fetchAllFromDatabase(): List<Pokemon>? {
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