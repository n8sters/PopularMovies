package com.android.ncpow.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by ncpow on 6/8/2017.
 */

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    private String mUrl;

    public MovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public List<Movie> loadInBackground() {
        return null;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    protected List<Movie> onLoadInBackground() {
        if ( mUrl == null ) {
            return null;
        }

        List<Movie> movies = QueryUtils.fetchMovieData(mUrl);
        return movies;
    }
}
