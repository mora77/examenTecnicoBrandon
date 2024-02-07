package com.example.examentecnico.core.model

sealed class Routes(val idRoute: String) {
    object RecipesScreen : Routes("recipesScreen")
    object DetailScreen : Routes("detailScreen/{id}") {
        fun createRoute(id: String) = "detailScreen/$id"
    }
}