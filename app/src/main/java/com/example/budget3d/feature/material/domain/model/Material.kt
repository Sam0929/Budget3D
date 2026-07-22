package com.example.budget3d.feature.material.domain.model

data class Material(
    val id: String,
    val name: String,
    val pricePerUnit: Double, // Preço do rolo (ex: R$ 150,00)
    val weightInGrams: Double // Peso total do rolo (ex: 1000g)
) {
    val costPerGram: Double
        get() = pricePerUnit / weightInGrams
}