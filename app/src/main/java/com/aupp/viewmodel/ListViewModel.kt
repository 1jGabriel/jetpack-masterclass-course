package com.aupp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.aupp.model.DogBreed
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
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L //5 minutes in nanosecods
    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()

    fun refresh() {
        val updateTime = preferencesHelper.getUpdateTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            fetchFromDataBase()
        } else {
            fetchFromRemote()
        }
    }

    fun refreshBypassCache() {
        fetchFromRemote()
    }

    private fun fetchFromDataBase() {
        loading.value = true
        launch {
            val dogs = dogDao().getAllDogs()
            onDogsSuccess(dogs)
            loading.value = false
        }
    }

    val loading = MutableLiveData<Boolean>()

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
            dogDao().deleteAllDogs()

            val result = dogDao().insertAll(*list.toTypedArray())

            var i = 0
            while (i < list.size) {
                list[i].uuid = result[i].toInt()
                ++i
            }
            onDogsSuccess(list)
        }

        preferencesHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}