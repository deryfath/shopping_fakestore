package com.shopping.project.module

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.shopping.project.R
import com.shopping.project.databinding.ActivityDetailBinding
import com.shopping.project.module.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val myViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productId = intent.getIntExtra("productId", -1)
        myViewModel.getProductById(productId)
        initObserver()


    }

    private fun initObserver(){
        myViewModel.productDetailLiveData.observe(this, {
            // Set data to views
            Glide.with(this).load(it.image).into(binding.imageViewProduct)
            binding.textViewTitle.text = it.title
            binding.textViewDescription.text = it.description
            binding.textViewPrice.text = it.price.toString()
            binding.ratingBar.rating = it.rating.rate.toFloat()

            binding.buttonAddToCart.setOnClickListener {
                // Handle add to cart logic
            }
        })
    }
}