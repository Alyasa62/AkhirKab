package com.domain.model

import androidx.room.PrimaryKey

data class Occasion(
    val id: Int?,
    val title: String,
    val dateMillis: Long?,
    val endDateMillis: Long?,
    val emoji: String
)
