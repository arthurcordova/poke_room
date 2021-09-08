package com.proway.pokemonapp.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proway.pokemonapp.model.Pokemon
import com.proway.pokemonapp.repository.PokemonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val _pokemons = MutableLiveData<List<Pokemon>?>()
    var pokemons: LiveData<List<Pokemon>?> = _pokemons

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    /**
     * Função que chamamos somente interna dentro do viewmodel.
     * Irá buscar da API a lista de pokemons. Após receber os dados
     * insere no banco local a lista que recebeu.
     */
    fun fetchAllPokemons(context: Context) {
        /**
         * Instaciamos o Repository
         */
        val repository = PokemonRepository(context)
        _pokemons.value = emptyList()
        _isLoading.value = true

        viewModelScope.launch {
            repository.fetchAll().let { pokes ->
                _isLoading.value = false
                _pokemons.value = pokes
            }
        }
    }

    fun fetchFilteredFromDatabase(context: Context, query: String) {
        val repository = PokemonRepository(context)
        _pokemons.value = emptyList()
        val filteredList = repository.fetchAllFromDatabaseWithFilter(query)
        filteredList?.let {
            _pokemons.value = it
        }
    }

}