package com.example.examentecnico.recipe.domain

import com.example.examentecnico.recipe.data.RecipeRepository
import com.example.examentecnico.recipe.data.network.response.RecipeResponse
import javax.inject.Inject

class RecipeUseCase @Inject constructor(private val repository: RecipeRepository) {

    operator fun invoke(): RecipeResponse {
        return repository.getRecipes()
    }
}