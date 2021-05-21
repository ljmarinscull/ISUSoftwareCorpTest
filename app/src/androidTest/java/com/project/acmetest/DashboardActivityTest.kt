package com.project.acmetest

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.project.acmetest.ui.dashboard.DashboardActivity
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test for DashboardActivity.
 **/
@RunWith(AndroidJUnit4::class)
class DashboardActivityTest {

    @Test
    fun checkIfDashBoardActivityWasLaunched() {
        val activityScenario = ActivityScenario.launch(DashboardActivity::class.java)

        onView(withId(R.id.dashboardContainer))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testingNavigationToWorkTicketFromDashboard() {
        val activityScenario = ActivityScenario.launch(DashboardActivity::class.java)

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
        onView(withText(R.string.go_to_work_ticket)).perform(click())

        onView(withId(R.id.workTicketContainer))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testingNavigationToNewTicketFromDashboard() {
        val activityScenario = ActivityScenario.launch(DashboardActivity::class.java)

        onView(withContentDescription(R.string.new_ticket)).perform(click());

        onView(withId(R.id.newTicketContainer))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testingNavigationToMapFromDashboard() {
        val activityScenario = ActivityScenario.launch(DashboardActivity::class.java)

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
        onView(withText(R.string.go_to_get_directions)).perform(click())

        onView(withId(R.id.mapContainer))
            .check(matches(isDisplayed()))
    }
}