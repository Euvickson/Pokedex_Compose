package br.com.euvickson.pokedex_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                        composable(
                            "description/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.IntType })
                        ) {
                            val pokemon = pokemonList.find { pokemon ->
                                pokemon.pokedexId == it.arguments?.getInt("id")
                            }
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

        var isClicked by remember { mutableStateOf(false) }
        var statusIsClicked by remember { mutableStateOf(false) }

        nullablePokemon?.let { pokemon ->

            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(Icons.Rounded.ArrowBack, contentDescription = "")
                    Text(text = "Pokemon Nº #${pokemon.formattedNumber}")
                    Icon(Icons.Rounded.ArrowForward, contentDescription = "")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    GlideImage(
                        model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/${pokemon.pokedexId}.gif",
                        contentDescription = "Pokemon Image",
                        modifier = Modifier
                            .heightIn(max = 125.dp, min = 100.dp)
                            .widthIn(max = 125.dp, min = 100.dp)
                    )

                    GlideImage(
                        model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/shiny/${pokemon.pokedexId}.gif",
                        contentDescription = "Pokemon Image",
                        modifier = Modifier
                            .heightIn(max = 125.dp, min = 100.dp)
                            .widthIn(max = 125.dp, min = 100.dp)
                    )
                }

                IconButton(
                    onClick = {
                        isClicked = !isClicked
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .width(200.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF65CABB))
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "MOVES", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Icon(
                            if (isClicked) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowRight,
                            "Icon Moves"
                        )
                    }
                }

                AnimatedVisibility(visible = isClicked) {
                    LazyHorizontalGrid(
                        rows = GridCells.Fixed(2), modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {
                        pokemon.moves.forEach { move ->
                            if (move.version_group_details[0].level_learned_at != 0) {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .clip(RoundedCornerShape(20.dp))
                                            .background(Color(0xFFAAFF00))
                                            .padding(horizontal = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "${move.version_group_details[0].level_learned_at}",
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Icon(
                                                Icons.Rounded.ArrowForward,
                                                "Level Arrow",
                                                modifier = Modifier
                                            )
                                            Text(
                                                text = move.move.name,
                                                fontSize = 20.sp,
                                                fontStyle = FontStyle.Italic
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                IconButton(
                    onClick = {
                        statusIsClicked = !statusIsClicked
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .width(200.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF65CABB)),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "STATS",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                        )
                        Icon(
                            if (statusIsClicked) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowRight,
                            "Status Button"
                        )
                    }
                }

                AnimatedVisibility(visible = statusIsClicked, modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFCACACA))) {
                    Column {
                        pokemon.stats.forEach {stat ->
                            Text(text = "${stat.stat.name.uppercase()} -> ${stat.base_stat}",
                                modifier = Modifier.align(Alignment.CenterHorizontally))
                        }
                    }
                }

                pokemon.evolutionOne?.let {
                    Text(text = it)
                }
                pokemon.evolutionTwo?.let {
                    Text(text = it)
                }

            }

        } ?: Text(text = "An error occurred during the pokemon detail loading")
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