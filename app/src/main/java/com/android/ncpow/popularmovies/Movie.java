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
    private int mPosterImage;
    private String mMovieDuration;
    private String mReleaseDate;
    private String mRating;
    private String mMovieDescription;

    // empty constructor
    public Movie() {}

    public Movie(String movieName, int imageResource, String releaseDate, String duration,
                 String rating, String description) {
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

    // getter methods ^^^

    //------------------------------- div ---------------------------------

    // setter methods vvv

    public void setmMovieName(String movieName ) {
        mMovieName = movieName;
    }


    protected Movie(Parcel in) {
        mMovieName = in.readString();
        mPosterImage = in.readInt();
        mMovieDuration = in.readString();
        mReleaseDate = in.readString();
        mRating = in.readString();
        mMovieDescription = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mMovieName);
        dest.writeInt(mPosterImage);
        dest.writeString(mMovieDuration);
        dest.writeString(mReleaseDate);
        dest.writeString(mRating);
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
