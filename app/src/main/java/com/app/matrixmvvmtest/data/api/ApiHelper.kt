package com.app.matrixmvvmtest.data.api

class ApiHelper(private val apiService: ApiService) {

    fun getFruits() = apiService.getFruits()
}