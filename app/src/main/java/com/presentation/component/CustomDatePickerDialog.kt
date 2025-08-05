package com.presentation.component

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    onDismissRequest: () -> Unit = {},
    onConfirmButtonClick: (Long?) -> Unit = {  }
) {
    val state = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )

    if(isOpen) {
        DatePickerDialog(
            modifier = modifier,
            onDismissRequest = onDismissRequest,

            confirmButton = {
                TextButton(
                    modifier = modifier,
                    onClick = {
                        onConfirmButtonClick(state.selectedDateMillis)
                        onDismissRequest()
                    }
                ) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    modifier = modifier,
                    onClick = { onDismissRequest}
                ) {
                    Text(text = "Cancel")
                }
            },
            content = {
                DatePicker(
                    state = state,
                    modifier = modifier

                )


            },

            )
    }


}