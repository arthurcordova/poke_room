package com.proway.pokemonapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
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
) {

//    @Ignore
//    val id: String by lazy {
//        extractIdFromUrl()
//    }

    fun extractIdFromUrl(): String {
        val listStr = url.split("/")
        return listStr[6]
    }

}
