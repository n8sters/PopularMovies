package com.android.ncpow.popularmovies;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ncpow on 6/6/2017.
 */

public class DetailActivity extends AppCompatActivity {

    private String mMovieName;
    private int mPosterImage;
    private String mReleaseDate;
    private String mRating;
    private String mMovieDescription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);


    }
}
