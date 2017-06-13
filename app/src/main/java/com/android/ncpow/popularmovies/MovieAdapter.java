package com.android.ncpow.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by ncpow on 6/5/2017.
 */

public class MovieAdapter extends BaseAdapter {

    private final String LOG_TAG = MovieAdapter.class.getSimpleName();


    private Context mContext;
    private Movie[] mMovies;

    public MovieAdapter(Context context, Movie[] movies) {
        this.mContext = context;
        this.mMovies = movies;
    }

    @Override
    public int getCount() {
        if (mMovies == null || mMovies.length == 0) {
            return -1;
        }

        return mMovies.length;
    }

    @Override
    public Object getItem(int i) {
        if (mMovies == null || mMovies.length == 0) {
            return null;
        }

        return mMovies[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView posterView;

        if ( convertView == null ) {
            posterView = new ImageView(mContext);
            posterView.setAdjustViewBounds(true);
        } else {
            posterView = (ImageView) convertView;
        }

        Picasso.with(mContext)
                .load(mMovies[position].getPosterPath())
                .resize(mContext.getResources().getInteger(R.integer.poster_width),
                        mContext.getResources().getInteger(R.integer.poster_height))
                .error(R.drawable.no_poster_found)
                .placeholder(R.drawable.black)
                .into(posterView);

        Log.e(LOG_TAG, "Poster Path: " + mMovies[position].getPosterPath().toString());
        return posterView;
    }
}
