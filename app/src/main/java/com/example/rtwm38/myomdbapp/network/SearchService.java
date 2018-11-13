package com.example.rtwm38.myomdbapp.network;

import com.example.rtwm38.myomdbapp.model.Details;
import com.example.rtwm38.myomdbapp.model.Movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import util.Config;

public class SearchService {

    private static OmdbApi sOmdbApi;

    public static class ResultWithDetail {
        private List<Details> movieDetailList;
        private String totalResults;
        private String Response;

        public ResultWithDetail(Result result) {
            this.totalResults = result.totalResults;
            this.Response = result.Response;
            movieDetailList = new ArrayList<>();
        }

        public void addToList(Details detail) {
            movieDetailList.add(detail);
        }

        public List<Details> getMovieDetailList() {
            return movieDetailList;
        }

        public String getTotalResults() {
            return totalResults;
        }

        public String getResponse() {
            return Response;
        }
    }

    public static class Result {
        public List<Movie> Search;
        public String totalResults;
        public String Response;

        @Override
        public String toString() {
            return "Result{" +
                    "Search=" + Search +
                    ", totalResults='" + totalResults + '\'' +
                    ", Response='" + Response + '\'' +
                    '}';
        }
    }

    private static void setsOmdbApi() {
        if (sOmdbApi == null) {
            OkHttpClient.Builder httpClient =
                    new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();

                    HttpUrl url = originalHttpUrl.newBuilder()
                            .addQueryParameter("apikey", Config.API_KEY)
                            .build();

                    Request.Builder requestBuilder = original.newBuilder()
                            .url(url);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.API_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Create an instance of our OMDB API interface.
            sOmdbApi = retrofit.create(OmdbApi.class);
        }
    }

    public static Result performSearch(String title) throws IOException {
        setsOmdbApi();
        Call<Result> call = sOmdbApi.Result(title);

        return call.execute().body();
    }

    public static Details getDetail(String imdbId) throws IOException {
        setsOmdbApi();

        Call<Details> call = sOmdbApi.Detail(imdbId);

        return call.execute().body();
    }
}
