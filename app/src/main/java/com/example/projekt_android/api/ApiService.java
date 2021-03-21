package com.example.projekt_android.api;

import com.example.projekt_android.Model.News;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

public interface ApiService {

    @GET("123")
    Observable<Response<List<News>>> getNews();

    @GET("/users")
    Observable<Response<List<News>>> getNews2();




}
