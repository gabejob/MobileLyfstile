package com.example.lyfstile

import android.view.View

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions

import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Assert
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.internal.matchers.TypeSafeMatcher

class UsernamePassScreenTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(UsernamePassScreen::class.java)

    private val newUser = User("James", "Murray", "JM@JM.com", "cooldude1", "12/17", "M", "5'11", "195", "USA", "Seattle", "")


    @Test
    fun onCreate() {
        onView(ViewMatchers.withId(R.id.welcome_group))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun onCreateNewUser() {
        onView(withIndex(withId(R.id.enter_box), 0)).perform(ViewActions.typeText(newUser.email))
        onView(withIndex(withId(R.id.enter_box), 1)).perform(ViewActions.typeText(newUser.password))
        closeSoftKeyboard()
        onView(withIndex(withId(R.id.enter_box), 2)).perform(ViewActions.typeText(newUser.password))
        closeSoftKeyboard()
        onView(ViewMatchers.withId(R.id.next_button)).perform(ViewActions.click())

        onView(ViewMatchers.withId(R.id.wyn_textView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun onCreateEmptyUser() {
        onView(withIndex(withId(R.id.enter_box), 0)).perform(ViewActions.typeText(""))
        onView(withIndex(withId(R.id.enter_box), 1)).perform(ViewActions.typeText(""))
        closeSoftKeyboard()
        onView(withIndex(withId(R.id.enter_box), 2)).perform(ViewActions.typeText(""))
        closeSoftKeyboard()
        try {
            onView(ViewMatchers.withId(R.id.next_button)).perform(ViewActions.click())
            // Toast should appear and welcome_group/activity should still be visible
            onView(ViewMatchers.withId(R.id.welcome_group))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }catch (e: NoMatchingViewException){
            Assert.assertTrue(false)
        }
    }
    /*
    * Helper to pick the correct enter_box view
     */
    private fun withIndex(matcher: Matcher<View?>, index: Int): Matcher<View?> {
        return object : TypeSafeMatcher<View?>() {
            var currentIndex = 0
            override fun describeTo(description: Description) {
                description.appendText("with index: ")
                description.appendValue(index)
                matcher.describeTo(description)
            }

            override fun matchesSafely(view: View?): Boolean {
                return matcher.matches(view) && currentIndex++ == index
            }
        }
    }
}