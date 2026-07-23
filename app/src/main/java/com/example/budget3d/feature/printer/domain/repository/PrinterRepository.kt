package com.example.budget3d.feature.printer.domain.repository

import com.example.budget3d.feature.printer.domain.model.Printer
import kotlinx.coroutines.flow.Flow

interface PrinterRepository {
    suspend fun savePrinter(printer: Printer)
    fun getAllPrinters(): Flow<List<Printer>>
    suspend fun deletePrinter(printer: Printer)
}