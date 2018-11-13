package com.example.rtwm38.myomdbapp;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.rtwm38.myomdbapp.model.Movie;

import java.util.List;

public class FavouriteMovieAdapter extends RecyclerView.Adapter<FavouriteMovieAdapter.ViewHolder> {
    private List<Movie> mMovies;
    private Fragment mContext;
    private String TAG = this.getClass().getSimpleName();

    public FavouriteMovieAdapter(List<Movie> items, Fragment context) {
        this.mContext = context;
        this.mMovies = items;
    }
    @NonNull
    @Override
    public FavouriteMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fav_movie_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteMovieAdapter.ViewHolder viewHolder, int i) {
        Movie movie = mMovies.get(i);
        viewHolder.mTitleView.setText(movie.Title);
        viewHolder.mYearView.setText(movie.Year);

        Log.d(TAG, movie.toString());
        final String imageUrl;

        if (! movie.Poster.equals("N/A")) {
            imageUrl = movie.Poster;
        } else {
            imageUrl = mContext.getResources().getString(R.string.default_poster);
        }
        viewHolder.mThumbImageView.layout(0, 0, 0, 0); // invalidate the width so that glide wont use that dimension
        Glide.with(mContext).load(imageUrl).into(viewHolder.mThumbImageView);
    }

    @Override
    public int getItemCount() {
        if(mMovies == null) {
            return 0;
        }
        return mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mThumbImageView;
        public final EditText mTitleView;
        public final EditText mYearView;

        public ViewHolder(View view) {
            super(view);
            mTitleView = view.findViewById(R.id.editTextName);
            mYearView = view.findViewById(R.id.editTextYear);
            mThumbImageView = view.findViewById(R.id.fav_imageview);
        }
    }
}
