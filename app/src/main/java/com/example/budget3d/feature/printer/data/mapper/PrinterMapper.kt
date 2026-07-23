package com.example.budget3d.feature.printer.data.mapper

import com.example.budget3d.feature.printer.data.local.PrinterEntity
import com.example.budget3d.feature.printer.domain.model.Printer

fun PrinterEntity.toDomain() = Printer(
    id = id,
    name = name,
    machineCost = machineCost,
    lifespanHours = lifespanHours,
    powerConsumptionWatts = powerConsumptionWatts
)

fun Printer.toEntity() = PrinterEntity(
    id = id,
    name = name,
    machineCost = machineCost,
    lifespanHours = lifespanHours,
    powerConsumptionWatts = powerConsumptionWatts
)