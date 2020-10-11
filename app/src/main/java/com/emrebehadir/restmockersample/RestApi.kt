package com.emrebehadir.restmockersample

import com.emrebehadir.restmockerlib.annotations.Mocker
import retrofit2.Call
import retrofit2.http.GET

interface RestApi {

    @Mocker(R.raw.todo)
    @GET("/todos/1")
    fun getTodoFromMock(): Call<ApiResponse>

    @GET("/todos/1")
    fun getTodoFromApi(): Call<ApiResponse>
}

