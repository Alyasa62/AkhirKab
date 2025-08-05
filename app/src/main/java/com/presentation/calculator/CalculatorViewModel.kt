package com.presentation.calculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalculatorViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(CalcularUiState())
    val UiState: StateFlow<CalcularUiState> = _uiState.asStateFlow()

    fun onAction(action: CalculatorAction) {
        when (action) {
            CalculatorAction.ShowEmojiPicker -> {
                _uiState.update { it.copy(isEmojiPickerDialogOpen = true) }
            }

            CalculatorAction.DismissEmojiPicker -> {
                _uiState.update { it.copy(isEmojiPickerDialogOpen = false) }
            }

            is CalculatorAction.SetEmoji -> {
                _uiState.update { it.copy(emoji = action.emoji, isEmojiPickerDialogOpen = false) }
            }

            is CalculatorAction.EmojiSelected -> {
                _uiState.update { it.copy(emoji = action.emoji, isEmojiPickerDialogOpen = false) }
            }

            
            CalculatorAction.ShowDatePicker -> {
                _uiState.update { it.copy(isDatePickerDialogOpen = true) }
            }
            CalculatorAction.DismissDatePicker -> {
                _uiState.update { it.copy(isDatePickerDialogOpen = false) }
            }
            is CalculatorAction.DateSelected -> {
                _uiState.update { it.copy(dateMillis = action.millis, isDatePickerDialogOpen = false) }
            }




        }



    }

}