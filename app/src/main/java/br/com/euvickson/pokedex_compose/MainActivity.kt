package br.com.euvickson.pokedex_compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import br.com.euvickson.pokedex_compose.model.Pokemon
import br.com.euvickson.pokedex_compose.ui.components.PokemonListItem
import br.com.euvickson.pokedex_compose.ui.theme.Pokedex_ComposeTheme
import br.com.euvickson.pokedex_compose.viewmodel.MainViewModel

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
                    val list by mainViewModel.listFlow.collectAsState()
                    val navController = rememberNavController()

                    CreatePokemonList(list)
                }
            }
        }
    }

    @Composable
    private fun CreatePokemonList(list: List<Pokemon>) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (list.isEmpty()) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxSize()
                            .wrapContentSize(align = Alignment.Center)
                    )
                }
            } else {
                items(list) { pokemon ->
                    PokemonListItem(pokemon = pokemon) {
                        Log.i("TESTE", "CreatePokemonList: ${it}")
                    }
                }
            }
        }
    }
}