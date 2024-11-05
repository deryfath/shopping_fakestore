package com.shopping.project.module

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.shopping.project.R
import com.shopping.project.Utils.EndlessScrollListener
import com.shopping.project.Utils.gone
import com.shopping.project.Utils.observe
import com.shopping.project.Utils.visible
import com.shopping.project.module.adapter.MainAdapter
import com.shopping.project.module.viewmodel.MainViewModel
import com.shopping.project.databinding.ActivityMainBinding
import com.shopping.project.module.adapter.CategoryAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val myViewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainAdapter
    private var currentPage = 1;
    private val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myViewModel.getListStokBarang(currentPage)
        myViewModel.getListKategori()

        adapter = MainAdapter( this)
        val mLayoutManager = GridLayoutManager(this, 2)
        binding.rvStok.setLayoutManager(mLayoutManager)
        binding.rvStok.adapter = adapter

        initObserver()
        setSupportActionBar(binding.toolbar)
    }

    private fun initObserver() {

        binding.rvStok.addOnScrollListener(loadScrollData())

        myViewModel.stokLiveData.observe(this, {
            val data = it?.toMutableList() ?: mutableListOf()
            if(data.size > 0) {
                binding.rvStok.visible()
                adapter.updateItems(data)

            }else{
                binding.rvStok.gone()
            }
        })

        myViewModel.isDataLoading.observe(this, {
            if (it == true){
                println("LOADING")
                binding.pbMain.visible()
            }else{
                handler.postDelayed(Runnable {
                    binding.pbMain.gone()

                }, 3000)
            }
        })

        myViewModel.kategoriLiveData.observe(this, {
            val data = it?.toMutableList() ?: mutableListOf()
            data.add(0,"All")
            println("KATEGORI "+data)
            if(data.size > 0) {
                val categoryAdapter = CategoryAdapter(data, this){
                    if(it.equals("All")){
                        myViewModel.getListStokBarang(currentPage)
                    }else{
                        myViewModel.getListByCategory(it)
                    }
                }
                val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                binding.rvCategory.setLayoutManager(mLayoutManager)
                binding.rvCategory.adapter = categoryAdapter
            }

        })

    }

    private fun loadScrollData():EndlessScrollListener{

        return object : EndlessScrollListener(){
            override fun onLoadMore() {
                currentPage++
                myViewModel.getListStokBarang(currentPage)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this@MainActivity, CartActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
