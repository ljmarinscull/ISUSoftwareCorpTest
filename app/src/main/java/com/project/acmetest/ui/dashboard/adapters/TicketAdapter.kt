package com.project.acmetest.ui.dashboard.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.acmetest.R
import com.project.acmetest.data.model.EventType
import com.project.acmetest.data.model.TicketObject
import com.project.acmetest.databinding.TicketItemLayoutBinding

class TicketAdapter(
    private var mDataSet: MutableList<TicketObject>,
    private val listener: (TicketObject, EventType) -> Unit ) :
    RecyclerView.Adapter<TicketAdapter.ViewHolder>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ticket_item_layout, parent, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val ticket = mDataSet[position]
        holder.itemView.setOnClickListener {
            listener(ticket, EventType.CLICK)
        }
        holder.itemView.setOnLongClickListener {
            listener(ticket, EventType.LONG_CLICK)
            true
        }
        holder.bind(ticket, listener)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = mDataSet.size

    fun setData(list: List<TicketObject>){
        mDataSet.clear()
        mDataSet = list.toMutableList()
        notifyDataSetChanged()
    }

    fun getRecentTicket(): TicketObject {
        if (mDataSet.isNotEmpty())
           return mDataSet[0]
        throw RuntimeException("Tickets list is empty")
    }
    fun getTickets() = mDataSet

    fun deleteTicket(id: Int) {
        mDataSet.forEachIndexed { index, it ->
            if (it._id == id){
                mDataSet.removeAt(index)
                notifyDataSetChanged()
                return
            }
        }
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val binding = TicketItemLayoutBinding.bind(view)

        fun bind(ticket: TicketObject, listener: (TicketObject, EventType) -> Unit) = with(binding){
            name.text = ticket.clientName
            button.setOnClickListener {
                listener(ticket, EventType.NONE)
            }
        }
    }
}
