package com.presentation.calculator

sealed interface CalculatorAction {

    data class SetEmoji(val emoji: String) : CalculatorAction

    data object ShowEmojiPicker : CalculatorAction
    data object DismissEmojiPicker : CalculatorAction
    data class EmojiSelected(val emoji: String) : CalculatorAction


    data object ShowDatePicker : CalculatorAction
    data object DismissDatePicker : CalculatorAction
    data class DateSelected(val millis: Long?) : CalculatorAction

}