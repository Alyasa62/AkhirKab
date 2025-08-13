package com.presentation.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.presentation.theme.spacing

@Composable
fun EmojiPickerDialog(
    isOpen: Boolean,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onEmojiSelected: (String) -> Unit,
) {
     if (isOpen) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(text = "Select Emoji") },
            text = {
                EmojiGrid(
                    onEmojiSelected = onEmojiSelected,
                )
            },
            confirmButton = {},
            dismissButton = {},
            modifier = modifier
        )
    }
}

@Composable
private fun EmojiGrid(
    onEmojiSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val emojis = listOf(
        // Meetings & Work
        "🗓️", "📌", "💼", "📈", "💻", "📞", "🏢", "💡", "\uD83C\uDF93",
        // Celebrations & Personal
        "🎉", "🎂", "🥳", "🎁", "🎈", "💍", "❤️", "👨‍👩‍👧‍👦",
        // Appointments & Health
        "🩺", "🏥", "🦷", "💊", "🏋️‍♀️", "💪", "✂️", "🏃‍♂️", "🧘‍♀️",
        // Travel & Food
        "✈️", "🚗", "🏨", "🗺️", "📍", "☕", "🍽️", "🍔",
        // Leisure & Hobbies
        "🎬", "🎤", "🎶", "⚽️", "🎮", "📚", "🎨", "🛍️", "🏞️", "🍿"
    )
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(5),
        contentPadding = PaddingValues(MaterialTheme.spacing.small)
    ) {
        items(emojis) { emoji ->
            IconButton(
                onClick = { onEmojiSelected(emoji) },
                modifier = Modifier.padding(MaterialTheme.spacing.extraSmall)
            ) {
                Text(
                    text = emoji,
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewEmojiPickerDialog() {
    EmojiPickerDialog(
        isOpen = true,
        onEmojiSelected = {},
        onDismissRequest = {}
    )
}