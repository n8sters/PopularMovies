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
@SuppressWarnings("ObjectEqualsNull")
public class Movie implements Parcelable {

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
    private String mMovieName;
    private String mPosterImage;
    private String mReleaseDate;
    private Double mRating;
    private String mMovieDescription;


    // getter methods vvv

    // empty constructor
    public Movie() {}

    private Movie(Parcel in) {
        mMovieName = in.readString();
        mPosterImage = in.readString();
        mReleaseDate = in.readString();
        mRating = (Double) in.readValue(Double.class.getClassLoader());
        mMovieDescription = in.readString();
    }

    public String getmMovieName() {
        return mMovieName;
    }

    public void setmMovieName(String movieName) {
        mMovieName = movieName;
    }

    // TODO refactor
    public String getPosterPath() {
        final String TMDB_POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185";

        return TMDB_POSTER_BASE_URL + mPosterImage;
    }

    // getter methods ^^^

    //------------------------------- div ---------------------------------

    // setter methods vvv

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public void setmReleaseDate(String releaseDate) {
        if (!releaseDate.equals(null)) {
            mReleaseDate = releaseDate;
        }
    }

    public String getRating() {
        int num = (int) Math.ceil(mRating / 2);
        switch (num) {
            case 5:
                return "* * * * *";
            case 4:
                return "* * * *";
            case 3:
                return "* * *";
            case 2:
                return "* *";
            case 1:
                return "*";
            default:
                return "No rating found";
        }
    }

    public String getmMovieDescription() {
        return mMovieDescription;
    }

    public void setmMovieDescription(String description) {
        if (!description.equals(null)) {
            mMovieDescription = description;
        }
    }

    public void setmPosterImage(String poster) {
        mPosterImage = poster;
    }

    public void setmRating(Double rating) {
        mRating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mMovieName);
        dest.writeString(mPosterImage);
        dest.writeString(mReleaseDate);
        dest.writeValue(mRating);
        dest.writeString(mMovieDescription);
    }
}
