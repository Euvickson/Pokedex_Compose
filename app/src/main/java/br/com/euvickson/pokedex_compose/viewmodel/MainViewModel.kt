package br.com.euvickson.pokedex_compose.viewmodel

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

    var message: String? = null
    init {
        viewModelScope.launch {
            try {
                val pokemonListResponse = PokemonService.getPokemonInstance().getFullListPokemon().results

                pokemonListResponse.forEach {
                    val number = it.url.replace("https://pokeapi.co/api/v2/pokemon/", "")
                        .replace("/", "").toInt()

                    val resultPokemonInfo = PokemonService.getPokemonInstance().getPokemon(number)
                    if (resultPokemonInfo.types.size < 2) {
                        pokemonList.value += Pokemon(
                            pokedexId = resultPokemonInfo.id,
                            name = resultPokemonInfo.name,
                            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${number}.png",
                            type1 = resultPokemonInfo.types[0].type.name
                        )
                    } else {
                        pokemonList.value += Pokemon(
                            pokedexId = resultPokemonInfo.id,
                            name = resultPokemonInfo.name,
                            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${number}.png",
                            type1 = resultPokemonInfo.types[0].type.name,
                            type2 = resultPokemonInfo.types[1].type.name
                        )
                    }
                }
            } catch (e: Exception) {
                message = "Não foi possível carregar a lista de Pokemons. Verifique a sua conexão"
            }
        }
    }
}