package br.com.euvickson.pokedex_compose.model

data class Pokemon (
    val pokedexId: Int,
    val name: String,
    val imageUrl: String,
    val type1: String,
    val type2: String? = null
        ) {
    val formattedNumber = pokedexId.toString().padStart(length = 3, padChar = '0')
}