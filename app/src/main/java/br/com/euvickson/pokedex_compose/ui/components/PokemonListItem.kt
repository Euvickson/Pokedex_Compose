package br.com.euvickson.pokedex_compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.euvickson.pokedex_compose.model.Pokemon
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PokemonListItem(pokemon: Pokemon, onNavigate: (Int) -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clickable {onNavigate(pokemon.pokedexId)},
        horizontalAlignment = Alignment.CenterHorizontally) {
        GlideImage(
            model = pokemon.imageUrl,
            contentDescription = "Pokemon Image",
            modifier = Modifier
        )
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(text = "Nº #${pokemon.formattedNumber}")

                Text(pokemon.name, fontWeight = FontWeight.Bold, fontSize = 24.sp)

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = pokemon.type1, modifier = Modifier
                        .background(Color.LightGray)
                        .padding(horizontal = 32.dp))
                    pokemon.type2?.let { Text(text = it, modifier = Modifier
                        .background(Color.LightGray)
                        .padding(horizontal = 32.dp)) }
                }

            }
        }
    }
}
