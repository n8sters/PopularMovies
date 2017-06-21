package com.android.ncpow.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by ncpow, Nathaniel ( Nate ) Powers.
 * Please feel free to reach out if you want to talk or have any questions.
 * My current email is ncpowers93@gmail.com.
 * Cheers
 */

// The detail activity, sets the images using Picasso.
public class DetailActivity extends AppCompatActivity {


    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        TextView movieName = (TextView) findViewById(R.id.movie_title_text_view);
        ImageView poster = (ImageView) findViewById(R.id.movie_poster_image_view);
        TextView releaseDatetv = (TextView) findViewById(R.id.release_date_text_view);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        TextView description = (TextView) findViewById(R.id.description_scroll_view);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(getString(R.string.movie_parcel_name));

        movieName.setText(movie.getmMovieName());

        Picasso.with(this)
                .load(movie.getPosterPath())
                // images use a 1:1.50 ratio.
                .resize(getResources().getInteger(R.integer.poster_width),
                        getResources().getInteger(R.integer.poster_height))
                .error(R.drawable.no_poster_found)
                .placeholder(R.drawable.black)
                .into(poster);

        String releaseDate = movie.getmReleaseDate();
        if (releaseDate.length() >= 4) {
            releaseDate = movie.getmReleaseDate().substring(0, 4);
        } else {
            releaseDate = getResources().getString(R.string.default_date);
        }
        releaseDatetv.setText(releaseDate);


        Double rating = (movie.getRating() / 2);

        ratingBar.setRating(Float.parseFloat(rating.toString()));

        String overView = movie.getmMovieDescription();
        // if there wasn't an overview assigned to the movie, let the user know
        // instead of leaving the area blank
        if (overView == null) {
            overView = getResources().getString(R.string.default_description);
        } else {
            description.setText(overView);

        }
    }
}
