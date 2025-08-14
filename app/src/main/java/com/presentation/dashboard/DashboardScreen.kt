package com.presentation.dashboard

import android.R
import android.text.Layout
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.domain.model.Occasion
import com.presentation.component.StylizedAgeText
import com.presentation.theme.spacing

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier
) {

    val dummyOccasion = List(20) {
        Occasion(
            id = 1,
            title = "Occasion $it",
            emoji = "🎉",
            dateMillis = 0L,
            endDateMillis = 0L,
        )
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        DashboardTopBar (
            modifier = Modifier,
            onAddIconClick = { /* Handle add icon click */ }
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 400.dp),
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
        ) {
            items(dummyOccasion) { occasions ->
                OccasionCard(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    occasion = occasions,
                    onCalendarIconClick = { /* Handle calendar icon click */ },
                    onClick = { /* Handle occasion card click */ }
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
    Card(
        modifier = modifier.clickable { onClick() },
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.small),
        ) {
            Text(
                text = occasion.emoji,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            )
            Column {
                Text(
                    text = occasion.title,
                    style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = MaterialTheme.spacing.small)
                )

                Text(
                    text = occasion.dateMillis?.toFormattedDateString() ?: "No Date",
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
                .fillMaxSize()
                .padding(MaterialTheme.spacing.small),
            years = 23,
            months = 5,
            days = 12,
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
    DashboardScreen(
        modifier = Modifier.fillMaxSize()
    )
    
}

fun Long.toFormattedDateString(): String {
    val sdf = java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault())
    return sdf.format(java.util.Date(this))
}
