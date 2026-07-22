package com.example.budget3d.feature.material.data.mapper

import com.example.budget3d.feature.material.data.local.MaterialEntity
import com.example.budget3d.feature.material.domain.model.Material

// Converte do Banco de Dados para o Domínio
fun MaterialEntity.toDomain(): Material {
    return Material(
        id = id,
        name = name,
        pricePerUnit = pricePerUnit,
        weightInGrams = weightInGrams
    )
}

// Converte do Domínio para o Banco de Dados
fun Material.toEntity(): MaterialEntity {
    return MaterialEntity(
        id = id,
        name = name,
        pricePerUnit = pricePerUnit,
        weightInGrams = weightInGrams
    )
}