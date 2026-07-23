package com.example.budget3d.feature.printer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "printers")
data class PrinterEntity(
    @PrimaryKey val id: String,
    val name: String,
    val machineCost: Double,
    val lifespanHours: Double,
    val powerConsumptionWatts: Double
)