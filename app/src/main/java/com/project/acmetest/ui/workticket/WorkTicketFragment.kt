package com.project.acmetest.ui.workticket

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.project.acmetest.R
import com.project.acmetest.data.model.TicketObject
import com.project.acmetest.databinding.FragmentWorkticketBinding
import com.project.acmetest.ui.dashboard.DashboardActivity
import com.project.acmetest.ui.map.MapsActivity
import com.project.acmetest.ui.workticket.adapters.SectionsPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.project.acmetest.utils.onPageSelected

// the fragment initialization parameters, e.g. ARG_TICKET
const val ARG_TICKET = "ticket"

/**
 * A simple [Fragment] subclass.
 */
class WorkTicketFragment : Fragment() {

    lateinit var ticket: TicketObject
    lateinit var sectionsPagerAdapter: SectionsPagerAdapter
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

        sectionsPagerAdapter = SectionsPagerAdapter(
            ticket,
            this
        )
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.viewPager.onPageSelected {
            when(it){
                0->{
                    if (!binding.overview.isChecked)
                    binding.radioGroup.check(R.id.overview)
                }
                1->{
                    if (!binding.details.isChecked)
                    binding.radioGroup.check(R.id.details)
                }
                2->{
                    if (!binding.purchasing.isChecked)
                    binding.radioGroup.check(R.id.purchasing)
                }
                3->{
                    if (!binding.finishingUp.isChecked)
                    binding.radioGroup.check(R.id.finishingUp)
                }
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.overview ->{
                    if (binding.viewPager.currentItem != 0)
                    binding.viewPager.setCurrentItem(0,true)
                }
                R.id.details->{
                    if (binding.viewPager.currentItem != 1)
                    binding.viewPager.setCurrentItem(1,true)
                }
                R.id.purchasing->{
                    if (binding.viewPager.currentItem != 2)
                    binding.viewPager.setCurrentItem(2,true)
                }
                else-> {
                    if (binding.viewPager.currentItem != 3)
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
                startActivity(Intent(requireActivity(), MapsActivity::class.java))
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