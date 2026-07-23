package com.example.budget3d.feature.printer.di

import com.example.budget3d.feature.printer.data.repository.PrinterRepositoryImpl
import com.example.budget3d.feature.printer.domain.repository.PrinterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PrinterModule {

    @Binds
    @Singleton
    abstract fun bindPrinterRepository(
        impl: PrinterRepositoryImpl
    ): PrinterRepository
}