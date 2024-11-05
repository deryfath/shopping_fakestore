package com.shopping.project.data.api

import com.shopping.project.data.model.CartListResponse
import com.shopping.project.data.model.ListCategoryResponse
import com.shopping.project.data.model.StokBarangResponse
import com.shopping.project.data.model.StokBarangResponseItem
import retrofit2.http.GET
import kotlinx.coroutines.Deferred
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("/products")
    fun getListStokBarang(
    ):Deferred<StokBarangResponse>

    @GET("/products/categories")
    fun getListKategori(
    ):Deferred<ListCategoryResponse>

    @GET("/products/category/{category}")
    fun getListByKategori(
        @Path("category") category:String
    ):Deferred<StokBarangResponse>

    @GET("/products/{id}")
    fun getProductById(
        @Path("id") id:Int
    ):Deferred<StokBarangResponseItem>

    @GET("/carts/2")
    fun getCartList(
    ):Deferred<CartListResponse>
}