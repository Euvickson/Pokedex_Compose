package br.com.euvickson.pokedex_compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.euvickson.pokedex_compose.api.PokemonService
import br.com.euvickson.pokedex_compose.ui.theme.Pokedex_ComposeTheme
import br.com.euvickson.pokedex_compose.viewmodel.MainViewModel
import kotlinx.coroutines.launch

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
                    val mainviewModel = viewModel(modelClass = MainViewModel::class.java)
                    val list by mainviewModel.listFlow.collectAsState()

                    if(list.isNotEmpty()){
                        list.forEach {
                            Log.i("TesteAPI", "onCreate: ${it.name} ")
                        }
                    }

                    val coroutineScope = rememberCoroutineScope()

                    LaunchedEffect(key1 = Unit) {
                        coroutineScope.launch {
                            //val service = PokemonService.getPokemonInstance().getFullListPokemon()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}