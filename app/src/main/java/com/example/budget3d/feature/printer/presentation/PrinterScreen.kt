package com.example.budget3d.feature.printer.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.example.budget3d.feature.material.domain.model.Material
import com.example.budget3d.feature.material.presentation.MaterialScreenContent
import com.example.budget3d.feature.material.presentation.MaterialUiState
import com.example.budget3d.feature.printer.domain.model.Printer

@Composable
fun PrinterScreen(
    viewModel: PrinterViewModel = hiltViewModel(
        checkNotNull<ViewModelStoreOwner>(
            LocalViewModelStoreOwner.current
        ) {
            "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
        }, null
    )
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    PrinterScreenContent(
        state = state,
        onAddPrinter = { name, cost, lifespan, power ->
            viewModel.addPrinter(name, cost, lifespan, power)
        },
        onDeletePrinter = {
            viewModel.deletePrinter(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrinterScreenContent(
    state: PrinterUiState,
    onAddPrinter: (String, Double, Double, Double) -> Unit,
    onDeletePrinter: (Printer) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Minhas Impressoras") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Impressora")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                state.error != null -> Text(
                    "Erro: ${state.error}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )

                state.printers.isEmpty() -> Text(
                    "Nenhuma impressora cadastrada.",
                    modifier = Modifier.align(Alignment.Center)
                )

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.printers, key = { it.id }) { printer ->
                            PrinterItem(printer, onDeletePrinter)
                        }
                    }
                }
            }
        }

        if (showAddDialog) {
            AddPrinterDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { name, cost, lifespan, power ->
                    onAddPrinter( name, cost, lifespan, power)
                    showAddDialog = false
                }
            )
        }
    }
}


@Composable
fun PrinterItem(
    printer: Printer,
    onDelete: (Printer) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Text(
                    text = printer.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Custo Máquina: R$ ${String.format("%.2f", printer.machineCost)}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Vida Útil: ${printer.lifespanHours.toInt()}h",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Consumo: ${printer.powerConsumptionWatts.toInt()}W",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Depreciação: R$ ${
                        String.format("%.4f", printer.depreciationPerHour)
                    } /h",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            IconButton(
                onClick = {
                    onDelete(printer)
                },
                modifier = Modifier
                    .padding(end = 8.dp)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Excluir"
                )
            }
        }
    }
}

@Composable
fun AddPrinterDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, cost: Double, lifespan: Double, power: Double) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var lifespan by remember { mutableStateOf("") }
    var power by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nova Impressora") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome (ex: Ender 3)") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = cost,
                    onValueChange = { cost = it },
                    label = { Text("Valor da máquina (R$)") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = lifespan,
                    onValueChange = { lifespan = it },
                    label = { Text("Vida útil (Horas)") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = power,
                    onValueChange = { power = it },
                    label = { Text("Consumo Médio (Watts)") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val costParsed = cost.replace(",", ".").toDoubleOrNull() ?: 0.0
                    val lifespanParsed = lifespan.replace(",", ".").toDoubleOrNull() ?: 0.0
                    val powerParsed = power.replace(",", ".").toDoubleOrNull() ?: 0.0
                    onConfirm(name, costParsed, lifespanParsed, powerParsed)
                }
            ) { Text("Salvar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}