package com.app.matrixmvvmtest.data.model
import com.google.gson.annotations.SerializedName

data class Fruit(
    @SerializedName("image") val image: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("price") val price: Double = 0.0
)