package com.theopentutorials.android.xml.retrofitrus.network;

import com.theopentutorials.android.xml.retrofitrus.network.res.weather.Forecasts;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface RestService {

    @GET("ilma_andmed/xml/forecast.php")
    Call<Forecasts> getForcasts();
}
