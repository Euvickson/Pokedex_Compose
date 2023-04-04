package br.com.euvickson.pokedex_compose.api.model

data class PokemonListResponse (
    val results: List<PokemonResponse>
        )

data class PokemonResponse (
    val name: String,
    val url: String
        )

data class PokemonInfo(
    val id: Int,
    val name: String,
    val types: List<PokemonType>,
    val moves: List<PokemonMoves>
)

data class PokemonMoves (
    val move: Move,
//    val version_group_details: List
        )

data class Move (
    val name: String,
    val url: String
        )

data class PokemonType (
    val slot: Int,
    val type: Type
)

data class Type (
    val name: String
)
