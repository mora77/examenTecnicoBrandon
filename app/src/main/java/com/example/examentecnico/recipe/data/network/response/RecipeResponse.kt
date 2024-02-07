package com.example.examentecnico.recipe.data.network.response

import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    @SerializedName("result") val result: List<Recipe>,
)

data class Recipe(
    @SerializedName("id") var id: Long,
    @SerializedName("nombre") var nombre: String,
    @SerializedName("image") var image: String
) {
    companion object {
        const val ID = "id"
        const val NAME = "nombre"
        const val IMAGE = "image"

        val empty: Recipe
            get() {
                return Recipe(-1, String(), String())
            }
    }
}
