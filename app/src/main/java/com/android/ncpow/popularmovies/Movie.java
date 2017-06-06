package com.android.ncpow.popularmovies;

/**
 * Created by ncpow on 6/5/2017.
 */

public class Movie {

    private String mMovieName;
    private int mPosterImage;
    private String mReleaseDate;
    private String mRating;
    private String mMovieDescription;

    public Movie(String movieName, int imageResource, String releaseDate,
                 String rating, String description) {
        mMovieName = movieName;
        mPosterImage = imageResource;
        mReleaseDate = releaseDate;
        mRating = rating;
        mMovieDescription = description;
    }

    public String getmMovieName() {
        return mMovieName;
    }

    public int getImageResourceId() {
        return mPosterImage;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public String getmRating() {
        return mRating;
    }

    public String getmMovieDescription() {
        return mMovieDescription;
    }

}
