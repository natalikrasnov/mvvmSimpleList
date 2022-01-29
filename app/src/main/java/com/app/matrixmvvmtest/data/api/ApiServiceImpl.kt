package com.app.matrixmvvmtest.data.api

import android.util.Log
import com.app.matrixmvvmtest.data.model.Fruit
import com.google.gson.GsonBuilder
import org.json.JSONObject
import java.net.URL

class ApiServiceImpl : ApiService {

    override fun getFruits(): List<Fruit> {
        return try {
            val jsonStr = URL("https://dev-api.com/fruitsBT/getFruits").readText()
            val json = JSONObject(jsonStr)
            val response = json.get("fruits").toString()
            Log.i("ApiServiceImpl", "getFruits: $response")
            val gson = GsonBuilder().create()
            gson.fromJson(response,Array<Fruit>::class.java).toList()
        }catch (e: Exception) {
            Log.e("ApiServiceImpl", "getFruits exeption: ",e )
            emptyList()
        }
    }
}