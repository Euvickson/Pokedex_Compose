package br.com.euvickson.pokedex_compose.model

import br.com.euvickson.pokedex_compose.api.model.PokemonMoves

data class Pokemon (
    val pokedexId: Int,
    val name: String,
    val imageUrl: String,
    val type1: String,
    val type2: String? = null,
    val moves: List<PokemonMoves>
        ) {
    val formattedNumber = pokedexId.toString().padStart(length = 3, padChar = '0')
}