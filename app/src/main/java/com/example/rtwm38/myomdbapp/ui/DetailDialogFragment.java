package com.example.rtwm38.myomdbapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rtwm38.myomdbapp.R;
import com.example.rtwm38.myomdbapp.model.Details;

public class DetailDialogFragment extends DialogFragment {
    public static final String MOVIE_DETAIL = "movie_detail";
    public static final String IMAGE_URL = "image_url";

    public DetailDialogFragment() {
    }

    public static DetailDialogFragment newInstance(String title) {
        DetailDialogFragment frag = new DetailDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_detail, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Details detail = getArguments().getParcelable(MOVIE_DETAIL);
        final String imageUrl =  getArguments().getString(IMAGE_URL);
        Glide.with(this).load(imageUrl).into( (ImageView) view.findViewById(R.id.main_backdrop));

        ((TextView) view.findViewById(R.id.grid_title)).setText(detail.Title);
        ((TextView) view.findViewById(R.id.grid_writers)).setText(detail.Writer);
        ((TextView) view.findViewById(R.id.grid_actors)).setText(detail.Actors);
        ((TextView) view.findViewById(R.id.grid_director)).setText(detail.Director);
        ((TextView) view.findViewById(R.id.grid_genre)).setText(detail.Genre);
        ((TextView) view.findViewById(R.id.grid_released)).setText(detail.Released);
        ((TextView) view.findViewById(R.id.grid_plot)).setText(detail.Plot);
        ((TextView) view.findViewById(R.id.grid_runtime)).setText(detail.Runtime);
    }

}
