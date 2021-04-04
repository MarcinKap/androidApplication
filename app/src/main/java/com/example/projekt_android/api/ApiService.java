package com.example.projekt_android.api;

import com.example.projekt_android.model.News;
import com.example.projekt_android.model.SavingsIdea;
import com.example.projekt_android.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/restNews")
    Observable<Response<List<News>>> getNews();

    @GET("/users")
    Observable<Response<List<News>>> getUsers(); //nie wiem czy dziala

    @GET("/readnews")
    Observable<Response<News>> getNewsToRead(@Query("id") Long id);

    @POST("/sendQuestionRest")
    Call<Void> sendQuestion(
            @Query("nameAndSurname") String nameAndSurname,
            @Query("senderEmail") String senderEmail,
            @Query("subject") String  topic,
            @Query("textMsg")  String textMsg);

    @POST("/rest/sendQuestionRestForLoggedUser")
    Call<Void> sendQuestionForLoggedUser(
            @Header("authorization") String authHeader,
            @Query("senderEmail") String senderEmail,
            @Query("subject") String  topic,
            @Query("textMsg")  String textMsg);


    @GET("/getSavingsIdeasRest")
    Observable<Response<List<SavingsIdea>>> getSavingsIdeas();


    @GET("/restLogIn")
    Observable<Response<User>> logIn(
            @Query("email") String email,
            @Query("password") String password);

    @GET("/rest/news")
    Observable<Response<List<News>>> getNewsForLoggedUser(@Header("authorization") String authHeader);

    @POST("/rest/sendRating")
    Call<Void> sendSavingsIdeaRating(
            @Header("authorization") String authHeader,
            @Query("ratingValue") int  ratingValue,
            @Query("savingsIdeaId") Long savingsIdeaId,
            @Query("senderId") Long senderId
            );

}
