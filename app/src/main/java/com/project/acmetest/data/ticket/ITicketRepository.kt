package com.project.acmetest.data.ticket

import com.project.acmetest.data.model.TicketEntity
import com.project.acmetest.data.model.TicketObject
import com.project.acmetest.utils.Result
import java.time.LocalDate
import java.util.*

/**
 * Repository interface for ticket.
 */
interface ITicketRepository {
    /**
     * Add new ticket to database.
     *
     * @param ticket A new ticket.
    */
    suspend fun addTicket(ticket: TicketEntity): Boolean

    /**
     * Update a ticket in database.
     *
     * @param ticket Ticket to update.
     */
    suspend fun updateTicket(ticket: TicketEntity): Boolean

    /**
     * Delete a ticket from database.
     *
     * @param ticket Ticket to delete.
     */
    suspend fun deleteTicket(ticket: TicketEntity): Boolean

    /**
     * Get all tickets in descending order from the database.
     */
    suspend fun getAllTickets(): Result<List<TicketObject>>

    /**
     * Obtain a mutablemap of LocalDate and arraylist of tickets created in a specific month and year.
     *
     * @param month Month of ticket creation.
     * @param year Year of ticket creation.
     *
     * @return a mutablemap of LocalDate and arraylist of tickets.
     */
    suspend fun getTicketsByMonthAndYear(month: Int, year: Int): MutableMap<LocalDate, ArrayList<TicketObject>>
}