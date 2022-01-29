package com.app.matrixmvvmtest.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.matrixmvvmtest.R
import com.app.matrixmvvmtest.data.model.Fruit
import com.app.matrixmvvmtest.data.repository.FruitSelected
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MainAdapter(private val fruits: ArrayList<Fruit>, private val onFruitSelected: FruitSelected) : RecyclerView.Adapter<MainAdapter.DataViewHolder>(){

    inner class DataViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        private lateinit var text: TextView;
        private lateinit var imageView: ImageView;
        private lateinit var fruit: Fruit

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(fruit: Fruit){
            this.fruit = fruit
            this.text = itemView.findViewById(R.id.textViewName)
            this.imageView = itemView.findViewById(R.id.image)

            text.text = fruit.name
            Glide.with(imageView.context)
                .load(fruit.image)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)
        }

        override fun onClick(p0: View?) {
           onFruitSelected.onFruitSelected(this.fruit)
        }
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) = holder.bind(fruits[position])

    override fun getItemCount(): Int = fruits.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
         return DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout, parent, false)
        )
    }

    fun addData(list: List<Fruit>){
        fruits.addAll(list)
    }

}