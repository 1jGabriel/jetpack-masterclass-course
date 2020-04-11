package com.aupp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aupp.model.DogBreed
import com.aupp.model.DogsApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListViewModel : ViewModel() {
    private val dogsService = DogsApiService()
    private val disposable = CompositeDisposable()
    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchFromRemote()
    }

    private fun fetchFromRemote() {
        disposable.add(
            dogsService.getDogs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loading.value = true }
                .doFinally { loading.value = false }
                .subscribe({
                    dogs.value = it
                }, {
                    dogsLoadError.value = true
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}