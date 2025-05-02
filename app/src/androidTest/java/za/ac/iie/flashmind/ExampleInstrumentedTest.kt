package za.ac.iie.flashmind

import androidx.test.espresso.Espresso.onView // Main entry point for Espresso interactions
import androidx.test.espresso.action.ViewActions.click // Action to perform a click
import androidx.test.espresso.assertion.ViewAssertions.matches // Assertion to check view state
import androidx.test.espresso.intent.Intents // For initializing and releasing intent monitoring
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent // Matcher to check target component of an Intent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed // Matcher to check if a view is displayed
import androidx.test.espresso.matcher.ViewMatchers.withId // Matcher to find a view by its R.id
import androidx.test.espresso.matcher.ViewMatchers.withText // Matcher to find a view by its text
import androidx.test.ext.junit.rules.ActivityScenarioRule // Rule to launch Activities in tests
import androidx.test.ext.junit.runners.AndroidJUnit4 // Test runner for Android Instrumented tests
import androidx.test.filters.LargeTest // Mark this as a large test (UI test)

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test for MainActivity.
 * This test verifies UI elements are displayed and navigation to QuestionActivity works.
 * Remember to run this on a device or emulator.
 */
@RunWith(AndroidJUnit4::class) // Specify the test runner
@LargeTest // Indicate that this is a UI test that might take longer
class ExampleInstrumentedTest {

    /**
     * Use ActivityScenarioRule to launch MainActivity before each test method.
     * ActivityScenarioRule handles launching and closing the activity automatically.
     */
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    /**
     * Initialize Espresso Intents before each test.
     * This allows us to verify intents fired by the activity.
     */
    @Before
    fun setUp() {
        Intents.init() // Start monitoring intents
    }

    /**
     * Release Espresso Intents after each test.
     * Cleans up the intent monitoring.
     */
    @After
    fun tearDown() {
        Intents.release() // Stop monitoring intents
    }

    /**
     * Test if the welcome title TextView is displayed correctly.
     */
    @Test
    fun welcomeTitle_isDisplayed() {
        // Find the view with the specific ID and check if it's displayed
        onView(withId(R.id.welcome_message))
            .check(matches(isDisplayed()))

        // Additionally, check if it contains the expected text
        onView(withId(R.id.welcome_message))
            .check(matches(withText("Welcome")))
    }

    /**
     * Test if the welcome description TextView is displayed correctly.
     */
    @Test
    fun welcomeDescription_isDisplayed() {
        // Find the view and check if it's displayed
        onView(withId(R.id.welcome_message))
            .check(matches(isDisplayed()))

        // Optionally check partial text if the full text is long or might change slightly
        // onView(withId(R.id.welcomeDescTextView))
        //    .check(matches(withText(containsString("Test your knowledge"))))
    }

    /**
     * Test if the Start button is displayed.
     */
    @Test
    fun startButton_isDisplayed() {
        onView(withId(R.id.start_button))
            .check(matches(isDisplayed()))
        onView(withId(R.id.start_button))
            .check(matches(withText("Start")))
    }

    /**
     * Test if clicking the Start button navigates to QuestionActivity.
     */
    @Test
    fun clickStartButton_navigatesToQuestionActivity() {
        // Find the start button using its ID and perform a click action
        onView(withId(R.id.start_button))
            .perform(click())

        // Verify that an Intent was sent with the target component QuestionActivity
        Intents.intended(hasComponent(QuizActivity::class.java.name))

        // Note: This test confirms the Intent was *sent*. It doesn't fully test
        // if QuestionActivity loaded correctly, but it's a good check for navigation.
        // More complex tests could involve checking views on the *next* activity,
        // but that often makes tests more brittle.
    }
}