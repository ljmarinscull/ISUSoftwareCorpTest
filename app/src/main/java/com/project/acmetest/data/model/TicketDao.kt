package com.project.acmetest.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TicketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ticket: TicketEntity)

    @Query("SELECT * FROM tickets ORDER BY _id DESC")
    suspend fun getAll(): List<TicketEntity>

    @Query("SELECT * FROM tickets WHERE month = :month AND year = :year")
    suspend fun getTicketsByMonthAndYear(month: Int, year: Int): List<TicketEntity>
}