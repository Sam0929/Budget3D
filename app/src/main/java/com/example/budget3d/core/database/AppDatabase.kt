package com.example.budget3d.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.budget3d.feature.material.data.local.MaterialEntity
import com.example.budget3d.feature.material.data.local.MaterialDao
import com.example.budget3d.feature.printer.data.local.PrinterEntity
import com.example.budget3d.feature.printer.data.local.PrinterDao

@Database(
    entities = [
        MaterialEntity::class,
        PrinterEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val materialDao: MaterialDao
    abstract val printerDao: PrinterDao
}