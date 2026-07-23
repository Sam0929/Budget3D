package com.example.budget3d.feature.material.data.repository

import com.example.budget3d.feature.material.data.local.MaterialDao
import com.example.budget3d.feature.material.data.mapper.toDomain
import com.example.budget3d.feature.material.data.mapper.toEntity
import com.example.budget3d.feature.material.domain.model.Material
import com.example.budget3d.feature.material.domain.repository.MaterialRepository
import kotlin.collections.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MaterialRepositoryImpl @Inject constructor(
    private val dao: MaterialDao
) : MaterialRepository {

    override suspend fun saveMaterial(material: Material) {
        // Converte o modelo puro em modelo de banco antes de salvar
        dao.insertMaterial(material.toEntity())
    }
    override suspend fun deleteMaterial(material: Material) {
        dao.deleteMaterial(material.toEntity())
    }

    override fun getAllMaterials(): Flow<List<Material>> {
        // O banco retorna um Flow de Entities.
        return dao.getAllMaterials().map { entities ->
            entities.map { it.toDomain() }
        }
    }
}