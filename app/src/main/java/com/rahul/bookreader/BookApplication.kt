package com.rahul.bookreader

import android.app.Application
import androidx.compose.runtime.Applier
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BookApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}