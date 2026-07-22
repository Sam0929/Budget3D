package com.example.budget3d.feature.pricing.domain.model

// Representa o recibo detalhado gerado após o cálculo
data class PrintQuote(
    val materialCost: Double,
    val depreciationCost: Double,
    val energyCost: Double,
    val additionalCosts: Double,
    val subtotal: Double,
    val expectedProfit: Double,
    val platformTaxes: Double,
    val finalPrice: Double
)