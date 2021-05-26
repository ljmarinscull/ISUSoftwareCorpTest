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

    private val _updateTicketStatus = SingleLiveEvent<Boolean>()
    val updateTicketStatus: LiveData<Boolean> = _updateTicketStatus

    private val _deleteTicketStatus = SingleLiveEvent<Boolean>()
    val deleteTicketStatus: LiveData<Boolean> = _deleteTicketStatus

    private var isTicketUpdating = false
    private var tickeIdToDelete = -1

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
     * Update a ticket in database.
     *
     * @param ticket Ticket to update.
     */
    fun updateTicket(ticket: TicketEntity) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                var result: Boolean
                withContext(Dispatchers.IO) {
                    result = repository.updateTicket(ticket)
                }
                _updateTicketStatus.value = result
            } catch (e: Exception) {
                _updateTicketStatus.value = false
            }
        }
    }

    /**
     * Delete a ticket from database.
     *
     * @param ticket Ticket to delete.
     */
    fun deleteTicket(ticket: TicketEntity) {
        tickeIdToDelete = ticket.id
        viewModelScope.launch(Dispatchers.Main) {
            try {
                var result: Boolean
                withContext(Dispatchers.IO) {
                    result = repository.deleteTicket(ticket)
                }
                _deleteTicketStatus.value = result
            } catch (e: Exception) {
                tickeIdToDelete = -1
                _deleteTicketStatus.value = false
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

    /**
     * Change the estate of the fLag to know if the user is updating a ticket or not.
     *
     * @param state If the user is updating or not.
     *
     */
    fun setUpdatingState(state: Boolean){
        isTicketUpdating = state
    }

    /**
     * Get the estate of the fLag to know if the user is updating a ticket or not.
     *
     * @return true is user is updating a ticket false otherwise.
     */
    fun getUpdatingState() = isTicketUpdating

    /**
     * Get the id of the last ticket for deleting.
     *
     * @return the id of the last ticket for deleting.
     */
    fun getTicketIdToDelete() = tickeIdToDelete
}