package com.hvasoft.superheroes.presentation.main_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hvasoft.superheroes.core.exceptions.TypeError
import com.hvasoft.superheroes.data.model.Superhero
import com.hvasoft.superheroes.domain.use_case.GetSuperheroesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSuperheroesUseCase: GetSuperheroesUseCase
) : ViewModel() {

    private var _stateFlow = MutableStateFlow(listOf<Superhero>())
    val stateFlow: StateFlow<List<Superhero>> get() = _stateFlow

    private var _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private var _typeError = MutableStateFlow(TypeError.NONE)
    val typeError: StateFlow<TypeError> get() = _typeError

    init {
        getSuperheroes()
    }

    fun getSuperheroes(lastId: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                if (_stateFlow.value.isEmpty()) {
                    _stateFlow.value = getSuperheroesUseCase(lastId)
                } else {
                    val mutableSet = stateFlow.value.toMutableSet()
                    mutableSet.addAll(getSuperheroesUseCase(lastId))
                    _stateFlow.value = mutableSet.toList()
                }
            } catch (e: IOException) {
                _typeError.value = TypeError.NETWORK
            } finally {
                _isLoading.value = false
            }
        }
    }

}