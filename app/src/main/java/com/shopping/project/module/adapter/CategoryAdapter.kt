package com.shopping.project.module.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shopping.project.Utils.formatDate
import com.shopping.project.Utils.isDateExpired
import com.shopping.project.R
import com.shopping.project.Utils.orNull
import com.shopping.project.data.model.StokBarangResponseItem
import com.shopping.project.module.DetailActivity
import kotlinx.android.extensions.LayoutContainer

class CategoryAdapter(
    private var items: MutableList<String>,
    private val context: Context,
    private val onButtonClick: (String) -> Unit = { _ -> },
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var activePosition: Int = -1
    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vh = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false))

        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view=holder.itemView
        val data=items[position]

        view.let {
            it.visibility = View.VISIBLE

            (it.findViewById(R.id.tv_category) as TextView).text = data.toString()


        }

        if (position == activePosition) {
            view.findViewById<CardView>(R.id.cv_category).setCardBackgroundColor(ContextCompat.getColor(context, R.color.reddish_orange))
            view.findViewById<TextView>(R.id.tv_category).setTextColor(ContextCompat.getColor(context, R.color.white))
        } else {
            view.findViewById<CardView>(R.id.cv_category).setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
            view.findViewById<TextView>(R.id.tv_category).setTextColor(ContextCompat.getColor(context, R.color.black))

        }

        view.setOnClickListener {
            activePosition = if (activePosition == position) {
                -1
            } else {
                position
            }
            onButtonClick(data)
            notifyDataSetChanged()
        }

    }



    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {


    }
}