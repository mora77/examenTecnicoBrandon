package com.example.examentecnico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.examentecnico.core.model.Routes.*
import com.example.examentecnico.recipe.ui.DetailScreen
import com.example.examentecnico.recipe.ui.RecipesScreen
import com.example.examentecnico.recipe.ui.RecipesViewModel
import com.example.examentecnico.ui.theme.ExamenTecnicoTheme
import com.example.examentecnico.ui.theme.backgroundTopaz
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val recipesViewModel: RecipesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenTecnicoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = backgroundTopaz
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = RecipesScreen.idRoute
                    ) {
                        composable(RecipesScreen.idRoute) {
                            RecipesScreen(
                                recipesViewModel = recipesViewModel,
                                navController = navController
                            )
                        }

                        composable(
                            DetailScreen.idRoute,
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        ) {
                            idBackStackEntry ->
                            DetailScreen(
                                recipesViewModel = recipesViewModel,
                                navController = navController,
                                id = idBackStackEntry.arguments?.getString("id").orEmpty()
                            )
                        }
                    }
                }
            }
        }
    }
}