package com.example.examentecnico.recipe.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.examentecnico.recipe.data.network.response.Details
import com.example.examentecnico.recipe.data.network.response.RecipeResponse
import com.example.examentecnico.recipe.domain.DetailUseCase
import com.example.examentecnico.recipe.domain.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase,
    private val detailUseCase: DetailUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _result = MutableLiveData<RecipeResponse>()
    val result: LiveData<RecipeResponse> = _result

    private val _detail = MutableLiveData<Details>()
    val detail: LiveData<Details> = _detail

    fun getRecipes() {
        CoroutineScope(Dispatchers.IO).launch {
            recipeUseCase().let {
                _isLoading.postValue(true)
                if (it.result.isNotEmpty()) {
                    _result.postValue(it)
                } else {
                    //return json
                }
                _isLoading.postValue(false)
            }
        }
    }

    fun getRecipeDetail(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            detailUseCase(idDetail= id).let {
                _isLoading.postValue(true)
                if (!it.isEmpty) {
                    _detail.postValue(it)
                } else {
                    //return json
                }
                _isLoading.postValue(false)
            }
        }
    }
}