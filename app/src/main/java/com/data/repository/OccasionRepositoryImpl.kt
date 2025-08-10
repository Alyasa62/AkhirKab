package com.data.repository

import com.data.local.OccasionDao
import com.data.local.OccasionEntity
import com.data.mapper.toDomainModel
import com.data.mapper.toEntity
import com.domain.model.Occasion
import com.domain.repository.OccasionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OccasionRepositoryImpl(
    private val occasionDao: OccasionDao
): OccasionRepository {

    override suspend fun upsertOccasion(occasion: Occasion) {
        occasionDao.upsertOccasion(occasion.toEntity())
    }

    override suspend fun deleteOccasion(occasionId: Int) {
        occasionDao.deleteOccasion(occasionId)
    }

    override fun getAllOccasions(): Flow<List<Occasion>> {
        return occasionDao.getAllOccasions().map { occasionEntities -> occasionEntities.map { it.toDomainModel() } }
    }

    override suspend fun getOccasionById(occasionId: Int): Occasion? {
        return occasionDao.getOccasionById(occasionId)?.toDomainModel()
    }
}