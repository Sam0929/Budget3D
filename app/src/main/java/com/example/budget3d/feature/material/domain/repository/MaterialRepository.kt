package com.example.budget3d.feature.material.domain.repository

import com.example.budget3d.feature.material.domain.model.Material

interface MaterialRepository {
    suspend fun saveMaterial(material: Material)
}