package com.android.ncpow.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ncpow on 6/9/2017.
 */

// TODO: Refactor all of this tbh
public class MovieAsyncTask extends AsyncTask<String, Void, Movie[]> {

    private final String LOG_TAG = MovieAsyncTask.class.getSimpleName();

    private final String mApiKey;

    private final OnTaskCompleted mListener;

    // base url to be appended upon
    final String MOVIE_REQUEST_URL = "https://api.themoviedb.org/3/discover/movie?";


    public MovieAsyncTask(OnTaskCompleted listener, String apiKey) {
        super();

        mListener = listener;
        mApiKey = apiKey;
    }

    public interface OnTaskCompleted {
        void MovieAsyncTaskSuccessful(Movie[] movies);

    }

    @Override
    protected Movie[] doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Holds data returned from the API
        String moviesJsonStr = null;

        try {
            URL url = getApiUrl(strings);

            // Start connecting to get JSON
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();

            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Adds '\n' at last line if not already there.
                // This supposedly makes it easier to debug.
                builder.append(line).append("\n");
            }

            if (builder.length() == 0) {
                // No data found. Nothing more to do here.
                return null;
            }

            moviesJsonStr = builder.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            // Tidy up: release url connection and buffered reader
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            // Make sense of the JSON data
            return getMoviesDataFromJson(moviesJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Extracts data from the JSON object and returns an Array of movie objects.
     *
     * @param moviesJsonStr JSON string to be traversed
     * @return Array of Movie objects
     * @throws JSONException
     */
    private Movie[] getMoviesDataFromJson(String moviesJsonStr) throws JSONException {

        // Get the array containing hte movies found
        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray resultsArray = moviesJson.getJSONArray("results");

        // Create array of Movie objects that stores data from the JSON string
        Movie[] movies = new Movie[resultsArray.length()];

        // Traverse through movies one by one and get data
        for (int i = 0; i < resultsArray.length(); i++) {
            // Initialize each object before it can be used
            movies[i] = new Movie();

            // Object contains all tags we're looking for
            JSONObject movieInfo = resultsArray.getJSONObject(i);

            // Store data in movie object
            movies[i].setmMovieName(movieInfo.getString("original_title"));
            movies[i].setmPosterImage(movieInfo.getString("poster_path"));
            movies[i].setmMovieDescription(movieInfo.getString("overview"));
            movies[i].setmRating(movieInfo.getDouble("vote_average"));
            movies[i].setmReleaseDate(movieInfo.getString("release_date"));
        }

        return movies;
    }

    /**
     * Creates and returns an URL.
     *
     * @param parameters Parameters to be used in the API call
     * @return URL formatted with parameters for the API
     * @throws MalformedURLException
     */
    private URL getApiUrl(String[] parameters) throws MalformedURLException {

        final String sortOrderString = "sort_by";
        final String apiKeyPlaceholder = "api_key";

        Uri builtUri = Uri.parse(MOVIE_REQUEST_URL).buildUpon()
                .appendQueryParameter(sortOrderString, parameters[0])
                .appendQueryParameter(apiKeyPlaceholder, mApiKey)
                .build();

        return new URL(builtUri.toString());
    }

    @Override
    protected void onPostExecute(Movie[] movies) {
        super.onPostExecute(movies);
        mListener.MovieAsyncTaskSuccessful(movies);
    }
}
