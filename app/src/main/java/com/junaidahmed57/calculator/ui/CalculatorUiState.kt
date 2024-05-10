package com.junaidahmed57.calculator.ui

data class CalculatorUiState(
    val input: String = "",
    val result: String = "",
    val symbols: List<String> = emptyList(),
    val allData: List<String> = emptyList()
)