package com.axisting.artbookwithtesting.view

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.axisting.artbookwithtesting.R
import com.axisting.artbookwithtesting.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ArtsFragmentTest  {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup (){
        hiltRule.inject()
    }

    @Test
    fun testNavigationFromArtToArtDetails(){
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ArtsFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navController)

        }
        Espresso.onView(ViewMatchers.withId(R.id.fab)).perform(ViewActions.click())
        Mockito.verify(navController).navigate(
            ArtsFragmentDirections.actionArtsFragmentToArtDetailsFragment()
        )

    }


}