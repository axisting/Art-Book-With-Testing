package com.axisting.artbookwithtesting.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.axisting.artbookwithtesting.getOrAwaitValue
import com.axisting.artbookwithtesting.model.Art
import com.google.ar.core.Config
import com.google.common.truth.Truth

import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database: ArtDatabase

    private lateinit var dao : ArtDao
    //private lateinit var database: ArtDatabase


    @Before
    fun setup (){
        /*
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext() , ArtDatabase::class.java
        ).allowMainThreadQueries().build()

         */
        hiltRule.inject()
        dao = database.artDao()
    }

    @After
    fun teardown(){

        database.close()
    }

    @Test
    fun insertArtTesting ()  = runBlockingTest{

        val exampleArt = Art ("Mona Lisa" , "Da Vinci", 1800 , ".com" , 88)
        val exampleWrongArt = Art ("Mona" , "Da Vinci", 1800 , ".com" , 555)

        dao.insertArt(exampleArt)
        val list = dao.observeArts().getOrAwaitValue()

        assertThat(list).contains(exampleArt)

    }
    @Test
    fun deleteArtTesting ()  = runBlockingTest{


        val exampleArt = Art ("Mona Lisa" , "Da Vinci", 1800 , ".com" , 555)

        dao.insertArt(exampleArt)
        dao.deleteArts(exampleArt)
        val list = dao.observeArts().getOrAwaitValue()

        assertThat(list).doesNotContain(exampleArt)
    }

}