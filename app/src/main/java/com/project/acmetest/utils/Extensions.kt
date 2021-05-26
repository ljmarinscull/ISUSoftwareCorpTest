package com.project.acmetest.utils

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import java.time.DayOfWeek
import java.time.temporal.WeekFields
import java.util.*

fun ProgressBar.showProgress(){
    visibility = View.VISIBLE
}

fun ProgressBar.hideProgress(){
    visibility = View.GONE
}

fun SearchView.onQueryTextSubmit(onQueryTextSubmit: (String?)->Unit ) {
    this.setOnQueryTextListener( object : SearchView.OnQueryTextListener{

        override fun onQueryTextSubmit(query: String?): Boolean {
            onQueryTextSubmit.invoke(query)
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }

    })
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInVisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun dpToPx(dp: Int, context: Context): Int =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
        context.resources.displayMetrics
    ).toInt()

internal val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)

internal fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

internal fun TextView.setTextColorRes(@ColorRes color: Int) = setTextColor(context.getColorCompat(color))

fun daysOfWeekFromLocale(): Array<DayOfWeek> {
    val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
    var daysOfWeek = DayOfWeek.values()
    // Order `daysOfWeek` array so that firstDayOfWeek is at index 0.
    // Only necessary if firstDayOfWeek != DayOfWeek.MONDAY which has ordinal 0.
    if (firstDayOfWeek != DayOfWeek.MONDAY) {
        val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
        val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
        daysOfWeek = rhs + lhs
    }
    return daysOfWeek
}

fun TimeZone.getAvailableTimeZone(): String? {
    val ids = TimeZone.getAvailableIDs()
    if (ids.isNotEmpty())
        return ids[0]
    return TimeZone.getDefault().id
}

fun Calendar.todayMillis(): Long {
    set(get(Calendar.YEAR), get(Calendar.MONTH), (get(Calendar.DAY_OF_MONTH)))
    return timeInMillis
}
