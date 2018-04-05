package com.dominik.akilikickerliga.adapter

import com.dominik.akilikickerliga.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface  API
{
	@POST("/login")
	fun login(@Body name: String): Call<User>

	@GET("/users")
	fun getUsers(): Call<List<User>>;

	@GET
	fun getUser(@Url url: String): Call<User>

	@POST("/users")
	fun createUser(@Body user: User): Call<Boolean>;
}