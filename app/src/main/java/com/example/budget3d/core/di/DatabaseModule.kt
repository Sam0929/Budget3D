package com.example.budget3d.core.di

import android.app.Application
import androidx.room.Room
import com.example.budget3d.core.database.AppDatabase
import com.example.budget3d.feature.material.data.local.MaterialDao
import com.example.budget3d.feature.printer.data.local.PrinterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "budget3d_db"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideMaterialDao(db: AppDatabase): MaterialDao {
        return db.materialDao
    }

    @Provides
    @Singleton
    fun providePrinterDao(db: AppDatabase): PrinterDao {
        return db.printerDao
    }

}