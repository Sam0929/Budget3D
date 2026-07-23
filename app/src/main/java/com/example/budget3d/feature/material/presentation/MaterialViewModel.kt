package com.example.budget3d.feature.material.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget3d.feature.material.domain.model.Material
import com.example.budget3d.feature.material.domain.repository.MaterialRepository
import com.example.budget3d.feature.material.domain.usecase.AddMaterialUseCase
import com.example.budget3d.feature.material.domain.usecase.DeleteMaterialUseCase
import com.example.budget3d.feature.material.presentation.MaterialUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MaterialViewModel @Inject constructor(
    private val materialRepository: MaterialRepository,
    private val addMaterialUseCase: AddMaterialUseCase,
    private val deleteMaterialUseCase: DeleteMaterialUseCase
) : ViewModel() {

    val uiState: StateFlow<MaterialUiState> = materialRepository.getAllMaterials()
        .map { materiais ->
            MaterialUiState(materials = materiais, isLoading = false)
        }
        .catch { excecao ->
            emit(MaterialUiState(isLoading = false, error = excecao.message))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MaterialUiState(isLoading = true)
        )

    fun addMaterial(name: String, price: Double, weight: Double) {
        viewModelScope.launch {                                                                  // viewModelScope é um escopo de coroutines
            try {                                                                                // gerenciador de tarefas assincronas
                addMaterialUseCase(name = name, pricePerUnit = price, weightInGrams = weight)
            } catch (e: IllegalArgumentException) {
                //TODO : Exception
            }
        }
    }

    fun deleteMaterial(material: Material){
        viewModelScope.launch {                    // viewModelScope.launch cria uma couroutine, execucao em paralelo
            deleteMaterialUseCase(material)        // alem disso uma funcao suspend so pode ser chamado no contexto
        }                                          // de uma funcao suspend ou de uma courotine
    }
}