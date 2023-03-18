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


    init {
        viewModelScope.launch {
            val pokemonListResponse = PokemonService.getPokemonInstance().getFullListPokemon().results

            pokemonListResponse.forEach {
                val number = it.url.replace("https://pokeapi.co/api/v2/pokemon/", "")
                    .replace("/", "").toInt()

                val resultPokemonInfo = PokemonService.getPokemonInstance().getPokemon(number)

                pokemonList.value += Pokemon(
                    pokedexId = resultPokemonInfo.id,
                    name = resultPokemonInfo.name,
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${number}.png",
                    type1 = resultPokemonInfo.types[0].type.name
                )
            }
        }
    }
}