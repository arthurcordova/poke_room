package com.proway.pokemonapp.utils

fun String.toUpperFirstChar(): String {
    return replaceFirstChar { it.uppercase() }
}