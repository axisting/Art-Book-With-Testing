package com.axisting.artbookwithtesting.dependencyinjection

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.axisting.artbookwithtesting.roomdb.ArtDao
import com.axisting.artbookwithtesting.roomdb.ArtDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {




    @Singleton
    @Provides
    @Named("testDatabase")
    fun injectInMemoryRoom(@ApplicationContext context : Context)  : ArtDatabase {
        return Room.inMemoryDatabaseBuilder(context,ArtDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }



}