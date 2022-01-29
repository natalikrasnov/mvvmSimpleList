package com.app.matrixmvvmtest.data.api

import com.app.matrixmvvmtest.data.model.Fruit

interface ApiService {
    fun getFruits(): List<Fruit>
}