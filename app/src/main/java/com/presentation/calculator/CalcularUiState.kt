package com.presentation.calculator

data class CalcularUiState(

    val emoji: String = "🎂",
    val isEmojiPickerDialogOpen: Boolean = false,
    val isDatePickerDialogOpen: Boolean = false,
    val dateMillis: Long? = null,
)
