package com.example.budget3d.feature.printer.domain.usecase

import com.example.budget3d.core.domain.util.IdGenerator
import com.example.budget3d.feature.printer.domain.model.Printer
import com.example.budget3d.feature.printer.domain.repository.PrinterRepository
import javax.inject.Inject

class AddPrinterUseCase @Inject constructor(
    private val repository: PrinterRepository,
    private val idGenerator: IdGenerator
) {
    suspend operator fun invoke(
        name: String,
        machineCost: Double,
        lifespanHours: Double,
        powerConsumptionWatts: Double
    ) {
        require(name.isNotBlank()) { "O nome da impressora não pode ser vazio." }
        require(machineCost >= 0.0) { "O custo da máquina não pode ser negativo." }
        require(lifespanHours > 0.0) { "A vida útil deve ser maior que zero." }
        require(powerConsumptionWatts >= 0.0) { "O consumo de energia não pode ser negativo." }

        val printer = Printer(
            id = idGenerator.generate(),
            name = name,
            machineCost = machineCost,
            lifespanHours = lifespanHours,
            powerConsumptionWatts = powerConsumptionWatts
        )

        repository.savePrinter(printer)
    }
}