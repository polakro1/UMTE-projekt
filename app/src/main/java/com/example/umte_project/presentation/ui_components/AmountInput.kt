package com.example.umte_project.presentation.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AmountInput(
    amountText: String, onAmountChange: (String) -> Unit, modifier: Modifier = Modifier
) {
    var internalState by remember {
        mutableStateOf(
            TextFieldValue(
                text = amountText, selection = TextRange(amountText.length)
            )
        )
    }


    BasicTextField(
        value = internalState, onValueChange = {
            val sanitized = sanitizeAmountInput(it.text)

            internalState = TextFieldValue(
                text = sanitized, selection = TextRange(sanitized.length)
            )

            onAmountChange(sanitized)
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        cursorBrush = SolidColor(Color.Transparent),
        textStyle = TextStyle(
            fontSize = 70.sp,
            color = MaterialTheme.colorScheme.error,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End
        ),
        decorationBox = { innerTextField ->
            Box(
                modifier = modifier
                    .background(MaterialTheme.colorScheme.surfaceDim)
                    .padding(15.dp, 4.dp)
            ) {
                innerTextField()
            }
        }, modifier = modifier
    )
}

fun sanitizeAmountInput(input: String): String {
    var text = input.replace(',', '.') // podporuj čárku jako tečku

    // Odeber nepovolené znaky – jen číslice a tečka
    text = text.filterIndexed { index, c ->
        c.isDigit() || (c == '.' && text.indexOf('.') == index)
    }

    // Zamez prázdnotě – nahraď "" za "0"
    if (text.isBlank()) return "0"

    // Pokud začíná tečkou, doplň nulu před
    if (text.startsWith(".")) {
        text = "0$text"
    }

    // Pokud má víc než jednu tečku – smaž zbytek (první povolena výše)
    val firstDot = text.indexOf('.')
    if (firstDot != -1) {
        val beforeDot = text.substring(0, firstDot + 1)
        val afterDot = text.substring(firstDot + 1).replace(".", "")
        text = beforeDot + afterDot
    }

    // Odeber vedoucí nuly (např. 0004 → 4), ale ponech 0. nebo 0
    if (text.length > 1 && text.startsWith("0") && !text.startsWith("0.")) {
        text = text.trimStart('0')
        if (text.isEmpty()) text = "0"
    }

    if (!text.startsWith("-") || text.startsWith("0")) {
        text = "-${text}"
    }

    return text
}