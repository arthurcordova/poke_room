package com.proway.pokemonapp.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

data class PokeResponse(
    val results: List<Pokemon>
)

/**
 * Colocamos a annotation @Entity para identificar como uma table para nosso database.
 */
@Entity
data class Pokemon(
    /**
     * @PrimaryKey annotation para identificar qual o atributo que será a chave primaria da tabela
     */
    @PrimaryKey
    /**
     * @ColumnInfo annotation para modificar o node do atributo quando visto da tabela
     */
    @ColumnInfo(name = "poke_name")
    @SerializedName("name")
    val name: String,

    @ColumnInfo(name = "poke_url")
    @SerializedName("url")
    val url: String,

    @Embedded
    var details: PokemonDetails
) {


    fun extractIdFromUrl(withPads: Boolean = false): String {
        val listStr = url.split("/")
        return if (withPads) listStr[6].padStart(4, '0') else listStr[6]
    }

}
