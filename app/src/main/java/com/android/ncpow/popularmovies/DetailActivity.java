package com.android.ncpow.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by ncpow on 6/6/2017.
 */

public class DetailActivity extends AppCompatActivity {

    private final String LOG_TAG = DetailActivity.class.getSimpleName();


    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        TextView movieName = (TextView) findViewById(R.id.movie_title_text_view);
        ImageView poster = (ImageView) findViewById(R.id.movie_poster_image_view);
        TextView releaseDatetv = (TextView) findViewById(R.id.release_date_text_view);
        TextView rating = (TextView) findViewById(R.id.movie_rating_text_view);
        TextView description = (TextView) findViewById(R.id.description_scroll_view);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(getString(R.string.movie_parcel_name));

        movieName.setText(movie.getmMovieName());

        Picasso.with(this)
                .load(movie.getPosterPath())
                .resize(getResources().getInteger(R.integer.poster_width),
                        getResources().getInteger(R.integer.poster_height))
                .error(R.drawable.no_poster_found)
                .placeholder(R.drawable.black)
                .into(poster);

        String overView = movie.getmMovieDescription();
        if (overView == null) {
            overView = getResources().getString(R.string.default_description);
        }
        description.setText(overView);
        rating.setText("test rating");

        // First get the release date from the object - to be used if something goes wrong with
        // getting localized release date (catch).
        // TODO refactor this
        String releaseDate = movie.getmReleaseDate();
        if (releaseDate != null) {
            // TODO fix date formatter
            releaseDate = getResources().getString(R.string.default_date);
        } else {
            releaseDate = getResources().getString(R.string.default_date);
        }
        releaseDatetv.setText(releaseDate);

    }
}
