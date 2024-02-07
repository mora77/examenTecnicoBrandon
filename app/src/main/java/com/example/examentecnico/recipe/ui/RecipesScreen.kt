package com.example.examentecnico.recipe.ui

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.examentecnico.ui.theme.*
import com.example.examentecnico.R
import com.example.examentecnico.core.model.Routes
import com.example.examentecnico.recipe.data.AppMockup
import com.example.examentecnico.recipe.data.network.response.Recipe
import com.example.examentecnico.recipe.data.network.response.RecipeResponse

@Composable
fun RecipesScreen(recipesViewModel: RecipesViewModel, navController: NavHostController) {
    val isLoading: Boolean by recipesViewModel.isLoading.observeAsState(initial = true)
    recipesViewModel.getRecipes()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp, horizontal = 18.dp),
        verticalArrangement = if (isLoading) Arrangement.Center else Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.Black
            )
        } else {
            Header(Modifier.align(Alignment.End))
            recipesViewModel.result.value?.let {
                Body(
                    navController = navController,
                    modifier = Modifier,
                    recipe = it
                )
            }
        }
    }
}

@Composable
fun Body(navController: NavController, modifier: Modifier, recipe: RecipeResponse) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Title()
        Spacer(modifier = Modifier.size(35.dp))
        recipeRecycler(navController, recipe)
    }
}

@Composable
fun Title() {
    Column(Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.recipes_title), fontSize = 26.sp,
            fontWeight = FontWeight.Bold, color = Color.Black
        )
    }
}

@Composable
fun Header(modifier: Modifier) {
    val activity = LocalContext.current as Activity
    Icon(
        imageVector = Icons.Default.Close,
        tint = Color.Black,
        contentDescription = stringResource(id = R.string.description_close_app),
        modifier = modifier.clickable { activity.finish() })
}

@Composable
fun recipeRecycler(navController: NavController, recipe: RecipeResponse) {
    val rvState = rememberLazyListState()
    LazyColumn(modifier = Modifier.background(Color.White), state = rvState, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(recipe.result) { recipe ->
            itemRecipe(recipe = recipe) {
                navController.navigate(Routes.DetailScreen.createRoute(it.id.toString()))
            }
        }
    }
}

@Composable
fun itemRecipe(recipe: Recipe, onItemClicked: (Recipe) -> Unit) {
    Card(
        border = BorderStroke(2.dp, colorFlesh),
        modifier = Modifier
            .width(200.dp)
            .clickable { onItemClicked(recipe) }) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = recipe.nombre, modifier = Modifier.align(Alignment.CenterHorizontally))
            AsyncImage(
                model = AppMockup.BASE_IMAGE_URL + recipe.image,
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                error = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = stringResource(id = R.string.description_recipe_image),
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
    }
}