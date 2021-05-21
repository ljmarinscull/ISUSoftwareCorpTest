package com.project.acmetest.ui.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.project.acmetest.data.mappers.TicketDataMapper
import com.project.acmetest.data.model.TicketObject
import com.project.acmetest.databinding.FragmentNewTicketBinding
import com.project.acmetest.ui.dashboard.TicketViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class NewTicketFragment : Fragment() {

    private var _binding: FragmentNewTicketBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    @Inject
    lateinit var mMapper: TicketDataMapper
    var ticket: TicketObject = TicketObject()
    private val viewModel: TicketViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewTicketBinding.inflate(inflater, container, false)
        _binding?.ticket = ticket
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendarView.setDate(Calendar.getInstance().time.time,true,true)
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            ticket.month = month + 1
            ticket.year = year
            val localDate = LocalDate.of(year,month + 1, dayOfMonth)
            ticket.date = localDate
        }

        viewModel.addTicketStatus.observe(viewLifecycleOwner,{
            it ?: return@observe
            if(it) {
                val action = NewTicketFragmentDirections.actionNewTicketToWorkTicket(ticket)
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(),"An error occurred while inserting.", Toast.LENGTH_SHORT).show()
            }
        })

        binding.bSave.setOnClickListener {
            val newTicket = mMapper.map(ticket)
            viewModel.addTicket(newTicket)
        }
    }
}