package com.example.rtwm38.myomdbapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rtwm38.myomdbapp.MovieRecyclerViewAdapter;
import com.example.rtwm38.myomdbapp.R;
import com.example.rtwm38.myomdbapp.network.RetrofitLoader;
import com.example.rtwm38.myomdbapp.network.SearchService;

import util.CommonUtils;

public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<SearchService.ResultWithDetail>{
    private Button mSearchButton;
    private EditText mSearchEditText;
    private RecyclerView mMovieListRecyclerView;
    private MovieRecyclerViewAdapter mMovieAdapter;
    private String mMovieTitle;
    private ProgressBar mProgressBar;
    private static final int LOADER_ID = 1;

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_layout, container, false);

        mSearchEditText = view.findViewById(R.id.search_edittext);

        mSearchEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {
                    startSearch();
                    handled = true;
                }
                return handled;
            }
        });
        mSearchButton = view.findViewById(R.id.search_button);
        mMovieListRecyclerView = view.findViewById(R.id.recycler_view);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearch();
            }
        });
        mMovieAdapter = new MovieRecyclerViewAdapter(null, this);
        mMovieListRecyclerView.setAdapter(mMovieAdapter);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mMovieListRecyclerView.setItemAnimator(null);

        mMovieListRecyclerView.setLayoutManager(gridLayoutManager);
       // mContext.getSupportLoaderManager().enableDebugLogging(true);
        mProgressBar = view.findViewById(R.id.progress_spinner);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("mMovieTitle", mMovieTitle);
        outState.putInt("progress_visibility",mProgressBar.getVisibility());
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @NonNull
    @Override
    public Loader<SearchService.ResultWithDetail> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new RetrofitLoader(this, bundle.getString("movieTitle"));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<SearchService.ResultWithDetail> loader, SearchService.ResultWithDetail resultWithDetail) {
        mProgressBar.setVisibility(View.GONE);
        mMovieListRecyclerView.setVisibility(View.VISIBLE);
        if(resultWithDetail.getResponse().equals("True")) {
            mMovieAdapter.swapData(resultWithDetail.getMovieDetailList());
        } else {
            Snackbar.make(mMovieListRecyclerView,
                    getResources().getString(R.string.snackbar_title_not_found), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<SearchService.ResultWithDetail> loader) {
        mMovieAdapter.swapData(null);
    }

    private void startSearch() {
        if(CommonUtils.isNetworkAvailable(getActivity())) {
            CommonUtils.hideSoftKeyboard(this);
            String movieTitle = mSearchEditText.getText().toString().trim();
            if (!movieTitle.isEmpty()) {
                Bundle args = new Bundle();
                args.putString("movieTitle", movieTitle);
                getLoaderManager().restartLoader(LOADER_ID, args, this);
                mMovieTitle = movieTitle;
                mProgressBar.setVisibility(View.VISIBLE);
                mMovieListRecyclerView.setVisibility(View.GONE);
            }
            else
                Snackbar.make(mMovieListRecyclerView,
                        getResources().getString(R.string.snackbar_title_empty),
                        Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(mMovieListRecyclerView,
                    getResources().getString(R.string.network_not_available),
                    Snackbar.LENGTH_LONG).show();
        }
    }
}
