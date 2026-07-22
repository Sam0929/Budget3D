package com.example.budget3d.feature.material.di

import com.example.budget3d.feature.material.domain.repository.MaterialRepository
import com.example.budget3d.feature.material.data.repository.MaterialRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MaterialModule {

    @Binds
    @Singleton
    abstract fun bindMaterialRepository(
        materialRepositoryImpl: MaterialRepositoryImpl
    ): MaterialRepository
}