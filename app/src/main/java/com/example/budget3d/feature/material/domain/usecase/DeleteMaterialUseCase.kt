package com.example.budget3d.feature.material.domain.usecase

import com.example.budget3d.feature.material.domain.model.Material
import com.example.budget3d.feature.material.domain.repository.MaterialRepository
import javax.inject.Inject

class DeleteMaterialUseCase @Inject constructor(
    private val repository: MaterialRepository
) {
    suspend operator fun invoke(material: Material) {
        repository.deleteMaterial(material)
    }

}