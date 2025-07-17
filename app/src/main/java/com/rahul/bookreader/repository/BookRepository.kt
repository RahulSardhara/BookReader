package com.rahul.bookreader.repository

import com.rahul.bookreader.data.DataOrException
import com.rahul.bookreader.model.Item
import com.rahul.bookreader.network.BooksAPI
import javax.inject.Inject

class BookRepository @Inject constructor(private val bookAPI: BooksAPI) {

    suspend fun getAllBooks(query: String): DataOrException<List<Item>, Boolean, Exception> {
        val dataOrException = DataOrException<List<Item>, Boolean, Exception>()
        try {
            dataOrException.data = bookAPI.getAllBooks(query).body()?.items
            dataOrException.loading = true
        } catch (e: Exception) {
            dataOrException.e = e
            dataOrException.loading = false
        }
        return dataOrException
    }

    suspend fun getBookInfo(bookId: String): DataOrException<Item, Boolean, Exception> {
        val dataOrException = DataOrException<Item, Boolean, Exception>()
        try {
            dataOrException.data = bookAPI.getBookInfo(bookId).body()
            dataOrException.loading = true
        } catch (e: Exception) {
            dataOrException.e = e
            dataOrException.loading = false
        }
        return dataOrException
    }

}