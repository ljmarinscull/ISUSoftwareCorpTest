package com.project.acmetest.ui.workticket.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.project.acmetest.data.model.TicketObject
import com.project.acmetest.databinding.FragmentOverviewBinding
import com.project.acmetest.ui.workticket.ARG_TICKET
import com.project.acmetest.ui.workticket.WorkTicketFragmentDirections


/**
 * A fragment representing a list of Items.
 */
class OverViewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!
    lateinit var ticket: TicketObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let{
            ticket = it.getParcelable(ARG_TICKET)!!
        }
        // Here notify the fragment that it should participate in options menu handling.
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        _binding?.ticket = ticket
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bGetDirection.setOnClickListener {
            val address = binding.customerAddress.text.toString()
            val action = WorkTicketFragmentDirections.actionWorkTicketToMapsFragment(address)
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(ticket: TicketObject) = OverViewFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_TICKET, ticket)
            }
        }
    }
}