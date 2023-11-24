package com.axisting.artbookwithtesting.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.axisting.artbookwithtesting.MainCoroutineRule
import com.axisting.artbookwithtesting.getOrAwaitValueTest
import com.axisting.artbookwithtesting.repo.FakeArtRepository
import com.axisting.artbookwithtesting.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class ArtViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel : ArtViewModel


    @Before
    fun setup (){
        viewModel = ArtViewModel(FakeArtRepository())
    }


    @Test
    fun `insert art without year return ERROR` () {
        viewModel.makeArt("Mona Lisa" , " Da Vinci" , "")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Resource.Status.ERROR)
    }
    @Test
    fun `insert art without name return ERROR` () {
        viewModel.makeArt("" , " Da Vinci" , "1800")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Resource.Status.ERROR)
    }
    @Test
    fun `insert art without artistName return ERROR` () {
        viewModel.makeArt("Mona Lisa" , "" , "1800")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Resource.Status.ERROR)
    }
}