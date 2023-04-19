package br.com.euvickson.pokedex_compose.api.model

data class PokemonSpeciesResponse(
    val evolution_chain: EvolutionChain
)

data class EvolutionChain (
    val url: String
        )
