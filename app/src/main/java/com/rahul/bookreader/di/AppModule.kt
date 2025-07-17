package com.rahul.bookreader.di

import androidx.room.Insert
import com.google.firebase.firestore.FirebaseFirestore
import com.rahul.bookreader.network.BooksAPI
import com.rahul.bookreader.repository.BookRepository
import com.rahul.bookreader.repository.FireRepository
import com.rahul.bookreader.utils.Constants.BASEURL
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Singleton
    @Provides
    fun provideBookAPI(): BooksAPI {
        return Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksAPI::class.java)
    }


    @Singleton
    @Provides
    fun provideBookRepository(bookAPI: BooksAPI): BookRepository {
        return BookRepository(bookAPI)
    }

    @Singleton
    @Provides
    fun provideFireRepository(): FireRepository {
        return FireRepository(query = FirebaseFirestore.getInstance().collection("books"))
    }
}