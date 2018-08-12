package com.baking.srikar.justbaking.Network;

import android.util.Log;

import com.baking.srikar.justbaking.Config.ConfigURL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {


    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ConfigURL.BaseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Log.v("BaseURL", ConfigURL.BaseURL );
        }
        return retrofit;
    }
}
