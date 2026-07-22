package com.example.budget3d.feature.material.domain.repository

import com.example.budget3d.feature.material.domain.model.Material
import kotlinx.coroutines.flow.Flow

interface MaterialRepository {
    suspend fun saveMaterial(material: Material)

    fun getAllMaterials(): Flow<List<Material>>
}