package com.example.banking.screens.allTransactions.presentation.ui

import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.banking.R
import com.example.banking.screens.allTransactions.presentation.viewmodel.AllTransactionViewModel
import com.example.banking.screens.allTransactions.presentation.viewmodel.CalendarViewModel
import com.example.banking.ui.theme.Blue
import com.example.banking.ui.theme.Gray
import com.example.banking.ui.theme.Red
import com.example.banking.ui.theme.White
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Preview
@Composable
fun PreviewFilter() {
}

@Composable
fun FilterByDateBottomSheet(
    viewModel: CalendarViewModel,
    onSubmit: (startDate: String, endDate: String) -> Unit
) {

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
        Text(
            text = if (viewModel.startDate.isEmpty()) "Select start date" else viewModel.startDate,
            color = if (viewModel.startDateError) Red else White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { viewModel.showStartDatePicker = true }
                .background(Gray, shape = RoundedCornerShape(4.dp))
                .padding(16.dp)
        )

        if (viewModel.showStartDatePicker) {
            AndroidView(
                {  context ->
                    CalendarView(ContextThemeWrapper(context, R.style.CustomCalendarView))
                 },
                modifier = Modifier.wrapContentWidth(),
                update = { views ->
                    views.setOnDateChangeListener { _, year, month, dayOfMonth ->
                        viewModel.onStartDateChanged("${dayOfMonth}/${month + 1}/${year}")
                        viewModel.showStartDatePicker = false
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("End Date", color = White, fontSize = 16.sp)
        Text(
            text = if (viewModel.endDate.isEmpty()) "Select end date" else viewModel.endDate,
            color = if (viewModel.endDateError) Red else White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { viewModel.showEndDatePicker = true }
                .background(Gray, shape = RoundedCornerShape(4.dp))
                .padding(16.dp)
        )

        if (viewModel.showEndDatePicker) {
            AndroidView(
                {  context ->
                    CalendarView(ContextThemeWrapper(context, R.style.CustomCalendarView)) },
                modifier = Modifier.wrapContentWidth(),
                update = { views ->
                    views.setOnDateChangeListener { _, year, month, dayOfMonth ->
                        viewModel.onEndDateChanged("${dayOfMonth}/${month + 1}/${year}")
                        viewModel.showEndDatePicker = false
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.dateRangeError) {
            Text("Start date cannot be after end date", color = Red, fontSize = 12.sp)
        }

        Button(
            onClick = { viewModel.validateAndSubmit(onSubmit) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Blue),
            shape = RoundedCornerShape(9.dp)
        ) {
            Text("Submit")
        }
    }
}
