package com.example.retrofitpost.network.service

import com.example.retrofitpost.network.model.MyPost
import com.example.retrofitpost.network.model.MyTodo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("rejalar")
    fun getallTodo(): Call<List<MyTodo>>

    @POST("rejalar/")
    fun createTodo(@Body myPost: MyPost): Call<MyTodo>


    @PUT("rejalar/{id}/")
    fun updateTodo(@Path("id") id: Int, @Body todo: MyTodo): Call<MyTodo>

    @DELETE("rejalar/{id}/")
    fun deleteTodo(@Path("id") id: Int): Call<Void>
}