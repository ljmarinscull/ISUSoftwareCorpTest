package com.project.acmetest.ui.workticket

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.project.acmetest.R
import com.project.acmetest.data.model.TicketObject
import com.project.acmetest.databinding.FragmentWorkticketBinding
import com.project.acmetest.ui.dashboard.DashboardActivity
import com.project.acmetest.ui.workticket.adapters.SectionsPagerAdapter
import com.project.acmetest.utils.onPageSelected

// the fragment initialization parameters, e.g. ARG_TICKET
const val ARG_TICKET = "ticket"

/**
 * A simple [Fragment] subclass.
 */
class WorkTicketFragment : Fragment() {

    lateinit var ticket: TicketObject
    private var _binding: FragmentWorkticketBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
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
        _binding = FragmentWorkticketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionsPagerAdapter = SectionsPagerAdapter(
            ticket,
            requireActivity().supportFragmentManager
        )
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.viewPager.setCurrentItem(0,true)
        binding.viewPager.onPageSelected{
            when(it){
                0->{
                    binding.radioGroup.check(R.id.overview)
                }
                1->{
                    binding.radioGroup.check(R.id.details)
                }
                2->{
                    binding.radioGroup.check(R.id.purchasing)
                }
                3->{
                    binding.radioGroup.check(R.id.finishingUp)
                }
            }
        }


        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.overview ->{
                    binding.viewPager.setCurrentItem(0,true)
                }
                R.id.details->{
                    binding.viewPager.setCurrentItem(1,true)
                }
                R.id.purchasing->{
                    binding.viewPager.setCurrentItem(2,true)
                }
                else-> {
                    binding.viewPager.setCurrentItem(3,true)
                }
            }
        }

        binding.pictureLogo.setOnClickListener {
            Toast.makeText(requireContext(),"Picture icon", Toast.LENGTH_LONG).show()
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