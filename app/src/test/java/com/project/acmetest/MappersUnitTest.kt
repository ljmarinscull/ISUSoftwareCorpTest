package com.project.acmetest

import com.project.acmetest.data.mappers.TicketDataMapper
import com.project.acmetest.data.model.TicketEntity
import com.project.acmetest.data.model.TicketObject
import org.junit.Test

import org.junit.Assert.*

/**
 * Mappers local unit test, which will execute on the development machine (host).
 */
class MappersUnitTest {

    @Test
    fun testNewsDataMapper_Method_Map(){
        val mapper = TicketDataMapper()
        val ticketObj = TicketObject()

        val ticketEntity = mapper.map(ticketObj)
        assertEquals(ticketObj.clientName, ticketEntity.clientName)
        assertEquals(ticketObj.address, ticketEntity.address)
        assertEquals(ticketObj.month, ticketEntity.month)
        assertEquals(ticketObj.year, ticketEntity.year)
        assertEquals(ticketObj.date, ticketEntity.date)
    }

    @Test
    fun testNewsDataMapper_Method_MapReverse(){
        val mapper = TicketDataMapper()
        val ticketEntity = TicketEntity()

        val ticketObj = mapper.mapReverse(ticketEntity)
        assertEquals(ticketObj.clientName, ticketEntity.clientName)
        assertEquals(ticketObj.address, ticketEntity.address)
        assertEquals(ticketObj.month, ticketEntity.month)
        assertEquals(ticketObj.year, ticketEntity.year)
        assertEquals(ticketObj.date, ticketEntity.date)
    }
}