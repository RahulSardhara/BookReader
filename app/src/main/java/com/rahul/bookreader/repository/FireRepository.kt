package com.rahul.bookreader.repository

import com.google.firebase.firestore.Query
import com.rahul.bookreader.data.DataOrException
import com.rahul.bookreader.model.MBook
import com.rahul.bookreader.network.BooksAPI
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.jvm.java

class FireRepository @Inject constructor(private val query: Query)  {


    suspend fun getAllBooksFromDataBase(): DataOrException<List<MBook>, Boolean, Exception> {
        val dataOrException = DataOrException<List<MBook>, Boolean, Exception>()
        try {
            val books = query.get().await().documents.map { documentSnapshot ->
                documentSnapshot.toObject(MBook::class.java) ?: MBook()
            }
            dataOrException.data = books
            dataOrException.loading = false
        } catch (e: Exception) {
            dataOrException.e = e
            dataOrException.loading = false
        }
        return dataOrException
    }

}