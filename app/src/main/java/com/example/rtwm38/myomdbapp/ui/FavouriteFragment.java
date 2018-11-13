package com.example.rtwm38.myomdbapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.rtwm38.myomdbapp.FavouriteMovieAdapter;
import com.example.rtwm38.myomdbapp.R;
import com.example.rtwm38.myomdbapp.model.DBHelper;
import com.example.rtwm38.myomdbapp.model.Movie;
import java.util.ArrayList;

public class FavouriteFragment extends Fragment {
    private RecyclerView mFavMovieListRecyclerView;
    private FavouriteMovieAdapter mFavMovieAdapter;
    private DBHelper dbHelper;
    ArrayList<Movie> movies;

    public FavouriteFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourite_main, container, false);

        mFavMovieListRecyclerView = view.findViewById(R.id.fav_recycler);
        movies = readMoviesFromDB();
        mFavMovieAdapter = new FavouriteMovieAdapter(movies, this);
        mFavMovieListRecyclerView.setAdapter(mFavMovieAdapter);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        mFavMovieListRecyclerView.setLayoutManager(gridLayoutManager);
        return view;
    }

    private ArrayList<Movie> readMoviesFromDB() {
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        ArrayList arrayList = dbHelper.getAllMovies();
        for (int i=0 ; i <arrayList.size() ; i++) {
            Movie movie = (Movie)arrayList.get(i);
            movieArrayList.add(movie);
        }
        return movieArrayList;
    }

}

