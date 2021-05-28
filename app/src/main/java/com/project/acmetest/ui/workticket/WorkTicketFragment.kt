package com.project.acmetest.ui.workticket

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.project.acmetest.R
import com.project.acmetest.data.model.TicketObject
import com.project.acmetest.databinding.FragmentWorkTicketBinding
import com.project.acmetest.ui.dashboard.DashboardActivity

// the fragment initialization parameters, e.g. ARG_TICKET
private const val ARG_TICKET = "ticket"

/**
 * A simple [Fragment] subclass.
 */
class WorkTicketFragment : Fragment() {

    lateinit var ticket: TicketObject
    private var _binding: FragmentWorkTicketBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        savedInstanceState: Bundle?
    ): View {
        (activity as DashboardActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Inflate the layout for this fragment
        _binding = FragmentWorkTicketBinding.inflate(inflater, container, false)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.work_ticket_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                return true
            }
            R.id.go_to_dashboard -> {
                findNavController().popBackStack()
                true
            }
            R.id.go_to_get_directions -> {
                val action = WorkTicketFragmentDirections.actionWorkTicketToMapsFragment(null)
                findNavController().navigate(action)
                true
            }
            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}