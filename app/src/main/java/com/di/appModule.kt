package com.di

import com.data.local.DatabaseFactory
import com.data.local.OccasionDao
import com.data.local.OccasionDatabase
import com.data.repository.OccasionRepositoryImpl
import com.domain.repository.OccasionRepository
import com.presentation.calculator.CalculatorViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module{
    single<OccasionDatabase> {
        DatabaseFactory.create(get())
    }
    single<OccasionDao> { get<OccasionDatabase>().occasionDao() }

    singleOf(::OccasionRepositoryImpl).bind<OccasionRepository>()

    viewModelOf(::CalculatorViewModel)
}
