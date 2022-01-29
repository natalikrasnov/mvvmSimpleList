package com.app.matrixmvvmtest.data.repository

import com.app.matrixmvvmtest.data.model.Fruit

interface FruitSelected{
    fun onFruitSelected(fruit: Fruit)
}
