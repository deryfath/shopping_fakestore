package com.shopping.project.module.viewmodel

import androidx.lifecycle.MutableLiveData
import com.shopping.project.data.DataRepository
import com.shopping.project.data.model.CartListResponse
import com.shopping.project.data.model.ListCategoryResponse
import com.shopping.project.data.model.StokBarangResponse
import com.shopping.project.data.model.StokBarangResponseItem
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DataRepository):AbstractViewModel() {

    val stokLiveData = MutableLiveData<StokBarangResponse>()
    val kategoriLiveData = MutableLiveData<ListCategoryResponse>()
    val productDetailLiveData = MutableLiveData<StokBarangResponseItem>()
    val cartListLiveData = MutableLiveData<CartListResponse>()

    fun getListStokBarang(curPage: Int, pageSize: Int = 10){
        launch {
            try {
                setLoading()
                val dataProduct = repository.getListStokBarang(curPage, pageSize)
                stokLiveData.postValue(dataProduct)

            }catch (t:Throwable){
                println("ERROR COROUTINE "+t.message)

                setError(t)
            }finally {
                setLoading(false)
            }
        }
    }

    fun getListByCategory(category: String){
        launch {
            try {
                setLoading()
                val dataProduct = repository.getListByKategori(category)
                stokLiveData.postValue(dataProduct)

            }catch (t:Throwable){
                println("ERROR COROUTINE "+t.message)

                setError(t)
            }finally {
                setLoading(false)
            }
        }
    }

    fun getListKategori(){
        launch {
            try {
                setLoading()
                val listKategory = repository.getListKategori()
                kategoriLiveData.postValue(listKategory)

            }catch (t:Throwable){
                println("ERROR COROUTINE "+t.message)

                setError(t)
            }finally {
                setLoading(false)
            }
        }
    }

    fun getProductById(id: Int){
        launch {
            try {
                setLoading()
                val dataProductDetail = repository.getProductById(id)
                productDetailLiveData.postValue(dataProductDetail)

            }catch (t:Throwable){
                println("ERROR COROUTINE "+t.message)

                setError(t)
            }finally {
                setLoading(false)
            }
        }
    }

    fun getCartList(){
        launch {
            try {
                setLoading()
                val dataCartList = repository.getCartList()
                cartListLiveData.postValue(dataCartList)

            }catch (t:Throwable){
                println("ERROR COROUTINE "+t.message)

                setError(t)
            }finally {
                setLoading(false)
            }
        }
    }
}