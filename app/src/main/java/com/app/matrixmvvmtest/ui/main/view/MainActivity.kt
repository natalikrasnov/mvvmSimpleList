package com.app.matrixmvvmtest.ui.main.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.matrixmvvmtest.R
import com.app.matrixmvvmtest.data.api.ApiHelper
import com.app.matrixmvvmtest.data.api.ApiServiceImpl
import com.app.matrixmvvmtest.data.model.Fruit
import com.app.matrixmvvmtest.data.repository.FruitSelected
import com.app.matrixmvvmtest.ui.base.ViewModelFactory
import com.app.matrixmvvmtest.ui.main.adapter.MainAdapter
import com.app.matrixmvvmtest.ui.main.viewmodel.MainViewModel
import com.app.matrixmvvmtest.utils.Status
import com.google.gson.Gson

class MainActivity : AppCompatActivity() , FruitSelected {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI(){
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(ArrayList(),  this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context, (recyclerView.layoutManager as LinearLayoutManager).orientation)
        )
        recyclerView.adapter = adapter
    }

    private fun setupObserver(){
        mainViewModel.getFruits().observe(this, {
            when(it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { fruits -> renderList(fruits) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun setupViewModel(){
        mainViewModel = ViewModelProviders
            .of(this, ViewModelFactory(ApiHelper(ApiServiceImpl())))
            .get(MainViewModel::class.java)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderList(fruits: List<Fruit>){
        adapter.addData(fruits)
        adapter.notifyDataSetChanged()
    }

    private fun switchToDetailView(fruit: Fruit){
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("fruit", (Gson()).toJson(fruit))
        startActivity(intent)
    }

    override fun onFruitSelected(fruit: Fruit) {
        mainViewModel.onFruitSelected(fruit)
        switchToDetailView(fruit)
    }
}