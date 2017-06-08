package com.android.ncpow.popularmovies;

import android.test.mock.MockContentProvider;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ncpow on 6/8/2017.
 */

public class QueryUtils {



    // combine @extractFeaturesFromJson and @makeHTTPRequest to fetch data
    public static List<Movie> fetchMovieData(String requestUrl) {

        URL url = createUrl(requestUrl);

        String response = null;

        try {
            response = makeHTTPRequest(url);
        } catch (IOException e ) {
            // LOG tag removed
        }
        
        List<Movie> movies = extractFeaturesFromJson(response);
        
        return movies;
    }

    // returns a list of Movie objects
    private static List<Movie> extractFeaturesFromJson(String movieDBJson) {

        if (TextUtils.isEmpty(movieDBJson)) {
            return null;
        }

        List<Movie> movies = new ArrayList<>();

        try {

            JSONObject baseJsonResponse = new JSONObject(movieDBJson);

            JSONArray movieArray = baseJsonResponse.getJSONArray("results");

            Movie[] movie = new Movie[movieArray.length()];

            // loop over JSONObjects and create Movie objects
            for ( int i = 0; i < movieArray.length(); i++ ) {

                movie[i] = new Movie();

                JSONObject currentMovie = movieArray.getJSONObject(i);

                movie[i].setmMovieName(currentMovie.getString("original_title"));

            }

        } catch ( JSONException e ) {
            // LOG tag removed

            // IF YOU'RE READING THIS BEAUSE I APPLIED TO A POSITION
            // AT YOUR COMPANY SAY HI! EMAIL ME At
            // --> ncpowers93@gmail.com <--

        }

        return movies;
    }

    private static String makeHTTPRequest(URL url) throws IOException {
        
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            // LOG tag removed
        }
        return url;
    }

    // copied directly from the Quake Report, part of the Udacity Android Basics Nanodegree,
    // of which I took all classes and finished all projects.
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
}
