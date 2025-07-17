package com.rahul.bookreader.network

import com.rahul.bookreader.model.Book
import com.rahul.bookreader.model.Item
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BooksAPI {

     @GET("volumes")
     suspend fun getAllBooks(@Query("q") query: String): Response<Book>


    @GET("volumes/{bookId}")
    suspend fun getBookInfo(@Path("bookId") bookId: String): Response<Item>

}