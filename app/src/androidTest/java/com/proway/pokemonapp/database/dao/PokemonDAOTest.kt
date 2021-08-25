package com.proway.pokemonapp.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.proway.pokemonapp.database.AppDatabase
import com.proway.pokemonapp.model.Other
import com.proway.pokemonapp.model.Pokemon
import com.google.common.truth.Truth.assertThat
import com.proway.pokemonapp.model.PokemonDetails
import com.proway.pokemonapp.model.Sprites
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class PokemonDAOTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: PokemonDAO

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.pokemonDAO()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun test_insert_deve_retornar_true() {
        val pokemonToInsert =
            Pokemon("pikachu", "", PokemonDetails(0, Sprites(0, Other(0, null)), emptyList()))
        dao.insert(pokemonToInsert)

        val resturnedPokemons = dao.all()
        assertThat(resturnedPokemons).contains(pokemonToInsert)
    }

    @Test
    fun test_select_like() {
        val pokemon1 =
            Pokemon("botao", "", PokemonDetails(0, Sprites(0, Other(0, null)), emptyList()))
        dao.insert(pokemon1)
        val pokemon2 =
            Pokemon("andrei", "", PokemonDetails(0, Sprites(0, Other(0, null)), emptyList()))
        dao.insert(pokemon2)
        val pokemon3 =
            Pokemon("arthur", "", PokemonDetails(0, Sprites(0, Other(0, null)), emptyList()))
        dao.insert(pokemon3)

        val resturnedPokemons = dao.fetchFiltered("ao")
        assertThat(resturnedPokemons).hasSize(1)
    }

    @Test
    fun test_fetch_by_id() {
        val pokemon1 =
            Pokemon("botao", "", PokemonDetails(0, Sprites(0, Other(0, null)), emptyList()))
        dao.insert(pokemon1)
        val pokemon2 =
            Pokemon("andrei", "", PokemonDetails(0, Sprites(0, Other(0, null)), emptyList()))
        dao.insert(pokemon2)
        val pokemon3 =
            Pokemon("arthur", "", PokemonDetails(0, Sprites(0, Other(0, null)), emptyList()))
        dao.insert(pokemon3)

        val pokemon = dao.byId("arthur")
        assertThat(pokemon).isEqualTo(pokemon3)
    }

}