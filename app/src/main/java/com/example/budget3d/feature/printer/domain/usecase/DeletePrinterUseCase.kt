package com.example.budget3d.feature.printer.domain.usecase

import com.example.budget3d.feature.printer.domain.repository.PrinterRepository
import com.example.budget3d.feature.printer.domain.model.Printer
import javax.inject.Inject

class DeletePrinterUseCase @Inject constructor(
    private val repository: PrinterRepository
) {
    suspend operator fun invoke(printer: Printer){
        repository.deletePrinter(printer)
    }
}