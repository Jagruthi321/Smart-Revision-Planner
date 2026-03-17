package com.example.smartrevisionplanner.ui.plansetup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ExamDateScreen(
    examDateMillis: Long?,
    studyHoursPerDay: Float?,
    onExamDateChange: (Long) -> Unit,
    onStudyHoursChange: (Float?) -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var studyHoursText by remember(studyHoursPerDay) {
        mutableStateOf(studyHoursPerDay?.toString() ?: "")
    }

    Column(modifier = modifier.padding(24.dp)) {
        Text("Step 1: Exam Date", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { showDatePicker = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (examDateMillis != null) {
                    SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(Date(examDateMillis))
                } else {
                    "Select Exam Date"
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Study hours per day (optional)", style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = studyHoursText,
            onValueChange = {
                studyHoursText = it
                onStudyHoursChange(it.toFloatOrNull())
            },
            label = { Text("Hours") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth(),
            enabled = examDateMillis != null
        ) {
            Text("Next")
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = examDateMillis ?: System.currentTimeMillis()
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { onExamDateChange(it) }
                        showDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
