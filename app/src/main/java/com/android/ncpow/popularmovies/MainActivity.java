package com.android.ncpow.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Intent mMovieIntent;

    public final static String EXTRA_MOVIE_NAME = "com.android.ncpow.popularmovies.EXTRA_MOVIE_NAME";
    public final static String EXTRA_MOVIE_POSTER = "com.android.ncpow.popularmovies.EXTRA_MOVIE_POSTER";
    public final static String EXTRA_MOVIE_RELEASE_DATE = "com.android.ncpow.popularmovies.EXTRA_MOVIE_RELEASE_DATE";
    public final static String EXTRA_MOVIE_RATING = "com.android.ncpow.popularmovies.EXTRA_MOVIE_RATING";
    public final static String EXTRA_MOVIE_DURATION = "com.android.ncpow.popularmovies.EXTRA_MOVIE_DURATION";
    public final static String EXTRA_MOVIE_DESCRIPTION = "com.android.ncpow.popularmovies.EXTRA_MOVIE_DESCRIPTION";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie(getString(R.string.mad_max_movie_name), R.drawable.mad_max, getString(R.string.mad_max_release_date),
                getString(R.string.mad_max_duration), 8.6, getString(R.string.mad_max_description)));


        GridView gridView = (GridView) findViewById(R.id.movie_grid_view);
        MovieAdapter adapter = new MovieAdapter(this, R.layout.grid_movie_layout, movies);
        gridView.setOnItemClickListener(clickListener);
        gridView.setAdapter(adapter);


    }

    private final GridView.OnItemClickListener clickListener = new GridView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Movie item = (Movie) adapterView.getItemAtPosition(position);
            mMovieIntent = new Intent(getApplicationContext(), DetailActivity.class);

            String movieName = item.getmMovieName();
            int movieImage = item.getImageResourceId();
            String movieDuration = item.getmMovieDuration();
            String movieReleaseDate = item.getmReleaseDate();
            Double movieRating = item.getmRating();
            String movieDescription = item.getmMovieDescription();

            mMovieIntent.putExtra(EXTRA_MOVIE_NAME, movieName);
            mMovieIntent.putExtra(EXTRA_MOVIE_POSTER, movieImage);
            mMovieIntent.putExtra(EXTRA_MOVIE_DURATION, movieDuration);
            mMovieIntent.putExtra(EXTRA_MOVIE_RELEASE_DATE, movieReleaseDate);
            mMovieIntent.putExtra(EXTRA_MOVIE_RATING, movieRating);
            mMovieIntent.putExtra(EXTRA_MOVIE_DESCRIPTION, movieDescription);

            startActivity(mMovieIntent);

        }
    };
}
