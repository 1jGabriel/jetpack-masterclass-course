package com.aupp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aupp.model.DogBreed

class DetailViewModel : ViewModel() {
    val dog = MutableLiveData<DogBreed>()

    fun getDog() {
        dog.value = DogBreed(
            "1",
            "Corgi",
            "15 years",
            "1",
            "1",
            "1",
            "1"
        )
    }
}