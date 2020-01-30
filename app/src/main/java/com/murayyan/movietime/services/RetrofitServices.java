package com.murayyan.movietime.services;

import com.murayyan.movietime.env.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServices {
    private static Retrofit retrofit;
    public static <S> S createService(Class<S> serviceClass) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(serviceClass);
    }

}
