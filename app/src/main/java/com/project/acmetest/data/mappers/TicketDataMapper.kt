package com.project.acmetest.data.mappers

import com.project.acmetest.data.model.TicketEntity
import com.project.acmetest.data.model.TicketObject
import javax.inject.Inject

/**
 * A mapper to convert TicketObject to TicketEntity and reverse.
 */
class TicketDataMapper @Inject constructor(): IMapper<TicketObject, TicketEntity> {

    override fun map(input: TicketObject): TicketEntity {
        return TicketEntity(
            id = input._id,
            ticketName = input.ticketName,
            customerFullname = input.customerFullname,
            customerPhone = input.customerPhone,
            address = input.address,
            notes = input.notes,
            reasonsForCall = input.reasonsForCall,
            month = input.month,
            year = input.year,
            date= input.date
        )
    }

    override fun mapReverse(input: TicketEntity): TicketObject {
        return TicketObject(
            _id = input.id,
            _ticketName = input.ticketName!!,
            _customerFullname = input.customerFullname!!,
            _customerPhone = input.customerPhone!!,
            _address = input.address!!,
            _notes = input.notes!!,
            _reasonsForCall = input.reasonsForCall!!,
            month = input.month!!,
            year = input.year!!,
            date = input.date!!
        )
    }

    override fun mapWithOutId(input: TicketObject): TicketEntity {
        return TicketEntity(
            ticketName = input.ticketName,
            customerFullname = input.customerFullname,
            customerPhone = input.customerPhone,
            address = input.address,
            notes = input.notes,
            reasonsForCall = input.reasonsForCall,
            month = input.month,
            year = input.year,
            date= input.date
        )
    }
}