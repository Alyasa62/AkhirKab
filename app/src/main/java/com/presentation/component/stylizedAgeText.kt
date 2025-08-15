package com.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StylizedAgeText(
    modifier: Modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth(),

    years: Int,
    months: Int,
    days: Int
) {
    val numberStyle = SpanStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold
    )

    val wordStyle = SpanStyle(
        fontSize = 14.sp
    )

    val styledText: AnnotatedString = buildAnnotatedString {
        withStyle(style = numberStyle) {
            append("$years")
        }
        withStyle(style = wordStyle) {
            append(" Years ")
        }
        withStyle(style = numberStyle) {
            append("$months")
        }
        withStyle(style = wordStyle) {
            append(" Months ")
        }
        withStyle(style = numberStyle) {
            append("$days")
        }
        withStyle(style = wordStyle) {
            append(" Days")
        }
    }

    Text(
        text = styledText,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewStylizedAgeText() {
    StylizedAgeText(
        years = 5,
        months = 3,
        days = 15
    )
}