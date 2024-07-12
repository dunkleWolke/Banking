package com.example.banking.screens.allTransactions.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarViewModel : ViewModel() {
    var startDate by mutableStateOf("")
    var endDate by mutableStateOf("")
    var showStartDatePicker by mutableStateOf(false)
    var showEndDatePicker by mutableStateOf(false)
    var startDateError by mutableStateOf(false)
    var endDateError by mutableStateOf(false)
    var dateRangeError by mutableStateOf(false)

    fun onStartDateChanged(newDate: String) {
        startDate = newDate
        validateDates()
    }

    fun onEndDateChanged(newDate: String) {
        endDate = newDate
        validateDates()
    }

    fun validateAndSubmit(onSubmit: (startDate: String, endDate: String) -> Unit) {
        startDateError = startDate.isEmpty()
        endDateError = endDate.isEmpty()
        dateRangeError = !isDateRangeValid()

        if (!startDateError && !endDateError && isDateRangeValid()) {
            onSubmit(startDate, endDate)
        }
    }

    private fun isDateRangeValid(): Boolean {
        if (startDate.isEmpty() || endDate.isEmpty()) return false

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val startCal = Calendar.getInstance().apply { time = sdf.parse(startDate) ?: Date() }
        val endCal = Calendar.getInstance().apply { time = sdf.parse(endDate) ?: Date() }

        return !startCal.after(endCal)
    }

    fun reset() {
        showStartDatePicker = false
        showEndDatePicker = false
        startDateError = false
        endDateError = false
        dateRangeError = false
    }

    private fun validateDates() {
        dateRangeError = !isDateRangeValid()
    }
}