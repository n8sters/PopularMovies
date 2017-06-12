package com.android.ncpow.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ncpow on 6/5/2017.
 *
 * started with base class, and as suggested by my mentor has the class refactored at
 * http://www.parcelabler.com/
 * Thanks peeps!
 */
public class Movie implements Parcelable {

    private String mMovieName;
    private String mPosterImage;
    private String mMovieDuration;
    private String mReleaseDate;
    private Double mRating;
    private String mMovieDescription;

    // empty constructor
    public Movie() {}

    public Movie(String movieName, String imageResource, String releaseDate, String duration,
                 Double rating, String description) {
        mMovieName = movieName;
        mPosterImage = imageResource;
        mReleaseDate = releaseDate;
        mMovieDuration = duration;
        mRating = rating;
        mMovieDescription = description;
    }


    // getter methods vvv

    public String getmMovieName() {
        return mMovieName;
    }

    // TODO refactor
    public String getPosterPath() {
        final String TMDB_POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185";

        return TMDB_POSTER_BASE_URL + mPosterImage;
    }
    public String getmMovieDuration() { return  mMovieDuration; }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public double getmRating() {
        return mRating;
    }

    public String getmMovieDescription() {
        return mMovieDescription;
    }

    // getter methods ^^^

    //------------------------------- div ---------------------------------

    // setter methods vvv

    public void setmMovieName(String movieName ) {
        mMovieName = movieName;
    }

    public void setmPosterImage(String poster) {
        mPosterImage = poster;
    }

    public void setmMovieDescription(String description) {
        if (!description.equals(null)) {
            mMovieDescription = description;
        }
    }

    public void setmRating(Double rating) {
        mRating = rating;
    }

    public void setmReleaseDate(String releaseDate) {
        if (!releaseDate.equals(null)) {
            mReleaseDate = releaseDate;
        }
    }




    protected Movie(Parcel in) {
        mMovieName = in.readString();
        mPosterImage = in.readString();
        mMovieDuration = in.readString();
        mReleaseDate = in.readString();
        mRating = Double.valueOf(in.readString());
        mMovieDescription = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mMovieName);
        dest.writeString(mPosterImage);
        dest.writeString(mMovieDuration);
        dest.writeString(mReleaseDate);
        dest.writeValue(mRating);
        dest.writeString(mMovieDescription);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
