package com.rahul.bookreader.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahul.bookreader.data.DataOrException
import com.rahul.bookreader.model.MBook
import com.rahul.bookreader.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val fireRepository: FireRepository) : ViewModel() {

    val data: MutableState<DataOrException<List<MBook>, Boolean, Exception>> = mutableStateOf(DataOrException(listOf(), true, Exception("")))

    init {
        getAllBooksFromDataBase()
    }

    fun getAllBooksFromDataBase(){
        viewModelScope.launch {
            data.value.loading =true
            data.value= fireRepository.getAllBooksFromDataBase()
            if(data.value.data.toString().isNotEmpty()) data.value.loading=false
        }
    }

}