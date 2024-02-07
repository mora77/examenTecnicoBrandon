package com.example.examentecnico.recipe.data.network

import com.example.examentecnico.recipe.data.network.response.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET

interface RecipeClient {
    @GET("/recipes")
    suspend fun getRecipes(): Response<RecipeResponse>
}