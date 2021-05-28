package com.project.acmetest.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "tickets")
data class TicketEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = "_id") var id: Int = 0,

    @ColumnInfo(name = "ticket_name")
    var ticketName: String?,

    @ColumnInfo(name = "customer_fullname")
    var customerFullname: String?,

    @ColumnInfo(name = "customer_phone")
    var customerPhone: String?,

    @ColumnInfo(name = "address")
    var address: String?,

    @ColumnInfo(name = "notes")
    var notes: String?,

    @ColumnInfo(name = "reasonsForCall")
    var reasonsForCall: String?,

    @ColumnInfo(name = "month")
    var month: Int?,

    @ColumnInfo(name = "year")
    var year: Int?,

    @ColumnInfo(name = "date")
    var date: LocalDate?,
){
    constructor() : this(0,"","", "", "", "", "", LocalDate.now().dayOfMonth, LocalDate.now().dayOfYear, LocalDate.now())
    constructor(ticketName: String, customerFullname: String, customerPhone: String, address: String, notes: String,reasonsForCall: String, month: Int, year: Int, date: LocalDate) : this() {
        this.ticketName = ticketName
        this.customerFullname = customerFullname
        this.customerPhone = customerPhone
        this.address = address
        this.notes = notes
        this.reasonsForCall = reasonsForCall
        this.month = month
        this.year = year
        this.date = date
    }
}