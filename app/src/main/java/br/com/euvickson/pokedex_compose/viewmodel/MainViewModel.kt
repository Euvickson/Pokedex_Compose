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

        }
    }
}