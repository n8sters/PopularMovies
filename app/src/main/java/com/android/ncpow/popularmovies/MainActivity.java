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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Mad Max", R.drawable.mad_max, "2016", "8/10", "Stuff happens"));
        movies.add(new Movie("Mad Max", R.drawable.mad_max, "2016", "8/10", "Stuff happens"));
        movies.add(new Movie("Mad Max", R.drawable.mad_max, "2016", "8/10", "Stuff happens"));
        movies.add(new Movie("Mad Max", R.drawable.mad_max, "2016", "8/10", "Stuff happens"));
        movies.add(new Movie("Mad Max", R.drawable.mad_max, "2016", "8/10", "Stuff happens"));
        movies.add(new Movie("Mad Max", R.drawable.mad_max, "2016", "8/10", "Stuff happens"));


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
            mMovieIntent.putExtra(EXTRA_MOVIE_NAME, movieName);
            startActivity(mMovieIntent);

        }
    };
}
