package com.data.mapper

import com.data.local.OccasionEntity

fun OccasionEntity.toDomainModel(): com.domain.model.Occasion {
    return com.domain.model.Occasion(
        id = id,
        title = title,
        dateMillis = dateMillis,
        endDateMillis = endDateMillis,
        emoji = emoji
    )
}

fun com.domain.model.Occasion.toEntity(): OccasionEntity {
    return OccasionEntity(
        id = id ?: 0,
        title = title,
        dateMillis = dateMillis,
        endDateMillis = endDateMillis,
        emoji = emoji
    )
}