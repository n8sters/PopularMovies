package com.android.ncpow.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {


    private final String LOG_TAG = MainActivity.class.getSimpleName();

    Intent mMovieIntent;

    // !!!!!! REMOVE THIS BEFORE PUSHES AND RELEASES!!!!!!!!!!!!!
    // !!!!!! REMOVE THIS BEFORE PUSHES AND RELEASES!!!!!!!!!!!!!
    private static final String API_KEY = "???";
    // !!!!!! REMOVE THIS BEFORE PUSHES AND RELEASES!!!!!!!!!!!!!
    // !!!!!! REMOVE THIS BEFORE PUSHES AND RELEASES!!!!!!!!!!!!!

    private Menu menu;

    GridView gridView;

    MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.movie_grid_view);
        gridView.setOnItemClickListener(clickListener);

        if (savedInstanceState == null) {
            queryCurrentMovieData("popularity.desc");
        } else {

            Parcelable[] parcelable = savedInstanceState.
                    getParcelableArray(getString(R.string.movie_parcel_name));

            if (parcelable != null) {
                int numberReturnedMovie = parcelable.length;
                Movie[] movies = new Movie[numberReturnedMovie];
                for (int i = 0; i < numberReturnedMovie; i++) {
                    movies[i] = (Movie) parcelable[i];
                }

                gridView.setAdapter(adapter);
            }

        }

    }

    private final GridView.OnItemClickListener clickListener = new GridView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Movie item = (Movie) adapterView.getItemAtPosition(position);
            mMovieIntent = new Intent(getApplicationContext(), DetailActivity.class);

            mMovieIntent.putExtra(getResources().getString(R.string.movie_parcel_name), item);
            startActivity(mMovieIntent);

        }
    };

    private void queryCurrentMovieData(String sortPreference) {
        if (getInternetStatus() == true) {

            // TODO refactor this to chance name and some mild functionality
            MovieAsyncTask.OnTaskCompleted requestCompleted = new MovieAsyncTask.OnTaskCompleted() {

                @Override
                public void MovieAsyncTaskSuccessful(Movie[] movies) {
                    gridView.setAdapter(new MovieAdapter(getApplicationContext(), movies));
                }
            };

            MovieAsyncTask movieAsyncTask = new MovieAsyncTask(requestCompleted, API_KEY);
            movieAsyncTask.execute(sortPreference);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Log.v(LOG_TAG, "onSaveInstanceState");

        int numMovieObjects = gridView.getCount();
        if (numMovieObjects > 0) {
            // Get Movie objects from gridview
            Movie[] movies = new Movie[numMovieObjects];
            for (int i = 0; i < numMovieObjects; i++) {
                movies[i] = (Movie) gridView.getItemAtPosition(i);
            }

            // Save Movie objects to bundle
            outState.putParcelableArray(getString(R.string.movie_parcel_name), movies);
        }

        super.onSaveInstanceState(outState);
    }

    // check if phone is connected to internet.
    // TODO: add toast if no network is connected saying to connect to get movies!
    private boolean getInternetStatus() {
        // check state of connection
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // get state of default network
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }
}
