package com.proway.pokemonapp.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RefrofitService {

    /**
     * Cria uma instacia do retrofit passando a url base.
     * Deixamos como private pq não interessa para as outras classes, oq nos interessa
     */
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun getPokeService(): PokeService {
        /**
         * Criando novo pokemon
         */
        /**
         * Criando mais um novo pokemon
         */
        return retrofit.create(PokeService::class.java)
    }
}