package com.example.examentecnico.recipe.domain

import com.example.examentecnico.recipe.data.RecipeRepository
import com.example.examentecnico.recipe.data.network.response.Details
import javax.inject.Inject

class DetailUseCase @Inject constructor(private val repository: RecipeRepository) {

    operator fun invoke(idDetail: String): Details {
        return repository.getIdDetail(idDetail)
    }
}