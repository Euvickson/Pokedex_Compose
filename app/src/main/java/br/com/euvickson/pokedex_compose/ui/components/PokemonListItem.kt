package br.com.euvickson.pokedex_compose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import br.com.euvickson.pokedex_compose.model.Pokemon
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PokemonListItem(pokemon: Pokemon) {
    Column() {
        GlideImage(model = pokemon.imageUrl, contentDescription = "Pokemon Image")
        Row() {
            //pokemon.pokemonTypes.forEach {
              //  Text(text = it.type.name)
           // }
        }
    }
}
