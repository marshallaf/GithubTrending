package com.marshallaf.mobileui.browse

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.marshallaf.mobileui.R
import com.marshallaf.mobileui.test.TestApplication
import com.marshallaf.mobileui.test.factory.TestProjectFactory
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Rule
import org.junit.runner.RunWith
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
class BrowseActivityTest {

  @Rule @JvmField val activity = ActivityTestRule<BrowseActivity>(BrowseActivity::class.java, false, false)

  @Test fun activityLaunches() {
    val list = listOf(TestProjectFactory.makeProject())
    whenever(TestApplication.appComponent().projectsRepository().getProjects()).thenReturn(Observable.just(list))

    activity.launchActivity(null)
  }

  @Test fun projectsDisplay() {
    val projects = List(3) { TestProjectFactory.makeProject() }
    whenever(TestApplication.appComponent().projectsRepository().getProjects()).thenReturn(Observable.just(projects))
    activity.launchActivity(null)

    projects.forEachIndexed { index, project ->
      onView(withId(R.id.recycler_projects)).perform(RecyclerViewActions.scrollToPosition<BrowseAdapter.ViewHolder>(index))
      onView(withId(R.id.recycler_projects)).check(matches(hasDescendant(withText(project.name))))
    }
  }
}