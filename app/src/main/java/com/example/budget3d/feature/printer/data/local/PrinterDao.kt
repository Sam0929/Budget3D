package com.example.budget3d.feature.printer.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PrinterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrinter(printer: PrinterEntity)

    @Query("SELECT * FROM printers ORDER BY name ASC")
    fun getAllPrinters(): Flow<List<PrinterEntity>>

    @Delete
    suspend fun deletePrinter(printer: PrinterEntity)
}