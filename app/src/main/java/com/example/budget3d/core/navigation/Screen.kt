package com.example.budget3d.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    data object Pricing : Screen("pricing", "Orçamento", Icons.Default.PlayArrow)
    data object Material : Screen("material", "Materiais", Icons.Default.Build)
    data object Printer : Screen("printer", "Máquinas", Icons.Default.Settings)
}