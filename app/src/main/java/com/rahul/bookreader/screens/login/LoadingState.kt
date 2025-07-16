package com.rahul.bookreader.screens.login

data class LoadingState(val status: Status, val msg: String? = null) {

    companion object{
        val IDLE = LoadingState(Status.IDLE)
        val LOADING = LoadingState(Status.LOADING)
        val SUCCESS = LoadingState(Status.SUCCESS)
        val FAILED = LoadingState(Status.FAILED)
    }


    enum class Status{
        IDLE,
        LOADING,
        SUCCESS,
        FAILED
    }
}