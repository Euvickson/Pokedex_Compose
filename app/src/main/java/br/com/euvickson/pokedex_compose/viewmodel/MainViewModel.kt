package br.com.euvickson.pokedex_compose.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.euvickson.pokedex_compose.api.PokemonService
import br.com.euvickson.pokedex_compose.model.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val pokemonList = MutableStateFlow(emptyList<Pokemon>())
    val listFlow: StateFlow<List<Pokemon>>
        get() = pokemonList

    private var message: String? = null
    init {
        viewModelScope.launch {
            try {
                val pokemonservice = PokemonService.getPokemonInstance()
                val pokemonListResponse = pokemonservice.getFullListPokemon().results

                pokemonListResponse.forEach {
                    val pokemonNumber = it.url.replace("https://pokeapi.co/api/v2/pokemon/", "")
                        .replace("/", "").toInt()

                    val resultPokemonInfo = pokemonservice.getPokemon(pokemonNumber)
                    val pokemonSpeciesInfo = pokemonservice.getSpecies(pokemonNumber)
                    val speciesNumber = pokemonSpeciesInfo.evolution_chain.url.replace(
                        "https://pokeapi.co/api/v2/evolution-chain/",
                        ""
                    ).replace("/", "").toInt()

                    var mapNumber = 1

                    val pokemonEvolutions = pokemonservice.getEvolution(speciesNumber)
                    val evolutions = mutableMapOf(pokemonEvolutions.chain.species.name to mapNumber)

                    if (pokemonEvolutions.chain.evolves_to.isNotEmpty()) {
                        pokemonEvolutions.chain.evolves_to.forEach {evolution ->
                            mapNumber++
                            evolutions += evolution.species.name to mapNumber
                        }
                        if (pokemonEvolutions.chain.evolves_to[0].evolves_to.isNotEmpty()) {
                            mapNumber++
                            evolutions += pokemonEvolutions.chain.evolves_to[0].evolves_to[0].species.name to mapNumber
                        }
                    }

                    if (resultPokemonInfo.types.size < 2) {
                        pokemonList.value += Pokemon(
                            pokedexId = resultPokemonInfo.id,
                            name = resultPokemonInfo.name,
                            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemonNumber}.png",
                            type1 = resultPokemonInfo.types[0].type.name,
                            moves = resultPokemonInfo.moves,
                            stats = resultPokemonInfo.stats,
                            evolutions = evolutions
                        )
                    } else {
                        pokemonList.value += Pokemon(
                            pokedexId = resultPokemonInfo.id,
                            name = resultPokemonInfo.name,
                            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemonNumber}.png",
                            type1 = resultPokemonInfo.types[0].type.name,
                            type2 = resultPokemonInfo.types[1].type.name,
                            moves = resultPokemonInfo.moves,
                            stats = resultPokemonInfo.stats,
                            evolutions = evolutions
                        )
                    }
                }
            } catch (e: Exception) {
                message = "Não foi possível carregar a lista de Pokemons. Verifique a sua conexão"
            }
        }
    }
}