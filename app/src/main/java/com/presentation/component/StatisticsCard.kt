package com.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StatisticsCard(
    modifier: Modifier = Modifier,
    ageStats: AgeStats
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column (
            modifier = Modifier.fillMaxWidth()
            .padding(16.dp)

        ) {
            Text(
                text = "Age Statistics",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp),
                textDecoration = TextDecoration.Underline
            )
            TotalTimeRow(
                label = "Total Years: ",
                value = ageStats.years,
                modifier = Modifier.padding(8.dp)
            )
            TotalTimeRow(
                label = "Total Months: ",
                value = ageStats.months,
                modifier = Modifier.padding(8.dp)
            )
            TotalTimeRow(
                label = "Total Weeks: ",
                value = ageStats.weeks,
                modifier = Modifier.padding(8.dp)
            )
            TotalTimeRow(
                label = "Total Days: ",
                value = ageStats.days,
                modifier = Modifier.padding(8.dp)
            )
            TotalTimeRow(
                label = "Total Hours: ",
                value = ageStats.hours,
                modifier = Modifier.padding(8.dp)
            )
            TotalTimeRow(
                label = "Total Minutes: ",
                value = ageStats.minutes,
                modifier = Modifier.padding(8.dp)
            )
            TotalTimeRow(
                label = "Total Seconds: ",
                value = ageStats.seconds,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
private fun TotalTimeRow(
    modifier: Modifier = Modifier,
    label: String,
    value: Int
) {

    Row(modifier = modifier) {
        Text(
            modifier = Modifier.weight(1f),
            text = label,
            textAlign = TextAlign.End

            )
        Text(
            modifier = Modifier.weight(1f),
            text = value.toString(),
            fontWeight = FontWeight.Bold,


        )
    }
}

@Preview
@Composable
private fun PreviewStatisticsCard() {
    StatisticsCard(
        ageStats = AgeStats(
            years = 5,
            months = 3,
            weeks = 2,
            days = 15,
            hours = 120,
            minutes = 3000,
            seconds = 180000
        )    )
    
}

data class AgeStats(
    val years: Int = 0,
    val months: Int = 0,
    val weeks: Int = 0,
    val days: Int = 0,
    val hours: Int = 0,
    val minutes: Int = 0,
    val seconds: Int = 0,
)