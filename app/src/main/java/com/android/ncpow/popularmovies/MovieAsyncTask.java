package com.android.ncpow.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

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
import java.nio.charset.Charset;

/**
 * Created by ncpow, Nathaniel ( Nate ) Powers.
 * Please feel free to reach out if you want to talk or have any questions.
 * My current email is ncpowers93@gmail.com.
 * Cheers
 */

// This class is responsible for running a network query task off the main thread,
// as to free up the UI thread as to avoid a buggy experience, or
// potentially an ANR!!
public class MovieAsyncTask extends AsyncTask<String, Void, Movie[]> {

    Context context;
    private final String mApiKey;

    private final OnTaskCompleted listener;

    // base url to be appended upon
    final String MOVIE_REQUEST_URL = "https://api.themoviedb.org/3/discover/movie?";


    public MovieAsyncTask(OnTaskCompleted listener, String apiKey) {
        super();
        this.listener = listener;
        mApiKey = apiKey;
    }

    // taken from StackOverflow
    // https://stackoverflow.com/questions/9963691/android-asynctask-sending-callbacks-to-ui
    public interface OnTaskCompleted {
        void MovieAsyncTaskSuccessful(Movie[] movies);
    }

    @Override
    protected Movie[] doInBackground(String... strings) {

        // base string to be mutated later
        String jsonResponse = "";

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        InputStream inputStream;
        try {
            URL url = createHTTPRequestUrl(strings);
            urlConnection = (HttpURLConnection) url.openConnection();
            // keep the app from reporting an ANR with a slow connection
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            } else {
                // lets user know what broke if there was an error
                switch (urlConnection.getResponseCode()) {
                    case 500:
                        Toast.makeText(context, R.string.error_code_500_message,
                                Toast.LENGTH_LONG).show();
                        break;
                    case 401:
                        Toast.makeText(context, R.string.error_code_401_message,
                                Toast.LENGTH_LONG).show();
                        break;
                    case 400:
                        Toast.makeText(context, R.string.error_code_400_message,
                                Toast.LENGTH_LONG).show();
                        break;
                }
            }
        } catch (IOException e) {
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                }
            }
        }
        try {
            return extractDataFromJSONResponse(jsonResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // reads the input stream that the API is sending us.
    // Copied directly from the Quake Report app, a Udacity project.
    // The finished Quake Report app can be found in my GitHub under
    // the name "Earthquake App" --Nate
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    // pull the info we need to display from the API response.
    private Movie[] extractDataFromJSONResponse(String moviesJsonStr) throws JSONException {

        JSONObject apiResponse = new JSONObject(moviesJsonStr);
        // gets the info under the "results" heading
        JSONArray resultsArray = apiResponse.getJSONArray("results");

        // usually returns exactly 20 movies, but we don't want to hardcode that
        Movie[] movies = new Movie[resultsArray.length()];

        for (int i = 0; i < resultsArray.length(); i++) {

            movies[i] = new Movie();

            JSONObject movieInfo = resultsArray.getJSONObject(i);
            //get movie name, not null
            movies[i].setmMovieName(movieInfo.getString("original_title"));
            //get poster path, may be null
            movies[i].setmPosterImage(movieInfo.getString("poster_path"));
            //get release date, we will only use the year, may be null
            movies[i].setmReleaseDate(movieInfo.getString("release_date"));
            // get rating to push through star rating system selector, may be null
            movies[i].setmRating(movieInfo.getDouble("vote_average"));
            // get movie overview, may be null
            movies[i].setmMovieDescription(movieInfo.getString("overview"));
        }

        return movies;
    }


    // creates the API request URL with parameters from shared preferences
    private URL createHTTPRequestUrl(String[] parameters) throws MalformedURLException {

        final String sortOrderString = "sort_by";

        // THIS IS NOT WHERE YOU PUT YOUR API KEY!!!!
        // IT GOES AT THE VERY TOP OF MainActivity
        final String apiKeyPlaceholder = "api_key";

        // works with the base URL from the top of this class.
        Uri builtUri = Uri.parse(MOVIE_REQUEST_URL).buildUpon()
                .appendQueryParameter(sortOrderString, parameters[0])
                .appendQueryParameter(apiKeyPlaceholder, mApiKey)
                .build();

        // build result api with parameters from shared preferences
        URL resultUrl = new URL(builtUri.toString());

        return resultUrl;
    }

    // Lets the main thread know when we have some data ready to display
    @Override
    protected void onPostExecute(Movie[] movies) {
        super.onPostExecute(movies);
        // let the main thread know we got some juicy data!
        listener.MovieAsyncTaskSuccessful(movies);
    }
}
