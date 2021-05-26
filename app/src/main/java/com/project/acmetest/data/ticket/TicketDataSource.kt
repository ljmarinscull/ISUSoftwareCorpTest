package com.project.acmetest.data.ticket

import com.project.acmetest.data.mappers.TicketDataMapper
import com.project.acmetest.data.model.TicketDao
import com.project.acmetest.data.model.TicketEntity
import com.project.acmetest.data.model.TicketObject
import com.project.acmetest.utils.Result
import java.io.IOException
import java.time.LocalDate
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Class that handles authentication.
 */
class TicketDataSource @Inject constructor(
    private val ticketDao: TicketDao,
    private val mapper: TicketDataMapper)
    : ITicketDataSource {

    override suspend fun addTicket(ticket: TicketEntity): Boolean {
        return try {
            ticketDao.insert(ticket)
            true
        } catch (e: Throwable) {
            false
        }
    }

    override suspend fun updateTicket(ticket: TicketEntity): Boolean {
        return try {
            ticketDao.update(ticket)
            true
        } catch (e: Throwable) {
            false
        }
    }

    override suspend fun deleteTicket(ticket: TicketEntity): Boolean {
        return try {
            ticketDao.delete(ticket)
            true
        } catch (e: Throwable) {
            false
        }
    }

    override suspend fun getAllTickets(): Result<List<TicketObject>> {
        try {
            val result = ticketDao.getAll()
            val newList =  result.map { mapper.mapReverse(it) }
            return Result.Success(newList)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error inserting a ticket", e))
        }
    }

    override suspend fun getTicketsByMonthAndYear(month: Int, year: Int): MutableMap<LocalDate, ArrayList<TicketObject>> {
        try {
            val tickets = ticketDao.getTicketsByMonthAndYear(month, year)
            val resultMap = mutableMapOf<LocalDate, ArrayList<TicketObject>>()
            for (ticket in tickets){
               if(resultMap.containsKey(ticket.date!!)){
                  val list = resultMap[ticket.date!!]
                   list?.add(mapper.mapReverse(ticket))
               } else {
                   val list = arrayListOf<TicketObject>()
                   list.add(mapper.mapReverse(ticket))
                   resultMap[ticket.date!!] = list
               }
            }
            return resultMap
        } catch (e: Throwable) {
            throw IOException("Error getting tickets", e)
        }
    }
}