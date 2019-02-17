package nks.griplockiot.main


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import nks.griplockiot.createcourse.CreateCourseActivity
import nks.griplockiot.startgame.StartGameActivity
import nks.griplockiot.viewscorecards.ViewScoreCardsActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val mainActivityRule = IntentsTestRule(MainActivity::class.java)

    @Test
    fun testClickStartGameCheckIsStartGameActivityStarted() {
        onView(withId(nks.griplockiot.R.id.startGame)).perform(click())
        intended(hasComponent(StartGameActivity::class.java.name))
    }

    @Test
    fun testClickCreateCourseBtnIsCreateCourseActivityStarted() {
        onView(withId(nks.griplockiot.R.id.createCourse)).perform(click())
        intended(hasComponent(CreateCourseActivity::class.java.name))
    }

    @Test
    fun testClickViewScorecardsIsViewScorecardsActivityStarted() {
        onView(withId(nks.griplockiot.R.id.scoreCards)).perform(click())
        intended(hasComponent(ViewScoreCardsActivity::class.java.name))
    }

    @Test
    fun testClickExitApplicationCancelDialog() {
        onView(withId(nks.griplockiot.R.id.exit)).perform(click())
        onView(withText("CANCEL")).perform(click())
    }

    @Test
    fun testClickExitApplicationAcceptDialog() {
        onView(withId(nks.griplockiot.R.id.exit)).perform(click())
        onView(withText("OK")).perform(click())
    }

}
