package com.example.banking.ui.theme.TransactionScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.banking.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Field(label: String, value: String, enabled: Boolean) {

    Text(
        text = label,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = White,
        modifier = Modifier.padding(top = 8.dp)
    )

    OutlinedTextField(
        value = value,
        onValueChange = {},
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(9.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = White,
            unfocusedBorderColor = White,
            cursorColor = White,
            disabledTextColor = White,
            disabledBorderColor = White
        )
    )
}