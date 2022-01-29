package com.app.matrixmvvmtest.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.matrixmvvmtest.data.api.ApiHelper
import com.app.matrixmvvmtest.data.repository.MainRepository
import com.app.matrixmvvmtest.ui.main.viewmodel.MainViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(MainRepository(apiHelper)) as T

        throw IllegalArgumentException("unknown class name")
    }

}