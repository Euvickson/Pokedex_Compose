package br.com.euvickson.pokedex_compose.api.model

data class PokemonEvolutionChainResponse(
    val chain: Chain
)

data class Chain (
    val species: Species,
    val evolves_to: List<FirstEvolution>
        )

data class FirstEvolution (
    val species: Species,
    val evolves_to: List<SecondEvolution>
        )

data class SecondEvolution (
    val species: Species,
        )


data class Species (
    val name: String,
    val url: String
)
