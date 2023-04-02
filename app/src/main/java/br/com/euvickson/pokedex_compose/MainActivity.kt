package br.com.euvickson.pokedex_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.euvickson.pokedex_compose.model.Pokemon
import br.com.euvickson.pokedex_compose.ui.components.PokemonListItem
import br.com.euvickson.pokedex_compose.ui.theme.Pokedex_ComposeTheme
import br.com.euvickson.pokedex_compose.viewmodel.MainViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pokedex_ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val mainViewModel = viewModel(modelClass = MainViewModel::class.java)
                    val pokemonList by mainViewModel.listFlow.collectAsState()
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") {
                            ListScreen(pokemonList, navController)
                        }
                        composable("description/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })) {
                            val pokemon = pokemonList.find { pokemon -> pokemon.pokedexId == it.arguments?.getInt("id") }
                            DetailScreen(pokemon)
                        }
                    }

                }
            }
        }
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    private fun DetailScreen(nullablePokemon: Pokemon?) {
        nullablePokemon?.let {pokemon ->

            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(Icons.Rounded.ArrowBack, contentDescription = "")
                    Text(text = "Pokemon Nº #${pokemon.formattedNumber}")
                    Icon(Icons.Rounded.ArrowForward, contentDescription = "")
                }

                GlideImage(
                    model = pokemon.imageUrl,
                    contentDescription = "Pokemon Image",
                    modifier = Modifier.height(200.dp).width(200.dp)
                )
            }

        } ?: Text(text = "An error occurred during the detail loading")
    }

    @Composable
    private fun ListScreen(pokemonList: List<Pokemon>, navController: NavHostController) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (pokemonList.isEmpty()) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxSize()
                            .wrapContentSize(align = Alignment.Center)
                    )
                }
            } else {
                items(pokemonList) { pokemon ->
                    PokemonListItem(pokemon = pokemon) {
                        navController.navigate("description/${pokemon.pokedexId}")
                    }
                }
            }
        }
    }
}