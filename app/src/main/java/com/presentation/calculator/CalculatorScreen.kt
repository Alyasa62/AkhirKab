package com.presentation.calculator

import android.R
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentDataType.Companion.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.akhirkab.ui.theme.AkhirKabTheme
import com.example.akhirkab.ui.theme.CustomBlue
import com.example.akhirkab.ui.theme.CustomPink
import com.presentation.component.CustomDatePickerDialog
import com.presentation.component.EmojiPickerDialog
import com.presentation.component.StatisticsCard
import com.presentation.component.StylizedAgeText
import kotlinx.coroutines.flow.Flow

@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier,
    state: CalcularUiState,
    onAction: (CalculatorAction) -> Unit

){

    EmojiPickerDialog(
        isOpen = state.isEmojiPickerDialogOpen,
        onEmojiSelected = {selectedEmoji ->
            onAction(CalculatorAction.EmojiSelected(selectedEmoji))
        },
        onDismissRequest = { onAction(CalculatorAction.DismissEmojiPicker) }
    )


    CustomDatePickerDialog(
        isOpen = state.isDatePickerDialogOpen,
        onDismissRequest = { onAction(CalculatorAction.DismissDatePicker) },
        onConfirmButtonClick = { selectedDateMillis ->
            onAction(CalculatorAction.DateSelected(selectedDateMillis))
        }

    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CalculatorTopBar(
            isDeleteIconVisible = true,
            onBackClick = {  },
            onDeleteClick = { },
            onSaveClick = {  onAction(CalculatorAction.SaveOccasion) }
        )
        FlowRow(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(8.dp),


            ) {
            HeaderSection(

                modifier = Modifier
                    .widthIn(max = 400.dp)
                    .padding(8.dp),
                state = state,
                onAction = onAction
            )
            StatisticsSection(
                modifier = Modifier
                    .widthIn(max = 400.dp)
                    .padding(8.dp),
                state = state

            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalculatorTopBar(
    modifier: Modifier = Modifier,
    isDeleteIconVisible: Boolean,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onSaveClick: () -> Unit

) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Navigate Back"
                )
            }

        },
        title = { Text(text = "Calculator") },
        actions = {
            if (isDeleteIconVisible) {
                IconButton(
                    onClick = onDeleteClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }

            IconButton(
                onClick = onSaveClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_menu_save) ,
                    contentDescription = "Safe"
                )

            }

        },

    )

}

@Composable
private fun HeaderSection (

    state: CalcularUiState,
    onAction: (CalculatorAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            CustomBlue, CustomPink
                        )
                    ),
                    shape = CircleShape
                )
                .clickable {
                    onAction(CalculatorAction.ShowEmojiPicker)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = state.emoji,
                style = MaterialTheme.typography.displayLarge,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = state.title,
            onValueChange = { newTitle ->
                onAction(CalculatorAction.SetTitle(newTitle))
            },
            label = { Text(text = "Title") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        DateSection(
            title = "Start Date",
            date = "${state.fromDateMillis?.let { it -> java.text.SimpleDateFormat("dd MMMM yyyy").format(it) } ?: "Select Date"} ",
            onDateIconClick = { onAction(CalculatorAction.ShowDatePicker(DateField.FROM)) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        DateSection(
            title = "End Date",
            date = "${state.toDateMillis?.let { it -> java.text.SimpleDateFormat("dd MMMM yyyy").format(it) } ?: "Select Date"}",
            onDateIconClick = { onAction(CalculatorAction.ShowDatePicker(DateField.TO)) }
        )
    }
    
}

@Composable
private fun StatisticsSection(
    modifier: Modifier = Modifier,
    state: CalcularUiState = CalcularUiState()
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
    ) {

            StylizedAgeText(
                years = state.period.years,
                months = state.period.months,
                days = state.period.days,
            )
            StatisticsCard(
                ageStats = state.ageStats
                )

    }


}

@Composable
private fun DateSection(
    modifier: Modifier = Modifier,
    title: String ,
    date: String,
    onDateIconClick: () -> Unit,
) {
    Row(modifier = modifier){
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = date,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(8.dp)
        )
        IconButton(
            onClick = onDateIconClick,
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Calendar"
            )

        }


    }
}

@PreviewScreenSizes
@Composable
private fun PreviewCalculatorScreen() {
    AkhirKabTheme {
        CalculatorScreen(
            state = CalcularUiState(),
            onAction = {}
        )
    }
    
}