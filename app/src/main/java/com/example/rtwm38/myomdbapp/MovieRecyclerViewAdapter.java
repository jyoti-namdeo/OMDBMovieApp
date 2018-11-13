package com.example.rtwm38.myomdbapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rtwm38.myomdbapp.model.DBHelper;
import com.example.rtwm38.myomdbapp.model.Details;
import com.example.rtwm38.myomdbapp.ui.DetailDialogFragment;

import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder>  {
    private List<Details> mValues;
    private  Fragment mContext;
    DBHelper mydb;
    public MovieRecyclerViewAdapter(List<Details> items, Fragment context) {
        this.mContext = context;
        mValues = items;
        mydb = new DBHelper(context.getActivity());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_movie, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Details detail = mValues.get(i);
        final String title = detail.Title;
        final String director = detail.Director;
        final String year = detail.Year;
        viewHolder.mDirectorView.setText(director);
        viewHolder.mTitleView.setText(title);
        viewHolder.mYearView.setText(year);

        final String imageUrl;
        if (! detail.Poster.equals("N/A")) {
            imageUrl = detail.Poster;
        } else {
            // default image if there is no poster available
            imageUrl = mContext.getResources().getString(R.string.default_poster);
        }
        viewHolder.mThumbImageView.layout(0, 0, 0, 0); // invalidate the width so that glide wont use that dimension
        Glide.with(mContext).load(imageUrl).into(viewHolder.mThumbImageView);

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Bundle bundle = new Bundle();
                bundle.putParcelable(DetailDialogFragment.MOVIE_DETAIL, detail);
                bundle.putString(DetailDialogFragment.IMAGE_URL, imageUrl);

                FragmentManager fm = mContext.getFragmentManager();
                DetailDialogFragment detailDialogFragment = DetailDialogFragment.newInstance("Some Title");

                detailDialogFragment.setArguments(bundle);
                detailDialogFragment.show(fm, "Sample Fragment");
            }
        });
        viewHolder.mFavImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mydb.insertMovie(imageUrl, title, year)) {
                    v.setBackgroundResource(R.drawable.favourite_added);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mValues == null) {
            return 0;
        }
        return mValues.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        Glide.clear(holder.mThumbImageView);
    }

    public void swapData(List<Details> items) {
        if(items != null) {
            mValues = items;
            notifyDataSetChanged();
        } else {
            mValues = null;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mYearView;
        public final TextView mDirectorView;
        public final ImageView mThumbImageView;
        public final ImageButton mFavImageButton;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = view.findViewById(R.id.movie_title);
            mYearView = view.findViewById(R.id.movie_year);
            mThumbImageView = view.findViewById(R.id.thumbnail);
            mDirectorView = view.findViewById(R.id.movie_director);
            mFavImageButton = view.findViewById(R.id.fav_imageButton);
        }

    }
}
