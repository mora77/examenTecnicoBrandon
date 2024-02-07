package com.example.examentecnico.recipe.data.network

import com.example.examentecnico.recipe.data.network.response.RecipeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

//clase que consume al cliente
class RecipeService @Inject constructor(private val recipeClient: RecipeClient) {

    suspend fun getRecipe(): RecipeResponse {
        return withContext(Dispatchers.IO) {
            val response = recipeClient.getRecipes()
            response.body() ?: RecipeResponse(listOf())
        }
    }
}