package br.com.euvickson.pokedex_compose.api

import br.com.euvickson.pokedex_compose.api.model.PokemonInfo
import br.com.euvickson.pokedex_compose.api.model.PokemonListResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon")
    suspend fun getFullListPokemon(@Query("limit") limit: Int = 151): PokemonListResponse

    @GET("pokemon/{number}")
    suspend fun getPokemon(@Path("number") number: Int): PokemonInfo

    companion object {
        private var pokemonService: PokemonService? = null

        fun getPokemonInstance(): PokemonService {
            if (pokemonService == null) {
                pokemonService = Retrofit
                    .Builder()
                    .baseUrl("https://pokeapi.co/api/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create()
            }
            return pokemonService!!
        }
    }
}