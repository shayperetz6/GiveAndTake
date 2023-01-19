package com.android.giveandtake.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GiveAndTakeApi {

    private Api api;

    private static final String baseUrl = "http://192.168.43.123:3000/api/";
    private static GiveAndTakeApi instance;

    //Singleton instance
    public static GiveAndTakeApi getInstance() {
        if (instance == null) {
            instance = new GiveAndTakeApi();
            instance.createApi();
        }
        return instance;
    }

    private void createApi(){
        //Create gson converter
        Gson gson = new GsonBuilder().create();

        //Create logger for requests and response (print in console)
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        //Create retrofit as web services
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        //Create Api class for all api requests using retrofit engine(web service)
        api = retrofit.create(Api.class);
    }

    public Api getApi() {
        return api;
    }
}
