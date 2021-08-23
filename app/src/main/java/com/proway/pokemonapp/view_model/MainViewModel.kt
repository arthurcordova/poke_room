package com.proway.pokemonapp.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.proway.pokemonapp.model.Pokemon
import com.proway.pokemonapp.repository.PokemonRepository

class MainViewModel : ViewModel() {

    val _pokeResponse = MutableLiveData<List<Pokemon>>()
    val pokeResponse: LiveData<List<Pokemon>> = _pokeResponse

    val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchAllFromServer(context: Context) {
        val repository = PokemonRepository(context)

        repository.fetchAll { response, error ->
            response?.let {
                _pokeResponse.value = it.results
                repository.insertIntoDatabase(it.results)
            }
            error?.let {
                _error.value = it
            }
        }
    }

    fun fetchAllFromDatabase(context: Context) {
        val listOf = PokemonRepository(context).fetchAllFromDatabase()
        if (listOf != null && listOf.isNotEmpty()) {
            _pokeResponse.value = listOf!!
        } else {
            fetchAllFromServer(context)
        }

    }


}