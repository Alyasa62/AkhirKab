package com.presentation.calculator

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.data.local.OccasionDao
import com.data.local.OccasionDatabase
import com.data.local.OccasionEntity
import com.domain.model.Occasion
import com.domain.repository.OccasionRepository
import com.presentation.navigation.Route
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil
import kotlinx.datetime.until

class CalculatorViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: OccasionRepository,
) : ViewModel() {


    val occasionId = savedStateHandle.toRoute<Route.CalculatorScreen>().id
    private val _uiState = MutableStateFlow(CalcularUiState())
    val uiState: StateFlow<CalcularUiState> = _uiState.asStateFlow()

    private val _event = Channel<CalculatorEvent>()
    val event = _event.receiveAsFlow()

    init {
        getOccasion()
    }

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
            CalculatorAction.DeleteOccasion -> {
                deleteOccasion()
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
            CalculatorAction.SaveOccasion -> {
                saveOccasion()
            }
        }
    }

    private fun saveOccasion() {
        viewModelScope.launch {
            val occasion = Occasion(
                id = occasionId,
                title = _uiState.value.title,
                dateMillis = _uiState.value.fromDateMillis,
                endDateMillis = _uiState.value.toDateMillis,
                emoji = _uiState.value.emoji
            )
            repository.upsertOccasion(occasion)
            _event.send(CalculatorEvent.ShowToast("Saved successfully!"))
            _event.send(CalculatorEvent.NavigateToDashboardScreen)

        }
    }

    private fun getOccasion() {
        viewModelScope.launch {
            if (occasionId == null) return@launch
            repository.getOccasionById( occasionId)?.let {
                _uiState.update { currentState ->
                    currentState.copy(
                        title = it.title,
                        emoji = it.emoji,
                        fromDateMillis = it.dateMillis,
                        toDateMillis = it.endDateMillis,
                        occasionId = it.id
                    )
                }
            }
            calculateStats()
        }
    }



    private fun deleteOccasion() {
        viewModelScope.launch {
            if (occasionId == null) return@launch
            repository.deleteOccasion(occasionId)
            _event.send(CalculatorEvent.ShowToast("Deleted successfully!"))
            _event.send(CalculatorEvent.NavigateToDashboardScreen)
        }
    }
    private fun calculateStats() {
        val timeZone = TimeZone.currentSystemDefault()
        val fromMillis = _uiState.value.fromDateMillis
        val toMillis = _uiState.value.toDateMillis

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