package com.android.ncpow.popularmovies;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

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

        TextView movieName = (TextView) findViewById(R.id.movie_title_text_view);
        ImageView poster = (ImageView) findViewById(R.id.movie_poster_image_view);
        TextView releaseDate = (TextView) findViewById(R.id.release_date_text_view);
        TextView durationtv = (TextView) findViewById(R.id.movie_duration_text_view);
        TextView description = (TextView) findViewById(R.id.description_text_view);

        movieName.setText("Movie name");
        poster.setImageResource(R.drawable.mad_max);
        releaseDate.setText("2016");
        durationtv.setText("120 min");
        description.setText("Some guys do some things");
    }
}
