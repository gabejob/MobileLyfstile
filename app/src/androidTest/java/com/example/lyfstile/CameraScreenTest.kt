package com.example.lyfstile

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import org.junit.Assert
import org.junit.Test
import java.lang.NullPointerException

class CameraScreenTest {

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

    @Test
    fun cameraTest() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), CameraScreen::class.java)
            .putExtra(
                USER_DATA,
                newUser2
            )
        val cameraActivityRule2 = ActivityScenario.launch<CameraScreen>(intent)

        // Create a bitmap we can use for our simulated camera image
        val icon: Bitmap = Bitmap.createBitmap(1,1,Bitmap.Config.ALPHA_8)

        // Build a result to return from the Camera app
        var bundle: Bundle = Bundle()
        bundle.putParcelable("data", icon)
        val resultData = Intent().putExtras(bundle)
        cameraActivityRule2.onActivity {  Instrumentation.ActivityResult(Activity.RESULT_OK, resultData) }
        val result: Instrumentation.ActivityResult =
            Instrumentation.ActivityResult(Activity.RESULT_OK, resultData)
        Intents.init()
        // Stub out the Camera. When an intent is sent to the Camera, this tells Espresso to respond
        // with the ActivityResult we just created
        Intents.intending(IntentMatchers.toPackage("com.android.camera2")).respondWith(result);
        // Now that we have the stub in place, click on the button in our app that launches into the Camera
        Espresso.onView(ViewMatchers.withId(R.id.Camera)).perform(ViewActions.click())
        try{
            Espresso.onView(ViewMatchers.withId(R.id.next_button)).perform(ViewActions.click())
        }catch (e: NoSuchElementException){
            Assert.assertTrue(false)
        }
    }

    @Test
    fun cameraNullTest() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), CameraScreen::class.java)
            .putExtra(
                USER_DATA,
                newUser2
            )
        val cameraActivityRule2 = ActivityScenario.launch<CameraScreen>(intent)

        // Create a null bitmap we can use for our simulated camera image
        val icon: Bitmap? = null

        // Build a result to return from the Camera app
        var bundle: Bundle = Bundle()
        bundle.putParcelable("data", icon)
        val resultData = Intent().putExtras(bundle)
        cameraActivityRule2.onActivity {  Instrumentation.ActivityResult(Activity.RESULT_OK, resultData) }
        val result: Instrumentation.ActivityResult =
            Instrumentation.ActivityResult(Activity.RESULT_OK, resultData)
        Intents.init()

        try{
            // This should throw an error. Photo/icon cannot be null
            Intents.intending(IntentMatchers.toPackage("com.android.camera2")).respondWith(result);
            Espresso.onView(ViewMatchers.withId(R.id.Camera)).perform(ViewActions.click())

        }catch (e: NullPointerException){
            Assert.assertTrue(true)
        }
    }
}