package com.example.rtwm38.myomdbapp.network;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.rtwm38.myomdbapp.model.Movie;

import java.io.IOException;

public class RetrofitLoader extends AsyncTaskLoader<SearchService.ResultWithDetail> {

    private static final String LOG_TAG = "RetrofitLoader";
    private final String mTitle;
    private SearchService.ResultWithDetail mData;

    public RetrofitLoader(Fragment context, String title) {
        super(context.getContext());
        mTitle = title;
    }

    @Override
    public SearchService.ResultWithDetail loadInBackground() {
        try {
            SearchService.Result result =  SearchService.performSearch(mTitle);
            SearchService.ResultWithDetail resultWithDetail = new SearchService.ResultWithDetail(result);
            if(result.Search != null) {
                for(Movie movie: result.Search) {
                    resultWithDetail.addToList(SearchService.getDetail(movie.imdbID));
                }
            }
            return  resultWithDetail;
        } catch(final IOException e) {
            Log.e(LOG_TAG, "Error from api access", e);
        }
        return null;
    }

    @Override
    protected void onStartLoading() {
        if (mData != null) {
            deliverResult(mData);
        } else {
            forceLoad();
        }
    }

    @Override
    protected void onReset() {
        Log.d(LOG_TAG, "onReset");
        super.onReset();
        mData = null;
    }

    @Override
    public void deliverResult(SearchService.ResultWithDetail data) {
        if (isReset()) {
            return;
        }

        SearchService.ResultWithDetail oldData = mData;
        mData = data;

        if (isStarted()) {
            super.deliverResult(data);
        }

    }
}
