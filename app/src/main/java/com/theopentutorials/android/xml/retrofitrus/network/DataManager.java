package com.theopentutorials.android.xml.retrofitrus.network;

import com.theopentutorials.android.xml.retrofitrus.network.res.weather.Forecasts;

import retrofit2.Call;


public class DataManager {

    private static DataManager INSTANCE = null;
    private RestService mRestService;

    public DataManager() {
        this.mRestService = ServiceGenerator.createService(RestService.class);
    }

    public static DataManager getInstance(){
        if(INSTANCE==null){
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

    public Call<Forecasts> getForcasts (){
        return mRestService.getForcasts();
    }
}
