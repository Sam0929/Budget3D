package com.example.budget3d.feature.printer.presentation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget3d.feature.printer.domain.model.Printer
import com.example.budget3d.feature.printer.domain.repository.PrinterRepository
import com.example.budget3d.feature.printer.domain.usecase.AddPrinterUseCase
import com.example.budget3d.feature.printer.domain.usecase.DeletePrinterUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrinterViewModel @Inject constructor(
    private val printerRepository: PrinterRepository,
    private val addPrinterUseCase: AddPrinterUseCase,
    private val deletePrinterUseCase: DeletePrinterUseCase
) : ViewModel() {

    val uiState: StateFlow<PrinterUiState> = printerRepository.getAllPrinters()
        .map { printers ->
            PrinterUiState(printers = printers, isLoading = false)
        }
        .catch { excecao ->
            emit(PrinterUiState(isLoading = false, error = excecao.message))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PrinterUiState(isLoading = true)
        )

    fun addPrinter(name: String, cost: Double, lifespan: Double, power: Double) {
        viewModelScope.launch {
            try {
                addPrinterUseCase(name, cost, lifespan, power)
            } catch (e: Exception) {
                // TODO: Tratar erro de validação UI
            }
        }
    }
    fun deletePrinter(printer: Printer){
        viewModelScope.launch{
            deletePrinterUseCase(printer)
        }
    }
}