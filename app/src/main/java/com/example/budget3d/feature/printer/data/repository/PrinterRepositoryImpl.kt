package com.example.budget3d.feature.printer.data.repository

import com.example.budget3d.feature.printer.data.local.PrinterDao
import com.example.budget3d.feature.printer.data.mapper.toDomain
import com.example.budget3d.feature.printer.data.mapper.toEntity
import com.example.budget3d.feature.printer.domain.model.Printer
import com.example.budget3d.feature.printer.domain.repository.PrinterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PrinterRepositoryImpl @Inject constructor(
    private val dao: PrinterDao
) : PrinterRepository {
    override suspend fun savePrinter(printer: Printer) = dao.insertPrinter(printer.toEntity())

    override fun getAllPrinters(): Flow<List<Printer>> = dao.getAllPrinters().map { entities ->
        entities.map { it.toDomain() }
    }

    override suspend fun deletePrinter(printer: Printer) = dao.deletePrinter(printer.toEntity())
}