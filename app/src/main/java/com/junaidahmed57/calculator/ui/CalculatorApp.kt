package com.junaidahmed57.calculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.junaidahmed57.calculator.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.junaidahmed57.calculator.data.DataSource.calculatorData
import com.junaidahmed57.calculator.ui.theme.CalculatorTheme
import kotlinx.coroutines.coroutineScope

@Composable
fun CalculatorApp(viewModel: CalculatorViewModel = viewModel()){

    val uiState = viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        HistoryContainer(data = uiState.value.allData, modifier = Modifier.weight(1f))

        Column {
            InputField(uiState.value.input)
            if (uiState.value.result.isNotEmpty() && uiState.value.result != "0" )
                ResultField(result = uiState.value.result)
            calculatorData.forEach{
                CalculatorRow(
                    data = it,
                    modifier = Modifier.padding(top = 12.dp),
                    onButtonClicked = { input ->
                        when(input){
                            in "0".."9","." -> viewModel.onNumberInput(input)
                            in "+","-","x","÷","%" -> viewModel.onSymbolInput(input)
                            "C" -> viewModel.onClearClicked()
                            "Del" -> viewModel.onDeleteClicked()
                            "=" -> viewModel.onEqualClicked()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun HistoryContainer(modifier: Modifier = Modifier, data: List<String> = emptyList()){

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End,
        reverseLayout = true
    ){
        items(data.reversed()){ value ->
            Text(text = value, style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Composable
fun InputField(input: String){
    BasicTextField(
        value = input,
        onValueChange = {},
        maxLines = 2,
        enabled = false,
        textStyle = MaterialTheme.typography.displaySmall,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
    )
}

@Composable
fun ResultField(result: String){
    BasicTextField(
        value = result,
        onValueChange = {},
        singleLine = true,
        enabled = false,
        textStyle = MaterialTheme.typography.titleLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
    )
}

@Composable
fun CalculatorRow(data: List<String>, modifier: Modifier = Modifier, onButtonClicked: (String) -> Unit){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        data.forEach{value ->
            RoundButton(
                backgroundColor = getBackgroundColor(value),
                contentColor = getContentColor(value),
                value = value,
                fontSize = 32,
                onButtonClicked = onButtonClicked,
                width = if (value == "0") 176 else 78
            )
        }
    }
}

fun getSize(value: String) = when(value){
    in "Del","C" -> 20
    "-" -> 35
    else -> 32 }

fun getBackgroundColor(value: String) = when(value){
    in "0".."9" -> R.color.grey
    "." -> R.color.grey
    in "C","Del","%" -> R.color.silver
    else -> R.color.yellow
}

fun getContentColor(value: String) = when(value){
    in "C","Del","%" -> R.color.black
    else -> R.color.white
}

@Preview
@Composable
fun CalculatorPreview(){
    CalculatorTheme {
        CalculatorApp()
    }
}