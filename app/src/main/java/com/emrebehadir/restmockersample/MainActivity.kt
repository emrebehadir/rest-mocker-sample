package com.emrebehadir.restmockersample

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.emrebehadir.restmockerlib.RestMocker
import com.emrebehadir.restmockerlib.RestMockerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    var retrofit = generateClient()

    val callback = object  : Callback<ApiResponse>{
        override fun onFailure(call: Call<ApiResponse>, t: Throwable) {}

        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
            findViewById<TextView>(R.id.user_id).text = "User id : " + response?.body()?.userId.toString()
            findViewById<TextView>(R.id.id).text = "Id : " +  response?.body()?.id.toString()
            findViewById<TextView>(R.id.title).text = "Title : " + response?.body()?.title
            findViewById<TextView>(R.id.completed).text = "Complate Status : " + response?.body()?.completed.toString()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RestMocker.initialize(resources)

        findViewById<Button>(R.id.get_api_response).setOnClickListener {
            val call :Call<ApiResponse> = retrofit.getTodoFromApi()
            call.enqueue(callback)
        }

        findViewById<Button>(R.id.get_mock_response).setOnClickListener {
            val call :Call<ApiResponse> = retrofit.getTodoFromMock()
            call.enqueue(callback)
        }
    }


    private fun generateClient(): RestApi {
        val httpClient = OkHttpClient.Builder()
                .addInterceptor(RestMockerInterceptor())
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build()

        val api = Retrofit.Builder()
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build()
                .create(RestApi::class.java)

        return api
    }
}

