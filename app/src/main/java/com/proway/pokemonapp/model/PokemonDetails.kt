package com.proway.pokemonapp.model

import androidx.annotation.ColorInt
import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.proway.pokemonapp.R

@Entity
data class PokemonDetails(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "detail_id")
    val id: Int,

    @SerializedName("sprites")
    @Embedded
    val sprites: Sprites,

    @SerializedName("types")
    val type: List<Types>
)

@Entity
data class Sprites(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sprites_id")
    val id: Int,
    @SerializedName("other")
    @Embedded
    val other: Other
)

@Entity
data class Other(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "other_id")
    val id: Int,
    @SerializedName("official-artwork")
    @Embedded
    val artWork: ArtWork?
)

@Entity
data class ArtWork(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "artwork_id")
    val id: Int,
    @SerializedName("front_default")
    val image: String?
)


@Entity
data class Types(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "types_pokemon_id")
    val id: Int,
    var pokemonFk: Long,


    @SerializedName("slot")
    val slot: String,

    @Embedded
    @SerializedName("type")
    val type: PokemonType,
)

@Entity
data class PokemonType(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pokemon_type_name")
    val id: Int,

    @SerializedName("name")
    val typeName: String,

    @ColumnInfo(name = "pokemon_type_url")
    @SerializedName("url")
    val typeUrl: String,
) {

    fun extractBgColor(): Int {
        return when (typeName) {
            "bug" -> R.color.bg_type_bug
            "water" -> R.color.bg_type_water
            "fairy" -> R.color.bg_type_fairy
            "ghost" -> R.color.bg_type_ghost
            "normal" -> R.color.bg_type_normal
            "dark" -> R.color.bg_type_dark
            "figthing" -> R.color.bg_type_figthing
            "grass" -> R.color.bg_type_grass
            "poison" -> R.color.bg_type_poison
            "dragon" -> R.color.bg_type_dragon
            "fire" -> R.color.bg_type_fire
            "ground" -> R.color.bg_type_ground
            "psychic" -> R.color.bg_type_psychic
            "eletric" -> R.color.bg_type_eletric
            "flying" -> R.color.bg_type_flying
            "ice" -> R.color.bg_type_ice
            "rock" -> R.color.bg_type_rock
            else -> R.color.bg_type_normal
        }
    }


}