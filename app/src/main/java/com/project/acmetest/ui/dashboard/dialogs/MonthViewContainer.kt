package com.project.acmetest.ui.dashboard.dialogs

import android.view.View
import com.kizitonwose.calendarview.ui.ViewContainer
import com.project.acmetest.databinding.CalendarHeaderBinding

class MonthViewContainer(view: View) : ViewContainer(view) {
    val legendLayout = CalendarHeaderBinding.bind(view).legendLayout.root
}