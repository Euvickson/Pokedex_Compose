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
    val types: List<PokemonType>
)

data class PokemonType (
    val slot: Int,
    val type: Type
)

data class Type (
    val name: String
)
