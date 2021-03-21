package com.example.projekt_android.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    static Retrofit getClient(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("Connection", "close")
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        });
        return new Retrofit.Builder()
//                .baseUrl("https://jsonplaceholder.typicode.com")
                .baseUrl("http://192.168.1.64:8081")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

    }



}
