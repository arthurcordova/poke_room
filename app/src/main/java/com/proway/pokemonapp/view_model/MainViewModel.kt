package com.proway.pokemonapp.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.proway.pokemonapp.model.Pokemon
import com.proway.pokemonapp.repository.PokemonRepository

class MainViewModel : ViewModel() {

    private val _pokemons = MutableLiveData<List<Pokemon>>()
    val pokemons: LiveData<List<Pokemon>> = _pokemons

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    /**
     * Função que chamamos somente interna dentro do viewmodel.
     * Irá buscar da API a lista de pokemons. Após receber os dados
     * insere no banco local a lista que recebeu.
     */
    private fun fetchAllFromServer(context: Context) {
        /**
         * Instaciamos o Repository
         */
        val repository = PokemonRepository(context)

        repository.fetchAll { response, error ->
            response?.let {
                _pokemons.value = it.results
                /**
                 * Quando recebeu uma lista, chamamos o repository para add ela no database local
                 */
                repository.insertIntoDatabase(it.results)
            }
            error?.let {
                _error.value = it
            }
        }
    }

    /**
     * Função criada para verificar se busca os dados do database local ou da API.
     * Esta é a função que sempre será chamada da nossa view.
     */
    fun fetchAllFromDatabase(context: Context) {
        /**
         * Chama o repository e busca por todos os pokemons no database local
         */
        val listOf = PokemonRepository(context).fetchAllFromDatabase()

        /**
         * Se o database local tiver registros, mostramos para a view, caso contratio
         * chamamos os pokemons do servidor.
         */
        if (listOf != null && listOf.isNotEmpty()) {
            _pokemons.value = listOf!!
        } else {
            fetchAllFromServer(context)
        }

    }


}