package com.strayalpaca.android.abox.di

import com.strayalpaca.android.data.repositoryImpl.AvsBTestRepository
import com.strayalpaca.android.data.repositoryImpl.HomeTestRepository
import com.strayalpaca.android.data.repositoryImpl.OxQuizTestRepository
import com.strayalpaca.android.domain.repository.AvsBRepository
import com.strayalpaca.android.domain.repository.HomeRepository
import com.strayalpaca.android.domain.repository.OxQuizRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DiModule {
    @Provides
    fun providersAvsBRepository() : AvsBRepository {
        return AvsBTestRepository()
    }
    @Provides
    fun providersHomeRepository() : HomeRepository {
        return HomeTestRepository()
    }
    @Provides
    fun providersOxQuizRepository() : OxQuizRepository {
        return OxQuizTestRepository()
    }
}