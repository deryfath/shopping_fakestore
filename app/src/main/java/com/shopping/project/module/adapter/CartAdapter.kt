package com.shopping.project.module.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shopping.project.R
import com.shopping.project.data.model.Product
import com.shopping.project.data.model.StokBarangResponseItem
import com.shopping.project.module.viewmodel.MainViewModel
import kotlinx.android.extensions.LayoutContainer
import kotlin.text.toIntOrNull

class CartAdapter( var items:MutableList<Product>, private val context: Context) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vh = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false))

        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view=holder.itemView
        val data=items[position]

        view.let {
            it.visibility = View.VISIBLE


            (it.findViewById(R.id.tv_title) as TextView).text = data.title
            (it.findViewById(R.id.tv_price) as TextView).text = data.price.toString()
            val editText = it.findViewById<EditText>(R.id.editTextQuantity)
            editText.setText(data.quantity.toString())

            (it.findViewById(R.id.buttonDecrease) as Button).setOnClickListener {
                val currentQuantity = editText.text.toString().toIntOrNull() ?: 0
                if (currentQuantity > 1) {
                    editText.setText((currentQuantity - 1).toString())
                }
            }

            (it.findViewById(R.id.buttonIncrease) as Button).setOnClickListener {
                val currentQuantity = editText.text.toString().toIntOrNull() ?: 0
                editText.setText((currentQuantity + 1).toString())
            }
            Glide.with(context).load(data.image).into(it.findViewById(R.id.iv_product))


        }



//        view.setOnClickListener {
//            val intent = Intent(context,DetailActivity::class.java)
//            intent.putExtra("stok_id",data.id)
//            context.startActivity(intent)
//        }

    }

    fun updateData(list:MutableList<Product>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }



    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {


    }
}