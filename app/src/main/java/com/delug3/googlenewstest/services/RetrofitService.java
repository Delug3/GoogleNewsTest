package com.delug3.googlenewstest.services;

import com.delug3.googlenewstest.responses.ArticlesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {

    //http://newsapi.org/v2/top-headlines?country=us&apiKey=81c3f7715c26467d8dfad243c02bc988

    String API_KEY = "81c3f7715c26467d8dfad243c02bc988";

    @GET("top-headlines")
    Call<ArticlesResponse> obtainArticlesList(@Query("country")String country, @Query("apiKey")String apiKey);

}
