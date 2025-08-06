package com.presentation.calculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil
import kotlinx.datetime.until

class CalculatorViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CalcularUiState())
    val uiState: StateFlow<CalcularUiState> = _uiState.asStateFlow()

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

            is CalculatorAction.ShowDatePicker -> {
                _uiState.update {
                    it.copy(
                        isDatePickerDialogOpen = true,
                        activeDateField = action.dateField,
                    )
                }
            }

            CalculatorAction.DismissDatePicker -> {
                _uiState.update { it.copy(isDatePickerDialogOpen = false) }
            }

            is CalculatorAction.DateSelected -> {
                _uiState.update { currentState ->
                    when (currentState.activeDateField) {
                        DateField.FROM -> currentState.copy(fromDateMillis = action.millis, isDatePickerDialogOpen = false)
                        DateField.TO -> currentState.copy(toDateMillis = action.millis, isDatePickerDialogOpen = false)
                    }
                }
                calculateStats()
            }
            is CalculatorAction.SetTitle -> {
                _uiState.update { it.copy(title = action.title) }

            }
        }
    }

    private fun calculateStats() {
        val timeZone = TimeZone.currentSystemDefault()
        val fromMillis = _uiState.value.fromDateMillis
        val toMillis = _uiState.value.toDateMillis

        // Ensure we don't calculate if dates are not set
        if (fromMillis == null || toMillis == null) {
            return
        }

        val fromInstant = Instant.fromEpochMilliseconds(fromMillis)
        val toInstant = Instant.fromEpochMilliseconds(toMillis)

        val period = fromInstant.periodUntil(toInstant, timeZone = timeZone)
        val diffInMonths = fromInstant.until(toInstant, DateTimeUnit.MONTH, timeZone)
        val diffInWeeks = fromInstant.until(toInstant, DateTimeUnit.WEEK, timeZone)
        val diffInDays = fromInstant.until(toInstant, DateTimeUnit.DAY, timeZone)
        val diffInHours = fromInstant.until(toInstant, DateTimeUnit.HOUR, timeZone)
        val diffInMinutes = fromInstant.until(toInstant, DateTimeUnit.MINUTE, timeZone)
        val diffInSeconds = fromInstant.until(toInstant, DateTimeUnit.SECOND, timeZone)


        _uiState.update {
            it.copy(
                period = period,
                ageStats = AgeStats(
                    years = period.years,
                    months = diffInMonths.toInt(),
                    weeks = diffInWeeks.toInt(),
                    days =  diffInDays.toInt(),
                    hours = diffInHours.toInt(),
                    minutes = diffInMinutes.toInt(),
                    seconds = diffInSeconds.toInt()
                )
            )
        }
    }
}