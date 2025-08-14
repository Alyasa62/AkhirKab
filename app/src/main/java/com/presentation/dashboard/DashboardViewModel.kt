package com.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domain.model.Occasion
import com.domain.repository.OccasionRepository
import com.presentation.calculator.CalcularUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val repository: OccasionRepository
): ViewModel(
) {


    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = combine(
        _uiState,
        repository.getAllOccasions()
    ) {
        state, occasions ->
        state.copy(
            occasions = occasions
        )

    } .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = DashboardUiState()
    )

    fun onAction(
        action: DashboardAction
    ) {
        when(action) {
           is DashboardAction.ShowDatePicker -> {
                _uiState.value = _uiState.value.copy(
                    isDatePickerDialogOpen = true,
                    selectedOccasion = action.occasion
                )
            }
            DashboardAction.DismissDatePicker -> {
                _uiState.value = _uiState.value.copy(
                    isDatePickerDialogOpen = false
                )
            }
            DashboardAction.NavigateBack -> {
                _uiState.value = _uiState.value.copy(
                    isDatePickerDialogOpen = false
                )
            }
            is DashboardAction.DateSelected -> {
                updateOccasions(action.date)
            }
        }

    }

    private fun updateOccasions(dateMillis: Long?) {
        viewModelScope.launch {
            uiState.value.selectedOccasion?.let {
              val updatedOccasion =  it.copy(dateMillis = dateMillis)
                repository.upsertOccasion(updatedOccasion)
            }

        }

    }

}