package com.example.projekt_android.api;

import com.example.projekt_android.Model.News;
import com.example.projekt_android.Model.SavingsIdea;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("123")
    Observable<Response<List<News>>> getNews();

    @GET("/users")
    Observable<Response<List<News>>> getNews2();

    @GET("/readnews")
    Observable<Response<News>> getNewsToRead(@Query("id") Long id);

    @POST("/sendMessageRest")
    Call<Void> sendQuestion(
            @Query("nameAndSurname") String nameAndSurname,
            @Query("senderEmail") String senderEmail,
            @Query("subject") String  topic,
            @Query("textMsg")  String textMsg);

    @GET("/getSavingsIdeasRest")
    Observable<Response<List<SavingsIdea>>> getSavingsIdeas();


}
