package com.marshallaf.mobileui.bookmarked

import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.marshallaf.mobileui.R
import com.marshallaf.mobileui.browse.BrowseAdapter
import com.marshallaf.mobileui.test.TestApplication
import com.marshallaf.mobileui.test.factory.TestProjectFactory
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Rule
import org.junit.runner.RunWith
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
class BookmarkedActivityTest {

  @Rule @JvmField val activity = ActivityTestRule<BookmarkedActivity>(BookmarkedActivity::class.java, false, false)

  @Test fun activityLaunches() {
    val projects = List(3) { TestProjectFactory.makeProject() }
    whenever(TestApplication.appComponent().projectsRepository().getBookmarkedProjects()).thenReturn(Observable.just(projects))

    activity.launchActivity(null)
  }

  @Test fun projectsDisplay() {
    val projects = List(3) { TestProjectFactory.makeProject() }
    whenever(TestApplication.appComponent().projectsRepository().getBookmarkedProjects()).thenReturn(Observable.just(projects))
    activity.launchActivity(null)

    projects.forEachIndexed { index, project ->
      Espresso.onView(ViewMatchers.withId(R.id.recycler_projects)).perform(RecyclerViewActions.scrollToPosition<BrowseAdapter.ViewHolder>(index))
      Espresso.onView(ViewMatchers.withId(R.id.recycler_projects)).check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(project.fullName))))
    }
  }
}