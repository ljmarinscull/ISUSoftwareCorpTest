package com.project.acmetest

import android.text.InputType
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.project.acmetest.ui.auth.LoginActivity
import org.hamcrest.CoreMatchers.not
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test for LoginActivity.
 */
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @Test
    fun checkIfLoginActivityWasLaunched() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.loginContainer))
            .check(matches(isDisplayed()))
    }

    @Test
    fun checkIfSignInButtonIsEnabled() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.bSignIn))
            .check(matches(isEnabled()))
    }

    @Test
    fun checkIfUsernameFieldHasHintAndItIsEmpty() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.loginContainer))
            .check(matches(isDisplayed()))

        onView(withId(R.id.username))
            .check(matches(withHint(R.string.prompt_username)))

        onView(withId(R.id.username))
            .check(matches(withText("")))
    }

    @Test
    fun checkIfPasswordFieldHasHintAndItIsEmpty() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.loginContainer))
            .check(matches(isDisplayed()))

        onView(withId(R.id.password)).check(matches(withInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)))

        onView(withId(R.id.password))
            .check(matches(withHint(R.string.prompt_password)))

        onView(withId(R.id.password))
            .check(matches(withText("")))
    }

    @Test
    fun checkIfSignInButtonGetDisableWhenPasswordHasError() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.loginContainer))
            .check(matches(isDisplayed()))

        onView(withId(R.id.username)).perform(typeText("klsklas"), closeSoftKeyboard())

        onView(withId(R.id.bSignIn)).check(matches(not(isEnabled())))
    }

    @Test
    fun checkIfSignInButtonGetEnableWhenUsernameAndPasswordHaveValidData() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.loginContainer))
            .check(matches(isDisplayed()))

        onView(withId(R.id.username)).perform(typeText("username"), closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText("password"), closeSoftKeyboard())

        onView(withId(R.id.bSignIn)).check(matches(isEnabled()))
    }

    @Test
    fun checkAutenticationErrorMessage() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.loginContainer))
            .check(matches(isDisplayed()))

        onView(withId(R.id.username)).perform(typeText("username"), closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText("password"), closeSoftKeyboard())

        onView(withId(R.id.bSignIn)).check(matches(isEnabled()))
        onView(withId(R.id.bSignIn)).perform(click())

        onView(withText(R.string.login_failed))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }

    @Test
    fun checkAutenticationSuccessMessage() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.loginContainer))
            .check(matches(isDisplayed()))

        onView(withId(R.id.username)).perform(typeText("admin"), closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText("admin123"), closeSoftKeyboard())

        onView(withId(R.id.bSignIn)).check(matches(isEnabled()))
        onView(withId(R.id.bSignIn)).perform(click())

        onView(withText(R.string.welcome))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }

    @Test
    fun checkLoginActivityNavigation() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.loginContainer))
            .check(matches(isDisplayed()))

        onView(withId(R.id.username)).perform(typeText("admin"), closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText("admin123"), closeSoftKeyboard())

        onView(withId(R.id.bSignIn)).check(matches(isEnabled()))
        onView(withId(R.id.bSignIn)).perform(click())

        onView(withId(R.id.dashboardContainer))
            .check(matches(isDisplayed()))
    }
}