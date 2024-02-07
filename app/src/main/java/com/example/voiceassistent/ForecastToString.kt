package com.example.voiceassistent

import android.util.Log
import java.util.function.Consumer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastToString {
    fun getForecast(city: String?, callback: (String) -> Unit) {
        val api: ForecastApi? = ForecastService().getApi()
        val call: Call<Forecast?>? = api?.getCurrentWeather(city)

        call!!.enqueue(object : Callback<Forecast?> {
            override fun onResponse(call: Call<Forecast?>, response: Response<Forecast?>) {
                val result = response.body()
                if (result != null) {
                    val answer =
                        "Сейчас где-то ${result.main?.temp} градуса и ${result.weather?.get(0)?.description}"
                    callback.invoke(answer)
                } else {
                    callback.invoke("Не могу узнать погоду")
                }
            }

            override fun onFailure(call: Call<Forecast?>, t: Throwable) {
                Log.w("WEATHER", t.message.toString())
            }
        })
    }
}