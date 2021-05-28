package com.project.acmetest.ui.dashboard.dialogs

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.acmetest.data.model.TicketObject
import com.project.acmetest.databinding.EventItemViewBinding
import com.project.acmetest.utils.layoutInflater

class EventsAdapter :
    RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    val events = mutableListOf<TicketObject>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        return EventsViewHolder(
            EventItemViewBinding.inflate(parent.context.layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(viewHolder: EventsViewHolder, position: Int) {
        viewHolder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    inner class EventsViewHolder(private val binding: EventItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: TicketObject) {
            binding.itemEventText.text = event.ticketName
        }
    }
}