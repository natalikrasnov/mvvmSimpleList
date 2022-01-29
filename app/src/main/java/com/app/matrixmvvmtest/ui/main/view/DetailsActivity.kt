package com.app.matrixmvvmtest.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.app.matrixmvvmtest.R
import com.app.matrixmvvmtest.data.api.ApiHelper
import com.app.matrixmvvmtest.data.api.ApiServiceImpl
import com.app.matrixmvvmtest.data.model.Fruit
import com.app.matrixmvvmtest.ui.base.ViewModelFactory
import com.app.matrixmvvmtest.ui.main.viewmodel.MainViewModel
import com.app.matrixmvvmtest.utils.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson


class DetailsActivity: AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_detail_layout)
        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI(){
        val actionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        val serializable = intent.getSerializableExtra("fruit")
        Log.i("TAG", "setupUI: $serializable")
        val fruit = Gson().fromJson(serializable.toString(), Fruit::class.java)
        if (fruit != null) {
            updateUI(fruit)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        onBackPressed()
        return true
    }

    private fun setupViewModel(){
        mainViewModel = ViewModelProviders
            .of(this, ViewModelFactory(ApiHelper(ApiServiceImpl())))
            .get(MainViewModel::class.java)
    }

    private fun setupObserver(){
        mainViewModel.getSelectedFruit().observe (this,{
            when(it.status) {
                Status.SUCCESS -> {
                    it.data?.let {fruit -> updateUI(fruit)}
                }
            }
        })
    }

    private fun updateUI(fruit: Fruit){
        val name: TextView = findViewById(R.id.textViewName)
        val description: TextView = findViewById(R.id.textViewDescription)
        val price: TextView = findViewById(R.id.textViewPrice)
        val imageView: ImageView = findViewById(R.id.imageView)

        name.text = fruit.name
        name.visibility = View.VISIBLE
        description.text = fruit.description
        description.visibility = View.VISIBLE
        price.text = fruit.price.toString()
        price.visibility = View.VISIBLE
        Glide.with(imageView.context)
            .load(fruit.image)
            .apply(RequestOptions.circleCropTransform())
            .into(imageView)
    }

}