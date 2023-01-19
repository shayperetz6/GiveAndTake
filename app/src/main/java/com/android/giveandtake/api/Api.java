package com.android.giveandtake.api;

import com.android.giveandtake.home.Post;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

//Retrofit api requests
public interface Api {

    @POST("post/")
    Call<Void> createPost(@Body() Post post);
}
