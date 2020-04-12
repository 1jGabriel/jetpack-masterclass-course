package com.aupp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.aupp.model.DogDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    private val job = Job()

    fun dogDao() = DogDatabase(getApplication()).dogDao()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}