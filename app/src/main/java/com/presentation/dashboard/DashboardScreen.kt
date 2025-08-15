package com.presentation.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add

import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.domain.model.Occasion
import com.presentation.component.CustomDatePickerDialog
import com.presentation.component.StylizedAgeText
import com.presentation.theme.spacing
import com.presentation.util.periodUntil
import com.presentation.util.toFormattedDateString
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    navigateToCalculatorScreen: (Int?) -> Unit,
    state: DashboardUiState,
    onAction: (DashboardAction) -> Unit = {  }
) {

    CustomDatePickerDialog(
        isOpen = state.isDatePickerDialogOpen,
        onDismissRequest = { onAction(DashboardAction.DismissDatePicker) },onConfirmButtonClick = { selectedDateMillis ->
            onAction(DashboardAction.DateSelected(date = selectedDateMillis))
        }

    )


    Column(
        modifier = modifier.fillMaxSize()
    ) {
        DashboardTopBar (
            modifier = Modifier,
            onAddIconClick = { navigateToCalculatorScreen(null) }
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 400.dp),
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
        ) {
            items(state.occasions) { occasions ->
                OccasionCard(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    occasion = occasions,
                    onCalendarIconClick = { onAction(DashboardAction.ShowDatePicker(occasions)) },
                    onClick = { navigateToCalculatorScreen(occasions.id) }
                )


            }

        }

    }

    
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardTopBar(
    modifier: Modifier = Modifier,
    onAddIconClick: () -> Unit,

) {
    TopAppBar(
        windowInsets = WindowInsets(0),
        modifier = modifier,
        title = { Text(text = "Dashboard") },
        actions = {
            IconButton(
                onClick = onAddIconClick
            ) {
                    Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },

        )

}

@Composable
private fun OccasionCard(
    modifier: Modifier = Modifier,
    occasion: Occasion,
    onCalendarIconClick: () -> Unit,
    onClick: () -> Unit
) {
    val dateMillis = occasion.dateMillis
    Card(
        modifier = modifier.clickable { onClick() },
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start =  MaterialTheme.spacing.medium,
                    top = MaterialTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = occasion.emoji,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            )
            Spacer(modifier = Modifier.size(MaterialTheme.spacing.medium))
            Column {
                Text(
                    text = occasion.title,
                    style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = MaterialTheme.spacing.small)
                )

                Text(
                    text = dateMillis?.toFormattedDateString() ?: "No Date",


                )
            }
            Spacer( modifier = Modifier.weight(1f))
            IconButton(
                onClick = onCalendarIconClick
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Add"
                )
            }
            }
        StylizedAgeText(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .align(Alignment.CenterHorizontally),

            years = dateMillis?.periodUntil()?.years ?: 0,
            months = dateMillis?.periodUntil()?.months ?: 0,
            days = dateMillis?.periodUntil()?.days ?: 0,

        )
        FilledIconButton(
            modifier = Modifier.padding(MaterialTheme.spacing.small)
                .align(Alignment.End)
                .size(25.dp),
            onClick = onClick,
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

        ) {
            Icon(
                modifier = Modifier.padding(MaterialTheme.spacing.extraSmall),
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Show Details"
            )
        }
        }
    }



@Preview(showBackground = true)
@Composable
private fun PreviewDashboardScreen() {
    val dummyOccasion = List(20) {
        Occasion(
            id = 1,
            title = "Occasion $it",
            emoji = "🎉",
            dateMillis = 0L,
            endDateMillis = 0L,
        )
    }


    DashboardScreen(
        state = DashboardUiState(occasions = dummyOccasion),
        modifier = Modifier.fillMaxSize()
        , navigateToCalculatorScreen = {},
        onAction = {}

    )

}


