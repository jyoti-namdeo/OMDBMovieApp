package com.example.rtwm38.myomdbapp.network;

import com.example.rtwm38.myomdbapp.model.Details;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OmdbApi {
    @GET("?type=movie")
    Call<SearchService.Result> Result(
            @Query("s") String Title);

    @GET("?plot=full")
    Call<Details> Detail(
            @Query("i") String ImdbId);
}