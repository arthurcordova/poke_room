package com.proway.pokemonapp.database.dao

import androidx.room.*
import com.proway.pokemonapp.model.Pokemon
//import com.proway.pokemonapp.model.PokemonItem
import com.proway.pokemonapp.model.Types

/**
 * Camada responsável em conversar diretamente com a base de dados.
 * Nesta camada onde excutaremos o commandos SQL.
 *
 * Obs* o nome da table passa a ser o data classa que utilizamos com a
 * annotation @Entity
 *
 * */
@Dao
interface PokemonDAO {

    /**
     * Declaramos a função all() para retornar todos os registros da tabela.
     * O commando que esta dentro do @Query é o reponsável pela busca.
     */
    @Query("SELECT * FROM Pokemon")
    fun all(): List<Pokemon>?

    /**
     * Declaramos a função byId() para retornar um unico registro da tabela.
     * O commando que esta dentro do @Query é o reponsável pela busca, e neste.
     * caso utilizamos o parametro passado para a funçao :pokeId para passar
     * dentro do comando SQL.
     */
    @Query("SELECT * FROM Pokemon WHERE poke_name = :pokeId")
    fun byId(pokeId: String): Pokemon?

    /**
     * Declaramos a função insert() para inserir os registros no banco
     * Por parametros esperamos um Pokemon que irá autoreconhecer sua Entity
     * e será inserido nela.
     *
     * @param pokemon
     *
     * onConflict server para validar quando o insert identifica algum resigtro duplicado.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemon: List<Pokemon>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertType(types: List<Types>)

    /**
     * Criado query para buscar pokemons por nome
     */
    /**
     * Criado query para buscar pokemons por nome
     */
    @Query("SELECT * FROM pokemon WHERE poke_name LIKE '%' || :query || '%'")
    fun fetchFiltered(query: String): List<Pokemon>


//    @Transaction
//    fun insertDetails(pokemonItem: PokemonItem) {
//        insert(pokemon = pokemonItem.pokemon)
//        insertType(types = pokemonItem.types)
//    }

}