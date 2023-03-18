package br.com.euvickson.pokedex_compose.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import br.com.euvickson.pokedex_compose.model.Pokemon
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PokemonListItem(pokemon: Pokemon) {
    Column(Modifier.fillMaxWidth().height(300.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        GlideImage(
            model = pokemon.imageUrl,
            contentDescription = "Pokemon Image",
            modifier = Modifier
        )
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Text(text = pokemon.type1)
            pokemon.type2?.let { Text(text = it) }
        }
    }
}
