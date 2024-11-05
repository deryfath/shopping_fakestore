package com.shopping.project.data

import com.shopping.project.data.api.ApiService
import com.shopping.project.data.model.CartListResponse
import com.shopping.project.data.model.ListCategoryResponse
import com.shopping.project.data.model.StokBarangResponse
import com.shopping.project.data.model.StokBarangResponseItem

class DataRepository(private val apiDataRepository: ApiService) {

    suspend fun getListStokBarang(curPage: Int, pageSize: Int): StokBarangResponse {
        val data = apiDataRepository.getListStokBarang().await()
        println("datana $data")
        return  data
    }

    suspend fun getListKategori(): ListCategoryResponse {
        val data = apiDataRepository.getListKategori().await()
        println("datana $data")
        return  data
    }

    suspend fun getListByKategori(category: String): StokBarangResponse {
        val data = apiDataRepository.getListByKategori(category).await()
        println("datana $data")
        return  data
    }

    suspend fun getProductById(id: Int): StokBarangResponseItem {
        val data = apiDataRepository.getProductById(id).await()
        println("datana $data")
        return  data
    }

    suspend fun getCartList(): CartListResponse {
        val data = apiDataRepository.getCartList().await()
        println("datana $data")
        return  data
    }

}