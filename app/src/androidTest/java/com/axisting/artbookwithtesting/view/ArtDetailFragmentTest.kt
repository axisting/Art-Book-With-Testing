package com.axisting.artbookwithtesting.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.axisting.artbookwithtesting.R
import com.axisting.artbookwithtesting.getOrAwaitValue
import com.axisting.artbookwithtesting.launchFragmentInHiltContainer
import com.axisting.artbookwithtesting.model.Art
import com.axisting.artbookwithtesting.repo.FakeArtRepositoryTest
import com.axisting.artbookwithtesting.viewmodel.ArtViewModel
import com.google.common.truth.Truth
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
class ArtDetailFragmentTest {


    @get:Rule
    var hiltRule  = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory : ArtFragmentFactory

    @Before
    fun setup (){
        hiltRule.inject()
    }

    @Test
    fun testNavigationArtDetailToImageApi(){
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(), navController)
        }
        Espresso.onView(ViewMatchers.withId(R.id.artImageView)).perform(click())
        Mockito.verify(navController).navigate(
            ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment()
        )

    }
    @Test
    fun testOnBackPressed(){
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(), navController)
        }
        Espresso.pressBack()
        Mockito.verify(navController).popBackStack()

    }

    @Test
    fun testSave (){
        val navController = Mockito.mock(NavController::class.java)
        val testViewModel = ArtViewModel(FakeArtRepositoryTest())
        launchFragmentInHiltContainer<ArtDetailsFragment>(
                factory = fragmentFactory
        ){
            viewModel = testViewModel
            Navigation.setViewNavController(requireView(), navController)

        }
        Espresso.onView(ViewMatchers.withId(R.id.artNameEditText)).perform(replaceText("Mona Lisa"))
        Espresso.onView(ViewMatchers.withId(R.id.artistNameEditText)).perform(replaceText("Da Vinci"))
        Espresso.onView(ViewMatchers.withId(R.id.artYearEditText)).perform(replaceText("1500"))
        Espresso.onView(ViewMatchers.withId(R.id.saveButton)).perform(click())

        Truth.assertThat(testViewModel.artList.getOrAwaitValue()).contains(
            Art("Mona Lisa" , "Da Vinci" , 1500 , "")
        )



    }


}