package com.presentation.dashboard

import com.domain.model.Occasion

data class DashboardUiState(
    val occasions: List<Occasion> = emptyList(),
    val isDatePickerDialogOpen: Boolean = false,
    val selectedOccasion: Occasion? = null
)
