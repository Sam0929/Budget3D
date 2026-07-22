package com.example.budget3d.feature.material.presentation

import com.example.budget3d.feature.material.domain.model.Material

data class MaterialUiState(
    val materials: List<Material> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)
