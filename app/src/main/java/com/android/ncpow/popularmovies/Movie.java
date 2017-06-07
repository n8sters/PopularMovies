package com.android.ncpow.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ncpow on 6/5/2017.
 */

public class Movie implements Parcelable {

    private String mMovieName;
    private int mPosterImage;
    private String mMovieDuration;
    private String mReleaseDate;
    private String mRating;
    private String mMovieDescription;

    public Movie(String movieName, int imageResource, String releaseDate, String duration,
                 String rating, String description) {
        mMovieName = movieName;
        mPosterImage = imageResource;
        mReleaseDate = releaseDate;
        mMovieDuration = duration;
        mRating = rating;
        mMovieDescription = description;
    }

    public String getmMovieName() {
        return mMovieName;
    }

    public int getImageResourceId() {
        return mPosterImage;
    }

    public String getmMovieDuration() { return  mMovieDuration; }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public String getmRating() {
        return mRating;
    }

    public String getmMovieDescription() {
        return mMovieDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
