package com.presentation.dashboard

import com.domain.model.Occasion

sealed interface DashboardAction {
    data class ShowDatePicker(val occasion: Occasion) : DashboardAction
    data object DismissDatePicker : DashboardAction
    object NavigateBack : DashboardAction
    data class DateSelected(val date: Long?) : DashboardAction
}