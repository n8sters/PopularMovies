package com.android.ncpow.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import static com.android.ncpow.popularmovies.MainActivity.EXTRA_MOVIE_DESCRIPTION;
import static com.android.ncpow.popularmovies.MainActivity.EXTRA_MOVIE_DURATION;
import static com.android.ncpow.popularmovies.MainActivity.EXTRA_MOVIE_NAME;
import static com.android.ncpow.popularmovies.MainActivity.EXTRA_MOVIE_POSTER;
import static com.android.ncpow.popularmovies.MainActivity.EXTRA_MOVIE_RATING;
import static com.android.ncpow.popularmovies.MainActivity.EXTRA_MOVIE_RELEASE_DATE;

/**
 * Created by ncpow on 6/6/2017.
 */

public class DetailActivity extends AppCompatActivity {


    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        TextView movieName = (TextView) findViewById(R.id.movie_title_text_view);
        ImageView poster = (ImageView) findViewById(R.id.movie_poster_image_view);
        TextView releaseDate = (TextView) findViewById(R.id.release_date_text_view);
        TextView rating = (TextView) findViewById(R.id.movie_rating_text_view);
        TextView durationtv = (TextView) findViewById(R.id.movie_duration_text_view);
        TextView description = (TextView) findViewById(R.id.description_text_view);

        Intent intent = getIntent();
        String id = intent.getStringExtra(EXTRA_MOVIE_NAME);
        int image = intent.getIntExtra(EXTRA_MOVIE_POSTER, R.drawable.mad_max); // mad_max is placeholder
        String release = intent.getStringExtra(EXTRA_MOVIE_RELEASE_DATE);
        String rate = intent.getStringExtra(EXTRA_MOVIE_RATING);
        String duration = intent.getStringExtra(EXTRA_MOVIE_DURATION);
        String desc = intent.getStringExtra(EXTRA_MOVIE_DESCRIPTION);

        movieName.setText(id);
        poster.setImageResource(image);
        releaseDate.setText(release);
        rating.setText(rate);
        durationtv.setText(duration);
        description.setText(desc);

    }
}
