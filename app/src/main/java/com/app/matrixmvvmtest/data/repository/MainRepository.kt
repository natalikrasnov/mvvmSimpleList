package com.app.matrixmvvmtest.data.repository

import com.app.matrixmvvmtest.data.api.ApiHelper
import com.app.matrixmvvmtest.data.model.Fruit

class MainRepository(private val apiHelper: ApiHelper) {

    fun getFruits(): List<Fruit> {
        return apiHelper.getFruits()
    }
}