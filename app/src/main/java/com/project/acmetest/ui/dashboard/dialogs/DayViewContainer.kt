package com.project.acmetest.ui.dashboard.dialogs

import android.view.View
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.ViewContainer
import com.project.acmetest.databinding.CalendarDayBinding
import java.time.LocalDate

class DayViewContainer(view: View, listener: (LocalDate)-> Unit) : ViewContainer(view) {
    lateinit var day: CalendarDay // Will be set when this container is bound.
    val binding = CalendarDayBinding.bind(view)

    init {
        view.setOnClickListener {
            if (day.owner == DayOwner.THIS_MONTH) {
                listener(day.date)
            }
        }
    }
}