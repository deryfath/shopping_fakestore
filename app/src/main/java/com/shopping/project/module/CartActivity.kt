package com.shopping.project.module

import android.os.Bundle
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.shopping.project.R
import com.shopping.project.data.model.Product
import com.shopping.project.databinding.ActivityCartBinding
import com.shopping.project.module.adapter.CartAdapter
import com.shopping.project.module.viewmodel.MainViewModel

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private val mainViewModel: MainViewModel by viewModel()
    private var productList = mutableListOf<Product>()
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.getCartList()

        initObserver()
    }

    private fun initObserver() {

        mainViewModel.cartListLiveData.observe(this, {
            val data = it

            if(data.products.size > 0) {
                productList = data.products

                productList.forEach {
                    mainViewModel.getProductById(it.productId)
                }
            }

        })

        mainViewModel.productDetailLiveData.observe(this, { dataDetail ->
            productList.forEach {
                if (it.productId == dataDetail.id) {
                    it.title = dataDetail.title
                    it.image = dataDetail.image
                    it.price = dataDetail.price
                    counter++
                }
            }

            if(counter == productList.size){
                val adapter = CartAdapter( productList, this)
                val mLayoutManager = LinearLayoutManager(this)
                binding.recyclerViewCart.setLayoutManager(mLayoutManager)
                binding.recyclerViewCart.adapter = adapter
            }



        })


    }
}