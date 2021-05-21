package com.project.acmetest.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.acmetest.data.model.TicketEntity
import com.project.acmetest.data.model.TicketObject
import com.project.acmetest.data.ticket.ITicketRepository
import com.project.acmetest.utils.Result
import com.project.acmetest.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * TicketViewModel
 *
 * ViewModel that handles the ticket business logic.
 *
 * @property repository that handles the ticket business logic.
 * @property _ticketsStatus MutableLiveData to store the status of the operation [getTickets].
 * @property tickets LiveData to observe the status of the [getTickets] operation.
 * @property _addTicketStatus MutableLiveData to store the status of the operation [addTicket].
 * @property addTicketStatus LiveData to observe the status of the [addTicket] operation.
 *
 * @constructor Creates an instance of TicketViewModel.
 */
@HiltViewModel
class TicketViewModel @Inject constructor(
    private val repository: ITicketRepository
): ViewModel() {

    private val _ticketsStatus = MutableLiveData<Result<List<TicketObject>>>()
    val tickets: LiveData<Result<List<TicketObject>>> = _ticketsStatus

    private val _addTicketStatus = SingleLiveEvent<Boolean>()
    val addTicketStatus: LiveData<Boolean> = _addTicketStatus

    /**
     * Get all tickets in descending order from the database.
     */
    fun getTickets() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _ticketsStatus.value = Result.Loading()
                var result : Result<List<TicketObject>>
                withContext(Dispatchers.IO) {
                    result = repository.getAllTickets()
                }
                _ticketsStatus.value = result
            } catch (e: Exception){
                _ticketsStatus.value = Result.Error(e)
            }
        }
    }

    /**
     * Add new ticket to database.
     *
     * @param ticket A new ticket.
     */
    fun addTicket(ticket: TicketEntity) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                var result: Boolean
                withContext(Dispatchers.IO) {
                    result = repository.addTicket(ticket)
                }
                _addTicketStatus.value = result
            } catch (e: Exception) {
                _addTicketStatus.value = false
            }
        }
    }

    /**
     * Obtain a mutablemap of LocalDate and arraylist of tickets created in a specific month and year.
     *
     * @param month Month of ticket creation.
     * @param year Year of ticket creation.
     *
     * @return a mutablemap of LocalDate and arraylist of tickets.
     */
    suspend fun getTicketsByMonthAndYear(month: Int, year: Int) = repository.getTicketsByMonthAndYear(month, year)
}