package com.example.budget3d.core.di

import com.example.budget3d.core.domain.util.IdGenerator
import com.example.budget3d.core.util.JvmIdGenerator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreModule {

    @Binds
    abstract fun bindIdGenerator(
        jvmIdGenerator: JvmIdGenerator
    ): IdGenerator
}