package com.junaidahmed57.calculator.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.function.BinaryOperator

private val symbols = listOf("+","-","x","%","÷")

enum class Arithmetics: BinaryOperator<Float> {
    PLUS {
        override fun apply(t: Float, u: Float): Float {
            return t + u
        }
    },
    MINUS {
        override fun apply(t: Float, u: Float): Float {
            return t - u
        }
    },
    MULTIPLY {
        override fun apply(t: Float, u: Float): Float {
            return t * u
        }
    },
    MODULUS {
        override fun apply(t: Float, u: Float): Float {
            return t % u
        }
    },
    DIVIDE {
        override fun apply(t: Float, u: Float): Float {
            return t / u
        }
    }
}

class CalculatorViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState = _uiState.asStateFlow()

    fun onNumberInput(value: String){
        val result = calculateResult(uiState.value.input.plus(value))
        _uiState.update {
            uiState.value.copy(
                input = it.input.plus(value),
                result = "= ".plus(result)
            )
        }
    }

    private fun calculateResult(expressionToSolve: String): String {
        if (expressionToSolve.any { it.toString() in symbols }) {
            val list = expressionToSolve.split(symbols[0], symbols[1], symbols[2], symbols[3], symbols[4])
//            Log.d("list", list.toString())
            var result = list[0].toFloat()
            if (list.size>1){
                for (i in 1..<list.size){
                    if (list[i].isNotEmpty())
                        result = evaluateExpression(result, list[i].toFloat(), uiState.value.symbols[i-1])
                }
                return result.toString()
            }
          return result.toString()
        }
        return expressionToSolve
    }

    private fun evaluateExpression(val1: Float, val2: Float, symbol: String): Float {
        return when(symbol){
            symbols[0] -> Arithmetics.PLUS.apply(val1, val2)
            symbols[1] -> Arithmetics.MINUS.apply(val1, val2)
            symbols[2] -> Arithmetics.MULTIPLY.apply(val1, val2)
            symbols[3] -> Arithmetics.MODULUS.apply(val1, val2)
            symbols[4] -> Arithmetics.DIVIDE.apply(val1, val2)
            else -> val1
        }
    }

    fun onSymbolInput(input: String){
        if (uiState.value.input.isEmpty()) return

        val expression = uiState.value.input
        val listOfOperators = uiState.value.symbols.toMutableList()

        if (expression.last().isDigit()) {
            listOfOperators.add(input)

            _uiState.update {
                uiState.value.copy(
                    input = expression.plus(input),
                    symbols = listOfOperators
                )
            }
        }
        else {
            listOfOperators.removeLast()
            listOfOperators.add(input)

            _uiState.update {
                uiState.value.copy(
                    input = expression.replace(expression.last().toString(),input)
                )
            }
        }
    }

    fun onEqualClicked(){

        _uiState.update {
            uiState.value.copy(
                input = if (it.result.isNotEmpty()) it.result.replace("= ","") else it.input,
                result = "",
                symbols = emptyList(),
                allData = getUpdatedList(it.allData, it.input, it.result)
            )
        }
    }

    fun onClearClicked(){
        _uiState.update {
            uiState.value.copy(
                input = "",
                result = "",
                symbols = emptyList()
            )
        }
    }

    fun onDeleteClicked(){
        if (uiState.value.input.isEmpty()) return
        val expressionToSolve = uiState.value.input.substring(0,uiState.value.input.length-1)
        val result = calculateResult(expressionToSolve)
        _uiState.update {
            uiState.value.copy(
                input = expressionToSolve,
                result = "= ".plus(result)
            )
        }
    }

    private fun getUpdatedList(list: List<String>, input: String, result: String): List<String> {
        return if (result.isEmpty() or (result.replace("= ","") == input))
            list
        else
            list + listOf(input, result)
    }

}
