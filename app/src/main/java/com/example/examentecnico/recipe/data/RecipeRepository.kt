package com.example.examentecnico.recipe.data

import android.util.JsonReader
import android.util.Log
import com.example.examentecnico.recipe.data.network.response.Details
import com.example.examentecnico.recipe.data.network.response.Recipe
import com.example.examentecnico.recipe.data.network.response.RecipeResponse
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import javax.inject.Inject

class RecipeRepository @Inject constructor(){
        fun getRecipes(): RecipeResponse {
            val TAG = "NETWORK_CALL_SUCCESS"
            val ERROR_TAG = "NETWORK_CALL_ERROR"
            try {
                val url = URL(AppMockup.BASE_URL + AppMockup.RECIPES)
                val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
                try {
                    val input: InputStream = BufferedInputStream(urlConnection.inputStream)
                    Log.d("recipe", TAG)
                    //input.toString().dlog(TAG)
                    val reader = JsonReader(InputStreamReader(input, "UTF-8"))
                    return try {
                        reader.getResults()
                    } catch (io: IOException) {
                        io.printStackTrace()
                        RecipeResponse(listOf())
                    }
                } catch (e: IOException) {
                    Log.e("recipe", ERROR_TAG)
                    //urlConnection.errorStream.toString().elog(ERROR_TAG)
                    return RecipeResponse(listOf())
                } finally {
                    urlConnection.disconnect()
                }
            } catch (e: MalformedURLException) {
                e.printStackTrace()
                return RecipeResponse(listOf())
            } catch (e: IOException) {
                e.printStackTrace()
                return RecipeResponse(listOf())
            }
        }

        fun getIdDetail(id: String): Details {
            val TAG = "NETWORK_CALL_SUCCESS"
            try {
                val path = (AppMockup.BASE_URL+AppMockup.DETAIL+id)
                val url = URL(path)
                val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
                try {
                    val input: InputStream = BufferedInputStream(urlConnection.inputStream)
                    //path.dlog("NETWORK_START_:")
                    Log.d("recipe", "NETWORK_START")
                    val reader = JsonReader(InputStreamReader(input, "UTF-8"))
                    return try {
                        reader.getDetails().apply { Log.d("recipe", TAG)}
                    } catch (io: IOException) {
                        io.printStackTrace()
                        Details.empty
                    }
                } catch (e: IOException) {
                    urlConnection.errorStream.toString()
                    return Details.empty
                } finally {
                    urlConnection.disconnect()
                }
            } catch (e: MalformedURLException) {
                e.printStackTrace()
                return Details.empty
            } catch (e: IOException) {
                e.printStackTrace()
                return Details.empty
            }
        }

        private fun JsonReader.getResults(): RecipeResponse {
            val recipes: ArrayList<Recipe> = arrayListOf()
            beginObject()
            while (hasNext()) {
                val key = nextName()
                if (key == "result") {
                    beginArray()
                    while (hasNext()) {
                        getRecipe()?.let { recipes.add(it) }
                    }
                    endArray()
                } else {
                    skipValue()
                }
            }
            endObject()
            return RecipeResponse(recipes.toList())
        }

        private fun JsonReader.getRecipe(): Recipe? {
            beginObject()
            var (id, name, image) = Recipe.empty
            while (hasNext()) {
                when (nextName()) {
                    Recipe.ID -> id = try {
                        nextLong()
                    } catch (e: IOException) {
                        -1L
                    }

                    Recipe.NAME -> name = try {
                        nextString()
                    } catch (e: IllegalStateException) {
                        String()
                    }

                    Recipe.IMAGE -> image = try {
                        nextString()
                    } catch (e: IllegalStateException) {
                        String()
                    }

                    else -> skipValue()
                }
            }
            endObject()
            return if (id != -1L) Recipe(id, name, image) else null
        }

        private fun JsonReader.getDetails(): Details {
            var (name, image, text, ingredients, dish, cockType, prepTime, cockTime, rations, longitude, latitude) = Details.empty
            beginObject()
            while (hasNext()) {
                when (nextName()) {
                    Details.NAME -> name = try {
                        nextString()
                    } catch (e: IllegalStateException) {
                        String()
                    }

                    Details.IMAGE -> image = try {
                        nextString()
                    } catch (e: IllegalStateException) {
                        String()
                    }

                    Details.TEXT -> text = try {
                        nextString()
                    } catch (e: IllegalStateException) {
                        String()
                    }

                    Details.INGREDIENTS -> ingredients = getIngredients()
                    Details.DISH -> dish = try {
                        nextString()
                    } catch (e: IllegalStateException) {
                        String()
                    }

                    Details.COCK_TYPE -> cockType = try {
                        nextString()
                    } catch (e: IllegalStateException) {
                        String()
                    }

                    Details.PREP_TIME -> prepTime = try {
                        nextString()
                    } catch (e: IllegalStateException) {
                        String()
                    }

                    Details.COCK_TIME -> cockTime = try {
                        nextString()
                    } catch (e: IllegalStateException) {
                        String()
                    }

                    Details.RATIONS -> rations = try {
                        nextString()
                    } catch (e: IllegalStateException) {
                        String()
                    }

                    Details.LONGITUDE -> longitude = try {
                        nextString()
                    } catch (e: IllegalStateException) {
                        String()
                    }

                    Details.LATITUDE -> latitude = try {
                        nextString()
                    } catch (e: IllegalStateException) {
                        String()
                    }

                    else -> skipValue()
                }
            }
            endObject()
            return Details(
                name,
                image,
                text,
                ingredients,
                dish,
                cockType,
                prepTime,
                cockTime,
                rations,
                longitude,
                latitude
            )
        }

        private fun JsonReader.getIngredients(): List<String> {
            val list = arrayListOf<String>()
            beginArray()
            while (hasNext()) {
                try {
                    nextString()
                } catch (e: IllegalStateException) {
                    String()
                }.takeIf { it.isNotBlank() }?.let { list.add(it) }
            }
            endArray()
            return list
        }
    }

