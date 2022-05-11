package com.example.github.repositories.features

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaClickInteractions
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.example.github.repositories.R
import com.example.github.repositories.utils.BaseUiTest
import org.hamcrest.CoreMatchers
import org.junit.Test

class UserDetailAndRepositoryListShould : BaseUiTest() {

    @Test
    fun displayUserDetail() {
        navigateToRepositoryUserRepositoryList()
        Thread.sleep(1000)
        BaristaVisibilityAssertions.assertDisplayed("Twitter handle: SquareDev")
    }

    @Test
    fun displayUserReposList() {
        navigateToRepositoryUserRepositoryList()
        Thread.sleep(3000)

        BaristaRecyclerViewAssertions.assertRecyclerViewItemCount(R.id.repositoryList, 20)

        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.name),
                ViewMatchers.isDescendantOfA(nthChildOf(ViewMatchers.withId(R.id.repositoryList), 0))
            )
        )
            .check(ViewAssertions.matches(ViewMatchers.withText("square/Aardvark")))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.author),
                ViewMatchers.isDescendantOfA(nthChildOf(ViewMatchers.withId(R.id.repositoryList), 0))
            )
        )
            .check(ViewAssertions.matches(ViewMatchers.withText("square")))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun navigateToRepositoryUserRepositoryList() {
        Thread.sleep(2000)
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.name),
                ViewMatchers.isDescendantOfA(nthChildOf(ViewMatchers.withId(R.id.repositoryList), 0))
            )
        ).perform(ViewActions.click())

        assertDisplayed(R.id.repositoryDetail)
        Thread.sleep(1000)
        BaristaClickInteractions.clickOn(R.id.detail)
        Thread.sleep(1000)
        assertDisplayed(R.id.userandRepositoryList)

        clickOn(R.id.detail)
        Thread.sleep(1000)
    }

}