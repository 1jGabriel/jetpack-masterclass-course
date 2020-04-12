package com.aupp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.aupp.model.DogBreed
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {
    val dog = MutableLiveData<DogBreed>()

    fun getDog(uid: Int) {
        launch {
            val dogResult = dogDao().getDog(uid)
            dog.value = dogResult
        }
    }
}