package br.com.euvickson.pokedex_compose.model

import br.com.euvickson.pokedex_compose.api.model.PokemonMoves
import br.com.euvickson.pokedex_compose.api.model.Stats

data class Pokemon (
    val pokedexId: Int,
    val name: String,
    val imageUrl: String,
    val type1: String,
    val type2: String? = null,
    val moves: List<PokemonMoves>,
    val stats: List<Stats>,
    val evolutionOne: String? = null,
    val evolutionTwo: String? = null
        ) {
    val formattedNumber = pokedexId.toString().padStart(length = 3, padChar = '0')
}