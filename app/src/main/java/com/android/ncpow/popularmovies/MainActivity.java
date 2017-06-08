package com.android.ncpow.popularmovies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {


    Intent mMovieIntent;

    // !!!!!! REMOVE THIS BEFORE PUSHES AND RELEASES!!!!!!!!!!!!!
    // !!!!!! REMOVE THIS BEFORE PUSHES AND RELEASES!!!!!!!!!!!!!
    private static final String API_KEY = "a90746122770e4eaa745543c90bf740b";
    // !!!!!! REMOVE THIS BEFORE PUSHES AND RELEASES!!!!!!!!!!!!!
    // !!!!!! REMOVE THIS BEFORE PUSHES AND RELEASES!!!!!!!!!!!!!


    MovieAdapter adapter;

    private static final String MOVIE_DB_REQUEST_URL =
            "https://api.themoviedb.org/3/movie/157336?api_key=" + API_KEY;

    private static final int MOVIE_LOADER_ID = 1;

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
        adapter = new MovieAdapter(this, R.layout.grid_movie_layout, movies);
        gridView.setOnItemClickListener(clickListener);
        gridView.setAdapter(adapter);

        // check state of connection
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // get state of default network
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(MOVIE_LOADER_ID, null, this);
        }

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


    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {

        Uri baseUri = Uri.parse(MOVIE_DB_REQUEST_URL);
        Uri.Builder builder = baseUri.buildUpon();

        return new MovieLoader(this, builder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        adapter.clear();
        if (movies != null && !movies.isEmpty()) {
            adapter.addAll(movies);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        adapter.clear();
    }
}
