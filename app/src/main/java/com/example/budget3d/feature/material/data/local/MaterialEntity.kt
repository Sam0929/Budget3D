package com.example.budget3d.feature.material.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "materials")
data class MaterialEntity(
    @PrimaryKey val id: String,
    val name: String,
    val pricePerUnit: Double,
    val weightInGrams: Double
)