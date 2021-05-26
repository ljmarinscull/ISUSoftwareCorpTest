package com.project.acmetest.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "tickets")
data class TicketEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = "_id") var id: Int = 0,

    @ColumnInfo(name = "clientName")
    var clientName: String?,

    @ColumnInfo(name = "address")
    var address: String?,

    @ColumnInfo(name = "month")
    var month: Int?,

    @ColumnInfo(name = "year")
    var year: Int?,

    @ColumnInfo(name = "date")
    var date: LocalDate?
){
    constructor() : this(0,"","", LocalDate.now().dayOfMonth, LocalDate.now().dayOfYear, LocalDate.now())
    constructor(clientName: String, address: String, month: Int, year: Int, date: LocalDate) : this() {
        this.clientName = clientName
        this.address = address
        this.month = month
        this.year = year
        this.date = date
    }
}