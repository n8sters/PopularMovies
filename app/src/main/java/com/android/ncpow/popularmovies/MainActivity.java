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


    // !!!!!! REMOVE THIS BEFORE PUSHES AND RELEASES!!!!!!!!!!!!!
    // !!!!!! REMOVE THIS BEFORE PUSHES AND RELEASES!!!!!!!!!!!!!
    private static final String API_KEY = "??";
    // !!!!!! REMOVE THIS BEFORE PUSHES AND RELEASES!!!!!!!!!!!!!
    // !!!!!! REMOVE THIS BEFORE PUSHES AND RELEASES!!!!!!!!!!!!!


    private final GridView.OnItemClickListener clickListener = new GridView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Movie item = (Movie) adapterView.getItemAtPosition(position);
            Intent mMovieIntent = new Intent(getApplicationContext(), DetailActivity.class);

            mMovieIntent.putExtra(getResources().getString(R.string.movie_parcel_name), item);
            startActivity(mMovieIntent);

        }
    };
    private Menu mMenu;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // in case you forget to add your API key make a toast to let user know
        if (API_KEY.length() < 5) {
            Toast.makeText(this, (R.string.forgot_to_add_API_key_message), Toast.LENGTH_LONG).show();

        }


        // set up gridview of movie posters
        gridView = (GridView) findViewById(R.id.movie_grid_view);
        gridView.setOnItemClickListener(clickListener);

        // get starting sort order.
        String defaultSortOrder = getSortOrderFromSharedPrefs();

        // if this is the first time the app is bring started
        if (savedInstanceState == null) {
            makeDBMovieRequest(defaultSortOrder);
        } else {
            // set up parcelable interface
            Parcelable[] parcelable = savedInstanceState.
                    getParcelableArray(getString(R.string.movie_parcel_name));

            // if we have some movies
            if (parcelable != null) {

                int length = parcelable.length;

                Movie[] movies = new Movie[length];

                for (int i = 0; i < length; i++) {
                    movies[i] = (Movie) parcelable[i];
                }

                // fill our gridview with movies
                // !!!! IMPORTANT !!!!
                // A new MovieAdapter must be created, if a class level
                // adapter is used, the databse will only be queried one time,
                // so if the device rotates the data will be lost
                gridView.setAdapter(new MovieAdapter(this, movies));
            }

        }

    }

    //if we have an internet connection, make an API DB request
    private void makeDBMovieRequest(String parameters) {

        final Context context = getApplicationContext();
        // if we has some internets!!!
        if (getInternetStatus()) {

            MovieAsyncTask.OnTaskCompleted onTaskCompleted = new MovieAsyncTask.OnTaskCompleted() {

                @Override
                public void MovieAsyncTaskSuccessful(Movie[] movies) {
                    gridView.setAdapter(new MovieAdapter(context, movies));
                }
            };

            MovieAsyncTask movieAsyncTask = new MovieAsyncTask(onTaskCompleted, API_KEY);
            movieAsyncTask.execute(parameters);
        } else {
            // let user know that they are not connected to the internet
            Toast.makeText(this, getString(R.string.no_internet_message), Toast.LENGTH_LONG).show();
        }
    }


    // repopulates when the device is rotated or navigated back to from
    // the DetailActivity
    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        int count = gridView.getCount();
        if (count > 0) {
            Movie[] movies = new Movie[count];
            // for each movie in teh gridview
            for (int i = 0; i < count; i++) {
                movies[i] = (Movie) gridView.getItemAtPosition(i);
            }
            // put that movies data into that box
            bundle.putParcelableArray(getString(R.string.movie_parcel_name), movies);
        } else {
            Toast.makeText(this, getString(R.string.no_movies_message), Toast.LENGTH_LONG).show();
        }
        super.onSaveInstanceState(bundle);
    }

    // check if phone is connected to internet.
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

        mMenu.add(Menu.NONE, R.string.setting_order_by_rating_key, Menu.NONE, null)
                .setVisible(false)
                .setTitle(R.string.menu_rate_label)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        mMenu.add(Menu.NONE,
                R.string.setting_order_by_popularity_key,
                Menu.NONE,
                null)
                .setVisible(false)
                .setTitle(R.string.menu_pop_label)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        changeToolbarSortTV();

        return true;
    }

    // this method brings together the helper methods defined below
    // to set the text in the toolbar and update the shared preferences.
    // Originally a huge single method. It can be easily refactored back to
    // that state if you're feeling particularly masochistic today...
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ((item.getItemId() == R.string.setting_order_by_popularity_key)) {
            updateSharedPrefs(getString(R.string.setting_order_by_popularity_value)); // order by popularity
            changeToolbarSortTV(); // update the TV to show the "Sort by Rating" text
            makeDBMovieRequest(getSortOrderFromSharedPrefs()); // re-query the API DB
            return true;
        } else if ((item.getItemId() == R.string.setting_order_by_rating_key)) { // basically the same as above, just in reverse.
                updateSharedPrefs(getString(R.string.setting_order_by_rating_value));
            changeToolbarSortTV();
            makeDBMovieRequest(getSortOrderFromSharedPrefs());
                return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    // changes the text view in the toolbar to let the user know which
    // sort order they are currently using.
    private void changeToolbarSortTV() {
        String sortMethod = getSortOrderFromSharedPrefs();
        if (sortMethod.equals(getString(R.string.setting_order_by_rating_value))) {
            mMenu.findItem(R.string.setting_order_by_rating_key).setVisible(false); // if we're sorting by popularity
            mMenu.findItem(R.string.setting_order_by_popularity_key).setVisible(true); // give the alternate option to sort by rating.
        } else {
            mMenu.findItem(R.string.setting_order_by_popularity_key).setVisible(false); // and if we're sorting by rating
            mMenu.findItem(R.string.setting_order_by_rating_key).setVisible(true); // give the option to sort by popularity
        }
    }

    // SUPER IMPORTANT METHOD DON'T CHANGE
    // It queries the PreferenceManager to get the current sort order.
    // Don't try to make this a class variable!!!
    private String getSortOrderFromSharedPrefs() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString(getString(R.string.pref_sort_order), getString(R.string.setting_order_by_popularity_value));
    }

    // taken from stack overflow
    // https://stackoverflow.com/questions/10186215/sharedpreferences-value-is-not-updated
    // credit where credit is due!
    private void updateSharedPrefs(String sortMethod) {
        SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditor = mypref.edit();
        prefsEditor.putString(getString(R.string.pref_sort_order), sortMethod);
        prefsEditor.apply();
    }
}
