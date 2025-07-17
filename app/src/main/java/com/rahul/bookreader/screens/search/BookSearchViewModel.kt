package com.rahul.bookreader.screens.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahul.bookreader.data.DataOrException
import com.rahul.bookreader.model.Item
import com.rahul.bookreader.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(private val bookRepository: BookRepository) : ViewModel(){

    var listOfBooks : MutableState< DataOrException<List<Item>, Boolean, Exception>> =
        mutableStateOf( DataOrException(null,true, Exception("")))

    init {

        getAllBooks("android")
    }

    fun getAllBooks(bookType: String?) {
        bookType?.let {
            viewModelScope.launch {
                listOfBooks.value.loading = true
                listOfBooks.value = bookRepository.getAllBooks(bookType)
                listOfBooks.value.loading = false
                if(listOfBooks.value.data.toString().isEmpty()) listOfBooks.value.e = Exception("No Books Found")
            }
        }

    }

}