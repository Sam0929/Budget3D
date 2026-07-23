package com.example.budget3d.feature.material.presentation

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.example.budget3d.feature.material.domain.model.Material
import com.example.budget3d.ui.theme.Budget3DTheme
import com.example.budget3d.R

@Composable
fun MaterialScreen(
    viewModel: MaterialViewModel = hiltViewModel(
        checkNotNull<ViewModelStoreOwner>(
            LocalViewModelStoreOwner.current
        ) {
            stringResource(R.string.view_model_store_owner_missing)
        }, null
    )
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    MaterialScreenContent(
        state = state,
        onAddMaterial = { name, price, weight ->
            viewModel.addMaterial(name, price, weight)
        },
        onDeleteMaterial = {
            viewModel.deleteMaterial(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialScreenContent(
    state: MaterialUiState,
    onAddMaterial: (String, Double, Double) -> Unit,
    onDeleteMaterial: (Material) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.my_materials)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_material))
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                state.error != null -> {
                    Text(
                        text = stringResource(
                            R.string.error,
                            state.error
                        ),
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.materials.isEmpty() -> {
                    Text(
                        text = stringResource(R.string.missing_material),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.materials, key = { it.id }) { material ->
                            MaterialItem(material = material, onDeleteMaterial)
                        }
                    }
                }
            }
        }

        if (showAddDialog) {
            AddMaterialDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { name, price, weight ->
                    onAddMaterial(name, price, weight)
                    showAddDialog = false
                }
            )
        }
    }
}

@Composable
fun MaterialItem(
    material: Material,
    onDelete: (Material) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Text(
                    text = material.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(
                        R.string.price,
                        material.pricePerUnit,
                        material.weightInGrams
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(
                        R.string.cost_per_gram,
                        material.costPerGram
                    ),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary
                )

            }
            IconButton(
                onClick = {
                    onDelete(material)
                },
                modifier = Modifier
                    .padding(end = 8.dp)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete)
                )
            }
        }
    }
}

@Preview
@Composable
fun MaterialItemPreview() {

    val materialTest = Material(
        id = "1",
        name = "PLA Branco",
        pricePerUnit = 120.0,
        weightInGrams = 1000.0
    )
    Budget3DTheme {
        MaterialItem(materialTest, { materialTest })
    }
}

@Composable
fun AddMaterialDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, price: Double, weight: Double) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.new_material)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = stringResource(R.string.label_material_name)) },
                    singleLine = true
                )
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text(text = stringResource(R.string.label_material_price)) },
                    singleLine = true
                )
                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text(text = stringResource(R.string.label_material_weight)) },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val priceParsed = price.replace(",", ".").toDoubleOrNull() ?: 0.0
                    val weightParsed = weight.replace(",", ".").toDoubleOrNull() ?: 0.0
                    onConfirm(name, priceParsed, weightParsed)
                }
            ) {
                Text(text = stringResource(R.string.save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(text = stringResource(R.string.cancel)) }
        }
    )
}