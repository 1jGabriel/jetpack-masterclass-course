package com.aupp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.aupp.model.DogBreed
import com.aupp.model.DogDatabase
import com.aupp.model.DogsApiService
import com.aupp.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : BaseViewModel(application) {
    private val dogsService = DogsApiService()
    private val disposable = CompositeDisposable()
    private var preferencesHelper = SharedPreferencesHelper(getApplication())
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
                    storeDogsLocally(it)
                }, {
                    dogsLoadError.value = true
                })
        )
    }

    private fun onDogsSuccess(dogs: List<DogBreed>?) {
        this.dogs.value = dogs
    }

    private fun storeDogsLocally(list: List<DogBreed>) {
        launch {
            dogDatabase().dogDao().deleteAllDogs()

            val result = dogDatabase().dogDao().insertAll(*list.toTypedArray())

            var i = 0
            while (i < list.size) {
                list[i].uuid = result[i].toInt()
                ++i
            }
            onDogsSuccess(list)
        }

        preferencesHelper.saveUpdateTime(System.nanoTime())
    }

    private fun dogDatabase() = DogDatabase(getApplication())

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}