package com.project.acmetest.ui.dashboard

import android.Manifest
import android.annotation.SuppressLint
import android.content.AsyncQueryHandler
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.view.*
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.project.acmetest.R
import com.project.acmetest.data.model.TicketObject
import com.project.acmetest.databinding.FragmentDashboardBinding
import com.project.acmetest.ui.dashboard.adapters.TicketAdapter
import com.project.acmetest.ui.dashboard.dialogs.CalendarDialogFragment
import com.project.acmetest.utils.Result
import com.project.acmetest.utils.hideProgress
import com.project.acmetest.utils.showProgress
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


// The indices for the projection array above.
private const val PROJECTION_ID_INDEX: Int = 0
@AndroidEntryPoint
class DashBoardFragment : Fragment() {

    private var goToWorkTicketMenu: MenuItem? = null
    private var _binding: FragmentDashboardBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var mAdapter: TicketAdapter
    private val viewModel: TicketViewModel by activityViewModels()
    private lateinit var tickets: List<TicketObject>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Here notify the fragment that it should participate in options menu handling.
        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewAndObservers()
        viewModel.getTickets()

        binding.calendar.setOnClickListener {
            val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            val newFragment = CalendarDialogFragment()
            newFragment.show(ft, "dialog")
        }

        binding.sync.setOnClickListener {
            Dexter.withContext(requireContext())
                .withPermissions(
                    Manifest.permission.READ_CALENDAR,
                    Manifest.permission.WRITE_CALENDAR
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {
                            if (it.areAllPermissionsGranted()) {
                                Toast.makeText(requireContext(),"Synchronising...!!!", Toast.LENGTH_LONG).show()

                                val handlerCalID = @SuppressLint("HandlerLeak")
                                object : AsyncQueryHandler(
                                    requireContext().contentResolver
                                ) {
                                    override fun onQueryComplete(token: Int, cookie: Any?, cursor: Cursor?) {
                                        if (cursor != null) {
                                            if (cursor.moveToFirst()) {
                                                val calID = cursor.getString(PROJECTION_ID_INDEX)
                                                cursor.close()
                                                syncEvents(calID.toLong())
                                            }
                                        }
                                    }
                                }

                                val projection = arrayOf(
                                    CalendarContract.Calendars._ID,
                                    CalendarContract.Calendars.CALENDAR_DISPLAY_NAME
                                )

                                handlerCalID.startQuery(
                                    0,
                                    null,
                                    CalendarContract.Calendars.CONTENT_URI,
                                    projection,
                                    CalendarContract.Calendars.VISIBLE + " = 1 AND " + CalendarContract.Calendars.IS_PRIMARY + "=1",
                                    null,
                                    CalendarContract.Calendars._ID + " ASC"
                                )}
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: List<PermissionRequest?>?,
                        token: PermissionToken?
                    ) {
                        token?.continuePermissionRequest()
                    }
                }).check()
        }
    }

    fun syncEvents(calID: Long, index:Int = 0) {
        try {
            val handlerInsertEvent = @SuppressLint("HandlerLeak")
            object : AsyncQueryHandler(
                requireContext().contentResolver
            ) {
                override fun onInsertComplete(token: Int, cookie: Any?, uri: Uri?) {
                    val next = (mAdapter.getTickets().size - 1 >= index + 1)
                    if (next)
                        syncEvents(calID, index + 1)
                    else
                        Toast.makeText(
                            requireContext(),
                            "Synchronising done successfully!",
                            Toast.LENGTH_LONG
                        ).show()
                }
            }

            val ticket = mAdapter.getTickets()[index]
            val values = createEvent(ticket, calID)
            handlerInsertEvent.startInsert(0, null, CalendarContract.Events.CONTENT_URI, values)
        } catch(e: Exception) {
            Toast.makeText(
                requireContext(),
                "A synchronisation error has occurred :(",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    private fun createEvent(ticket: TicketObject, calID: Long): ContentValues {

        val startMillis: Long = Calendar.getInstance().run {
            set(ticket.date.year, ticket.date.monthValue - 1, ticket.date.dayOfMonth, 8, 0)
            timeInMillis
        }
        val endMillis: Long = Calendar.getInstance().run {
            set(ticket.date.year, ticket.date.monthValue - 1, ticket.date.dayOfMonth, 9, 0)
            timeInMillis
        }

        val timeZone = TimeZone.getDefault().id

        return ContentValues().apply {
            put(CalendarContract.Events.DTSTART, startMillis)
            put(CalendarContract.Events.DTEND, endMillis)
            put(CalendarContract.Events.TITLE, ticket.clientName)
            put(CalendarContract.Events.EVENT_LOCATION, ticket.address)
            put(CalendarContract.Events.CALENDAR_ID, calID)
            put(CalendarContract.Events.EVENT_TIMEZONE, timeZone)
        }
    }

    private fun initViewAndObservers() = with(binding) {

        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.vertical_divider,
                null
            )!!
        )
        recyclerView.addItemDecoration(
            itemDecorator
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mAdapter = TicketAdapter(arrayListOf()) { ticketSelected(it) }
        recyclerView.adapter = mAdapter

        viewModel.tickets.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> {
                    progressBar.showProgress()
                    noTickets.visibility = View.GONE
                }
                is Result.Success -> {
                    progressBar.hideProgress()
                    if (it.data.isNotEmpty()) {
                        goToWorkTicketMenu?.isEnabled = true
                        sync.isEnabled = true
                        noTickets.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        mAdapter.setData(it.data)
                    } else {
                        goToWorkTicketMenu?.isEnabled = false
                        sync.isEnabled = false
                        noTickets.visibility = View.VISIBLE
                    }
                }
                else -> {
                    noTickets.visibility = View.VISIBLE
                    progressBar.hideProgress()
                }
            }
        }
    }

    private fun ticketSelected(ticket: TicketObject){
        val action = DashBoardFragmentDirections.actionDashboardToWorkTicket(ticket)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        goToWorkTicketMenu = menu.findItem(R.id.go_to_work_ticket)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.newTicket -> {
                val action = DashBoardFragmentDirections.actionDashboardToTicket()
                findNavController().navigate(action)
                true
            }
            R.id.go_to_work_ticket -> {
                try {
                    val ticket = mAdapter.getRecentTicket()
                    val action = DashBoardFragmentDirections.actionDashboardToWorkTicket(ticket)
                    findNavController().navigate(action)
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                }
                true
            } R.id.go_to_get_directions -> {
                val action = DashBoardFragmentDirections.actionDashboardToMapsFragment(" ")
                findNavController().navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}


