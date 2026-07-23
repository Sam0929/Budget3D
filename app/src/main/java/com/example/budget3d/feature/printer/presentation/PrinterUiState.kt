package com.example.budget3d.feature.printer.presentation

import com.example.budget3d.feature.printer.domain.model.Printer

data class PrinterUiState(
    val printers: List<Printer> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)
