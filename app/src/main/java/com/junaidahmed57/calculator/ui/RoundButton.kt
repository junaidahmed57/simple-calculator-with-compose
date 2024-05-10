package com.junaidahmed57.calculator.ui

import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.junaidahmed57.calculator.R
import com.junaidahmed57.calculator.ui.theme.CalculatorTheme

@Composable
fun RoundButton(
    modifier: Modifier = Modifier,
    value: String = "1",
    onButtonClicked: (String) -> Unit = {},
    @ColorRes
    backgroundColor: Int,
    @ColorRes
    contentColor: Int,
    fontSize: Int = 32,
    width: Int = 76){
    Button(
        onClick = { onButtonClicked(value) },
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = backgroundColor),
            contentColor = colorResource(id = contentColor)
        ),
        modifier = modifier
            .width(width.dp)
            .height(76.dp)
            .background(Color.Transparent)
            .clip(RoundedCornerShape(16.dp))
    ) {
        when(value){
            "Del" ->  Icon(painter = painterResource(id = R.drawable.baseline_backspace_24), contentDescription = "BackSpace")
            "0" -> Text(
                        text = value,
                        fontSize = fontSize.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
            else -> Text(
                        text = value,
                        fontSize = fontSize.sp
                    )
        }
    }
}

@Preview
@Composable
fun YellowButtonPreview(){
    CalculatorTheme {
        RoundButton(
            backgroundColor = R.color.yellow,
            contentColor = R.color.white
        )
    }
}

@Preview
@Composable
fun GreyButtonPreview(){
    CalculatorTheme {
        RoundButton(
            backgroundColor = R.color.grey,
            contentColor = R.color.white
        )
    }
}

@Preview
@Composable
fun SilverButtonPreview(){
    CalculatorTheme {
        RoundButton(
            backgroundColor = R.color.silver,
            contentColor = R.color.black
        )
    }
}