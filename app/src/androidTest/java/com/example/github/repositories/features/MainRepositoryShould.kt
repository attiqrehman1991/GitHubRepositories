package com.example.github.repositories.features

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.adevinta.android.barista.interaction.BaristaClickInteractions
import com.example.github.repositories.R
import com.example.github.repositories.utils.BaseUiTest
import org.hamcrest.CoreMatchers
import org.junit.Test

class MainRepositoryShould : BaseUiTest() {

    @Test
    fun displayListOfRepository() {
        Thread.sleep(3000)
        BaristaRecyclerViewAssertions.assertRecyclerViewItemCount(R.id.repositoryList, 20)

        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.name),
                ViewMatchers.isDescendantOfA(nthChildOf(withId(R.id.repositoryList), 0))
            )
        )
            .check(ViewAssertions.matches(ViewMatchers.withText("square/okhttp")))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.author),
                ViewMatchers.isDescendantOfA(nthChildOf(withId(R.id.repositoryList), 0))
            )
        )
            .check(ViewAssertions.matches(ViewMatchers.withText("square")))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun displayLoaderWhileFetchingTheRepositories() {
        assertNotDisplayed(R.id.loader)
    }

    @Test
    fun hideLoaderAfterFetchingTheRepositories() {
        assertNotDisplayed(R.id.loader)
    }

    @Test
    fun navigateToRepositoryUserDetailScreen() {
        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.name),
                ViewMatchers.isDescendantOfA(nthChildOf(withId(R.id.repositoryList), 0))
            )
        ).perform(click())

        assertDisplayed(R.id.repositoryDetail)
        Thread.sleep(1000)
        BaristaClickInteractions.clickOn(R.id.detail)
        Thread.sleep(1000)
    }
}