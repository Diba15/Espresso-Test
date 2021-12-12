package org.d3ifcool.espressotest

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.d3ifcool.espressotest.data.Mahasiswa
import org.d3ifcool.espressotest.data.MahasiswaDb
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    companion object {
        private val MAHASISWA_DUMMY = Mahasiswa(
            0, "6706192089", "Dimas Bagas Saputro"
        )
    }

    @Before
    fun setUp() {
        InstrumentationRegistry.getInstrumentation().targetContext
            .deleteDatabase(MahasiswaDb.DATABASE_NAME)
    }

    @Test
    fun insert() {
        val activityScenario = ActivityScenario.launch(
            MainActivity::class.java
        )

        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.nimEditText)).perform(typeText(MAHASISWA_DUMMY.nim))
        onView(withId(R.id.namaEditText)).perform(typeText(MAHASISWA_DUMMY.nama))
        onView(withText(R.string.simpan)).perform(click())

        onView(withText(MAHASISWA_DUMMY.nim)).check(matches(isDisplayed()))
        onView(withText(MAHASISWA_DUMMY.nama)).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun action() {
        runBlocking(Dispatchers.IO) {
            val dao = MahasiswaDb.getInstance(ApplicationProvider.getApplicationContext()).dao
            dao.insertData(MAHASISWA_DUMMY)
            dao.insertData(MAHASISWA_DUMMY)
            dao.insertData(MAHASISWA_DUMMY)
        }

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.recyclerView)).atItem(0, longClick())
        onView(withId(R.id.menu_delete)).check(matches(isDisplayed()))

        onView(withId(R.id.recyclerView)).atItem(2, click())
        onView(withId(R.id.recyclerView)).atItem(1, click())
        onView(withText("3")).check(matches(isDisplayed()))

        onView(withId(R.id.menu_delete)).perform(click())
        onView(withText(R.string.hapus)).perform(click())
        onView(withText(MAHASISWA_DUMMY.nim)).check(ViewAssertions.doesNotExist())
        onView(withText(MAHASISWA_DUMMY.nama)).check(ViewAssertions.doesNotExist())

        activityScenario.close()

    }

    @Test
    fun testDelete() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.recyclerView)).atItem(0, longClick())
        onView(withId(R.id.menu_delete)).perform(click())
        onView(withText(R.string.hapus)).perform(click())
        onView(withText(MAHASISWA_DUMMY.nim)).check(ViewAssertions.doesNotExist())
        onView(withText(MAHASISWA_DUMMY.nama)).check(ViewAssertions.doesNotExist())

        activityScenario.close()
    }

    @Test
    fun testDialog() {
        val activityScenario = ActivityScenario.launch(
            MainActivity::class.java
        )

        onView(withId(R.id.nimEditText)).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun notExist() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))

        activityScenario.close()
    }

    private fun ViewInteraction.atItem(pos: Int, action: ViewAction) {
        perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                pos, action
            )
        )
    }
}