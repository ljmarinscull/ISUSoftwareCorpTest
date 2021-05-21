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
            clientName = input.clientName,
            address = input.address,
            month = input.month,
            year = input.year,
            date= input.date
        )
    }

    override fun mapReverse(input: TicketEntity): TicketObject {
        return TicketObject(
            _clientName = input.clientName!!,
            _address = input.address!!,
            month = input.month!!,
            year = input.year!!,
            date = input.date!!
        )
    }
}