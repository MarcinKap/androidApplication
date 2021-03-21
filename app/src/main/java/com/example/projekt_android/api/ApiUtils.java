package com.example.projekt_android.api;


import retrofit2.Response;

public class ApiUtils {

    public static ApiService getApiService(){
        return ApiClient.getClient().create(ApiService.class);
    }

    public static int getResponseStatusCode(Response response){
        if (response == null){
            return 404;
        }

        return response.code();
    }


}
