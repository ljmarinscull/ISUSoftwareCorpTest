package com.project.acmetest.data.ticket

import com.project.acmetest.data.model.TicketEntity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class that requests authentication.
 * @property dataSource the datasource for the authentication.
 * @constructor Creates an AuthRepository.
 */
class TicketRepository @Inject constructor(
    private val dataSource: ITicketDataSource
    ): ITicketRepository {

    override suspend fun addTicket(ticket: TicketEntity) = dataSource.addTicket(ticket)
    override suspend fun getAllTickets() = dataSource.getAllTickets()
    override suspend fun getTicketsByMonthAndYear(month: Int, year: Int) = dataSource.getTicketsByMonthAndYear(month,year)
}