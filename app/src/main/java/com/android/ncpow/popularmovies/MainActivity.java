package com.android.ncpow.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private final String LOG_TAG = MainActivity.class.getSimpleName();

    Intent mMovieIntent;

    // !!!!!! REMOVE THIS BEFORE PUSHES AND RELEASES!!!!!!!!!!!!!
    // !!!!!! REMOVE THIS BEFORE PUSHES AND RELEASES!!!!!!!!!!!!!
    private static final String API_KEY = "??";
    // !!!!!! REMOVE THIS BEFORE PUSHES AND RELEASES!!!!!!!!!!!!!
    // !!!!!! REMOVE THIS BEFORE PUSHES AND RELEASES!!!!!!!!!!!!!


    GridView gridView;
    public Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.movie_grid_view);
        gridView.setOnItemClickListener(clickListener);

        if (savedInstanceState == null) {
            queryCurrentMovieData("vote_average.desc");
        } else {

            Parcelable[] parcelable = savedInstanceState.
                    getParcelableArray(getString(R.string.movie_parcel_name));

            if (parcelable != null) {
                int numberReturnedMovie = parcelable.length;
                Movie[] movies = new Movie[numberReturnedMovie];
                for (int i = 0; i < numberReturnedMovie; i++) {
                    movies[i] = (Movie) parcelable[i];
                }

                gridView.setAdapter(new MovieAdapter(this, movies));
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

        int numMovieObjects = gridView.getCount();
        if (numMovieObjects > 0) {
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
            Toast.makeText(this, getString(R.string.no_internet_message), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mMenu = menu;

        mMenu.add(Menu.NONE,
                R.string.setting_order_by_popularity_key,
                Menu.NONE,
                null)
                .setVisible(false)
                .setTitle(R.string.menu_pop_label)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        mMenu.add(Menu.NONE, R.string.setting_order_by_rating_key, Menu.NONE, null)
                .setVisible(false)
                .setTitle(R.string.menu_rate_label)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        updateMenu();

        return true;
    }

    // TODO refactor as if/else
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.string.setting_order_by_popularity_key:
                updateSharedPrefs(getString(R.string.setting_order_by_popularity_value));
                updateMenu();
                queryCurrentMovieData(getSortMethod());
                return true;
            case R.string.setting_order_by_rating_key:
                updateSharedPrefs(getString(R.string.setting_order_by_rating_value));
                updateMenu();
                queryCurrentMovieData(getSortMethod());
                return true;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    // TODO bring inside above method
    private void updateMenu() {
        String sortMethod = getSortMethod();

        if (sortMethod.equals(getString(R.string.setting_order_by_popularity_value))) {
            mMenu.findItem(R.string.setting_order_by_popularity_key).setVisible(false);
            mMenu.findItem(R.string.setting_order_by_rating_key).setVisible(true);
        } else {
            mMenu.findItem(R.string.setting_order_by_rating_key).setVisible(false);
            mMenu.findItem(R.string.setting_order_by_popularity_key).setVisible(true);
        }
    }

    // TODO bring inside above method
    private String getSortMethod() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        return prefs.getString(getString(R.string.pref_sort_order),
                getString(R.string.setting_order_by_popularity_value));
    }

    private void updateSharedPrefs(String sortMethod) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.pref_sort_order), sortMethod);
        editor.apply();
    }
}
