package com.example.examentecnico.recipe.data.network.response

data class Details (
    val name: String,
    val image: String,
    val text: String,
    val ingredients: List<String>,
    val dish: String,
    val cockType: String,
    val prepTime: String,
    val cockTime: String,
    val rations: String,
    val longitude: String,
    val latitude: String
) {
    val isEmpty: Boolean get() { return name.isEmpty() && image.isEmpty() && text.isEmpty() }

    companion object {
        const val NAME = "nombre"
        const val IMAGE = "image"
        const val TEXT = "text"
        const val INGREDIENTS = "ingredients"
        const val DISH = "plato"
        const val COCK_TYPE = "cocina"
        const val PREP_TIME = "tiempo de preparación"
        const val COCK_TIME = "tiempo de cocción"
        const val RATIONS = "raciones"
        const val LONGITUDE = "longitud"
        const val LATITUDE = "latitud"

        val empty: Details
            get() {
                return Details(
                    String(), String(), String(), listOf(), String(),
                    String(), String(), String(), String(), String(), String(),
                )
            }
    }
}