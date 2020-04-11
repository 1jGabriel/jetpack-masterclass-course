package com.aupp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aupp.model.DogBreed

class ListViewModel : ViewModel() {
    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        val dogList = arrayListOf(
            DogBreed("1", "Corgi", "15 years", "1", "1", "1", "1"),
            DogBreed("2", "Labrador", "10 years", "1", "1", "1", "1"),
            DogBreed("3", "PitBull", "3 years", "1", "1", "1", "1")
        )

        dogs.value = dogList

        dogsLoadError.value = false
        loading.value = false
    }
}