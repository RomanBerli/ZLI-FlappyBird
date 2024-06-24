package com.example.ueben1.retrofit;

import com.google.gson.Gson;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private Retrofit retrofit;

    public RetrofitService(){
        initializeRetrofit();
    }

    private void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000") //Url
                .addConverterFactory(GsonConverterFactory.create(new Gson())) //JSON übersetzer
                .build();


    }
    public Retrofit getRetrofit(){
        return retrofit;
    }

}
