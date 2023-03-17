package br.com.euvickson.pokedex_compose.model

data class Pokemon (
    val name: String,
    val imageUrl: String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
    val pokemonTypes: List<PokemonType>
        )

data class PokemonType (
    val slot: Int,
    val type: Type
        )

data class Type (
    val name: String = "Grass"
        )