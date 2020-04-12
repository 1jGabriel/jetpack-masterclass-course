package com.aupp.view

import android.view.View
import com.aupp.model.DogBreed

interface DogClickListener {
    fun onDogClicked(dog: DogBreed, view: View)
}