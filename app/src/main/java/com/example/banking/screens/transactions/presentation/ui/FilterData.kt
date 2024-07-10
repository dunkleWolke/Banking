package com.example.banking.screens.transactions.presentation.ui

import android.widget.CalendarView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.banking.ui.theme.Blue
import com.example.banking.ui.theme.White
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Preview
@Composable
fun PreviewFilter() {
    FilterByDateBottomSheet(onSubmit = { })
}

@Composable
fun FilterByDateBottomSheet(onSubmit: () -> Unit) {
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }
    var startDateError by remember { mutableStateOf(false) }
    var endDateError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Filter by Date",
            color = White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp, bottom = 16.dp)
        )

        Text("Start Date", color = White, fontSize = 16.sp)
        OutlinedTextField(
            value = startDate,
            onValueChange = {
                startDate = it
                startDateError = startDate.isEmpty()
                if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
                    val startCal = Calendar.getInstance()
                    val endCal = Calendar.getInstance()

                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    startCal.time = sdf.parse(startDate) ?: Date()
                    endCal.time = sdf.parse(endDate) ?: Date()

                    if (startCal.after(endCal)) {
                        endDate = startDate
                    }
                }
            },
            label = { Text("Select start date") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showStartDatePicker = !showStartDatePicker
                    if (showEndDatePicker) showEndDatePicker = false
                },
            isError = startDateError
        )
        if (showStartDatePicker) {
            AndroidView(
                { CalendarView(it) },
                modifier = Modifier.wrapContentWidth(),
                update = { views ->
                    views.setOnDateChangeListener { _, year, month, dayOfMonth ->
                        val date = "$dayOfMonth/${month + 1}/$year"
                        startDate = date
                        showStartDatePicker = false
                        startDateError = startDate.isEmpty()
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("End Date", color = White, fontSize = 16.sp)
        OutlinedTextField(
            value = endDate,
            onValueChange = {
                endDate = it
                endDateError = endDate.isEmpty()
                if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
                    val startCal = Calendar.getInstance()
                    val endCal = Calendar.getInstance()

                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    startCal.time = sdf.parse(startDate) ?: Date()
                    endCal.time = sdf.parse(endDate) ?: Date()

                    if (endCal.before(startCal)) {
                        startDate = endDate
                    }
                }
            },
            label = { Text("Select end date") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showEndDatePicker = !showEndDatePicker
                    if (showStartDatePicker) showStartDatePicker = false
                },
            isError = endDateError
        )
        if (showEndDatePicker) {
            AndroidView(
                { CalendarView(it) },
                modifier = Modifier.wrapContentWidth(),
                update = { views ->
                    views.setOnDateChangeListener { _, year, month, dayOfMonth ->
                        val date = "$dayOfMonth/${month + 1}/$year"
                        endDate = date
                        showEndDatePicker = false
                        endDateError = endDate.isEmpty()
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                startDateError = startDate.isEmpty()
                endDateError = endDate.isEmpty()
                if (!startDateError && !endDateError) {
                    onSubmit()
                } },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Blue),
            shape = RoundedCornerShape(9.dp)
        ) {
            Text("Submit")
        }
    }
}

