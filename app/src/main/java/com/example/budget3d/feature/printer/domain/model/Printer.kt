package com.example.budget3d.feature.printer.domain.model

data class Printer(
    val id: String,
    val name: String,
    val machineCost: Double, // Valor pago na impressora
    val lifespanHours: Double, // Vida útil estimada em horas (ex: 5000h)
    val powerConsumptionWatts: Double // Consumo médio (ex: 300W para PETG/ABS)
) {
    val depreciationPerHour: Double
        get() = machineCost / lifespanHours

    fun getEnergyConsumptionKwh(hours: Double): Double {
        return (powerConsumptionWatts / 1000.0) * hours
    }
}