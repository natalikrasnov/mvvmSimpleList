package com.app.matrixmvvmtest.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.matrixmvvmtest.data.model.Fruit
import com.app.matrixmvvmtest.data.repository.FruitSelected
import com.app.matrixmvvmtest.data.repository.MainRepository
import com.app.matrixmvvmtest.utils.Resource
import io.reactivex.disposables.CompositeDisposable
import kotlin.concurrent.thread

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() , FruitSelected {

    private val fruits = MutableLiveData<Resource<List<Fruit>>>()
    private val compositeDisposable = CompositeDisposable()
    private val selectedFruit = MutableLiveData<Resource<Fruit>>()

    init {
        fetchFruits()
    }

    private fun fetchFruits(){
        fruits.postValue(Resource.loading(null))
        thread{
            val fruitsData = mainRepository.getFruits()
            if(fruitsData.isNotEmpty()) fruits.postValue(Resource.success(fruitsData))
            else fruits.postValue(Resource.error("error get fruits", fruitsData))
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getFruits(): LiveData<Resource<List<Fruit>>> {
        return fruits
    }

    fun getSelectedFruit(): LiveData<Resource<Fruit>> {
        return selectedFruit
    }

    override fun onFruitSelected(fruit: Fruit) {
        selectedFruit.postValue(Resource.success(fruit))
    }
}