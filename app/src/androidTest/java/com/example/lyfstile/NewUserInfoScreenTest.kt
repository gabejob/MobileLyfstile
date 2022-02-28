package com.example.lyfstile

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.view.View
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.junit.internal.matchers.TypeSafeMatcher
import androidx.test.rule.ActivityTestRule
import java.util.*
import android.widget.DatePicker

import org.hamcrest.Matchers

import androidx.test.espresso.matcher.ViewMatchers.withClassName

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import java.lang.Exception


class NewUserInfoScreenTest {

    private val newUser = User(
        "James",
        "Murray",
        "JM@JM.com",
        "cooldude1",
        "12/17/2021",
        "M",
        "5'11",
        "195",
        "USA",
        "Seattle",
        ""
    )
    private val newUser2 = User(
        "James",
        "Murray",
        "JM@JM.com",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    )

/*    @get:Rule
    val activityRule = ActivityScenarioRule(NewUserInfoScreen::class.java)*/

    @get:Rule
    val activityRule: ActivityTestRule<NewUserInfoScreen> =
        object : ActivityTestRule<NewUserInfoScreen>(NewUserInfoScreen::class.java) {
            override fun getActivityIntent(): Intent {
                val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
                return Intent(targetContext, NewUserInfoScreen::class.java).apply {
                    putExtra(
                        USER_DATA,
                        newUser2)
                }
            }
        }

    /*
    * Helper to pick the correct enter_box view
    *
    * */
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

    @Test
    fun onCreate() {
        onView(ViewMatchers.withId(R.id.wyn_textView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun firstNameLastNameTest() {
        onView(withIndex(ViewMatchers.withId(R.id.enter_box), 0)).perform(ViewActions.typeText(newUser.firstName))
        onView(withIndex(ViewMatchers.withId(R.id.enter_box), 1)).perform(ViewActions.typeText(newUser.lastName))
        Espresso.closeSoftKeyboard()
        Thread.sleep(500)
        onView(ViewMatchers.withId(R.id.next_button)).perform(ViewActions.click())
        onView(ViewMatchers.withText("Tell us about you")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun ageSexTest() {
        // Avoid code duplication
        firstNameLastNameTest()

        // Age Sex
        onView(withIndex(withId(R.id.enter_box), 0)).perform(click())
        try{
            onView(withClassName(Matchers.equalTo(DatePicker::class.java.name))).perform(
                PickerActions.setDate(
                    1989,
                    8,
                    25
                )
            )
        }catch(e: NoMatchingViewException){
            onView(withIndex(withId(R.id.enter_box), 0)).perform(click())
            onView(withClassName(Matchers.equalTo(DatePicker::class.java.name))).perform(
                PickerActions.setDate(
                    1989,
                    8,
                    25
                )
            )
        }
        onView(withText("OK")).perform(click())
        Espresso.closeSoftKeyboard()

        onView(withIndex(withId(R.id.enter_box), 1)).perform(click())
        try {
            onView(withText("Male")).perform(click())
        }catch(e: NoMatchingViewException){
            onView(withIndex(withId(R.id.enter_box), 1)).perform(click())
            onView(withText("Male")).perform(click())
        }
        onView(withText("OK")).perform(click())

        Espresso.onView(ViewMatchers.withId(R.id.next_button)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("weight")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun heightWeightTest() {
        ageSexTest()

        // Height
        onView(withIndex(withId(R.id.enter_box), 0)).perform(click())
        try {
            onView(withText("OK")).perform(click())
            // Too many issues doing it this way, may look into it later
            /*onView(withText("12")).perform(click())
            onView(withText("1")).perform(click())*/
        }catch(e: NoMatchingViewException){
            onView(withIndex(withId(R.id.enter_box), 0)).perform(click())
            onView(withText("OK")).perform(click())

            // Too many issues doing it this way, may look into it later
            /*onView(withText("12")).perform(click())
            onView(withText("1")).perform(click())*/
        }

        Thread.sleep(500)

        // Weight
        onView(withIndex(withId(R.id.enter_box), 1)).perform(click())
        try {
            onView(withText("OK")).perform(click())
            // Too many issues doing it this way, may look into it later
            /*onView(withText("1")).perform(click())
            onView(withText("1000")).perform(click())*/
        }catch(e: NoMatchingViewException){
            onView(withIndex(withId(R.id.enter_box), 1)).perform(click())
            onView(withText("OK")).perform(click())
            // Too many issues doing it this way, may look into it later
            /*onView(withText("1")).perform(click())
            onView(withText("1000")).perform(click())*/
        }
        Thread.sleep(500)
        onView(withId(R.id.next_button)).perform(ViewActions.click())
        onView(withText("country")).check(ViewAssertions.matches(isDisplayed()))

    }

    @Test
    fun countryCityTest() {
        heightWeightTest()

    }

    @Test
    fun onBackPressed() {
    }
}