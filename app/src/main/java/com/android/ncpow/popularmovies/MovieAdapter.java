package com.android.ncpow.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by ncpow, Nathaniel ( Nate ) Powers.
 * Please feel free to reach out if you want to talk or have any questions.
 * My current email is ncpowers93@gmail.com.
 * Cheers
 */

class MovieAdapter extends BaseAdapter {


    private final Context mContext;
    private final Movie[] mMovies;

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
        return posterView;
    }
}
