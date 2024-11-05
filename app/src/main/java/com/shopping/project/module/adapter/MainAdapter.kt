package com.shopping.project.module.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shopping.project.Utils.formatDate
import com.shopping.project.Utils.isDateExpired
import com.shopping.project.R
import com.shopping.project.Utils.orNull
import com.shopping.project.data.model.StokBarangResponseItem
import com.shopping.project.module.DetailActivity
import kotlinx.android.extensions.LayoutContainer

class MainAdapter (private val context: Context) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var items:MutableList<StokBarangResponseItem> = mutableListOf()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vh = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_stok, parent, false))

        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view=holder.itemView
        val data=items[position]

        view.let {
            it.visibility = View.VISIBLE

            (it.findViewById(R.id.tv_title) as TextView).text = data.title.orEmpty()
            (it.findViewById(R.id.tv_price) as TextView).text = data.price.toString()
            (it.findViewById(R.id.tv_rating) as TextView).text = data.rating.rate.toString()+"/"+data.rating.count

            Glide.with(context).load(data.image).into(it.findViewById(R.id.iv_product))

        }

        view.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("productId",data.id)
            context.startActivity(intent)
        }

    }

    fun addItems(list:MutableList<StokBarangResponseItem>){
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun updateItems(list:MutableList<StokBarangResponseItem>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }



    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {


    }
}