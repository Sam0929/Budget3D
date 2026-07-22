package com.example.budget3d.feature.material.domain.usecase

import com.example.budget3d.core.domain.util.IdGenerator
import com.example.budget3d.feature.material.domain.model.Material
import com.example.budget3d.feature.material.domain.repository.MaterialRepository
import javax.inject.Inject

class AddMaterialUseCase @Inject constructor(
    private val repository: MaterialRepository,
    private val idGenerator: IdGenerator
) {
    suspend operator fun invoke(name: String, pricePerUnit: Double, weightInGrams: Double) {
        // Validações de negócio (Domain logic)
        require(name.isNotBlank()) { "O nome do material não pode ser vazio." }
        require(pricePerUnit > 0.0) { "O preço deve ser maior que zero." }
        require(weightInGrams > 0.0) { "O peso deve ser maior que zero." }

        val newMaterial = Material(
            id = idGenerator.generate(), // Geração agnóstica
            name = name,
            pricePerUnit = pricePerUnit,
            weightInGrams = weightInGrams
        )

        repository.saveMaterial(newMaterial)
    }
}