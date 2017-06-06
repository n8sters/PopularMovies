package com.android.ncpow.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ncpow on 6/5/2017.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    public MovieAdapter( Context context, int resource, ArrayList data) {
        super(context, resource, data);
        this.layoutResourceId = resource;
        this.mContext = context;
        this.data = data;
    }

    // grid layout poster adapter
    static class ViewHolder {
        TextView movieName; // not necessary for the end
        ImageView moviePoster;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        Movie currentMovie = getItem(position);

        if ( convertView == null ) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.movieName = (TextView) convertView.findViewById(R.id.poster_text_view);
            holder.moviePoster = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.movieName.setText(currentMovie.getmMovieName());
        holder.moviePoster.setImageResource(currentMovie.getImageResourceId());
        return  convertView;

    }
}
