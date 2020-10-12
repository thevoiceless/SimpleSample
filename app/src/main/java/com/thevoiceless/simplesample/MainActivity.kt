package com.thevoiceless.simplesample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.squareup.moshi.Json
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
                .baseUrl("https://tools.learningcontainer.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        val service = retrofit.create(SampleService::class.java)

        service.getSampleJson().enqueue(object : Callback<SampleJson> {
            override fun onResponse(call: Call<SampleJson>, response: Response<SampleJson>) {
                Log.i("sample", "Success: ${response.body().toString()}")
            }

            override fun onFailure(call: Call<SampleJson>, t: Throwable) {
                Log.e("sample", "Failure: ${t.message}")
            }
        })
    }
}

data class SampleJson(
        val firstame: String,
        val lastName: String,
        val gender: Gender,
        val age: Long,
        val address: Address,
        val phoneNumbers: List<PhoneNumber>
)

enum class Gender {
    @Json(name = "man")
    Man
}

data class Address(
    val streetAddress: String,
    val city: String,
    val state: String,
    val postalCode: String
)

data class PhoneNumber(
        val type: PhoneNumberType,
        val number: String
)

enum class PhoneNumberType {
    @Json(name = "home")
    Home
}

interface SampleService {
    @GET("/sample-json.json")
    fun getSampleJson(): Call<SampleJson>
}