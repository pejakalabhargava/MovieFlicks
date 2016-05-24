package com.example.bkakran.flicks.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bkakran.flicks.DetailActivity;
import com.example.bkakran.flicks.R;
import com.example.bkakran.flicks.VideoActivity;
import com.example.bkakran.flicks.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by bkakran on 5/17/16.
 */
public class MoviesAdapter extends ArrayAdapter<Movie> {

    // View lookup cache
    private static class ViewHolder {
        TextView tvTitle;
        TextView tvDesc;
        ImageView ivPoster;
    }


    public MoviesAdapter(Context context, ArrayList<Movie> movies) {
        super(context, R.layout.item_movie_relative, movies);
    }

    @Override
    public int getViewTypeCount() {
        // Returns the number of types of Views that will be created by this adapter
        // Each type represents a set of views that can be converted
        return 2;
    }

    // Get the type of View that will be created by getView(int, View, ViewGroup)
    // for the specified item.
    @Override
    public int getItemViewType(int position) {
        // Return an integer here representing the type of View.
        // Note: Integers must be in the range 0 to getViewTypeCount() - 1
        if (getItem(position).voteAverage <= 5.00d) {
            return 0;
        }
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Movie movie = getItem(position);
        ViewHolder viewHolder;
        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {
            int type = getItemViewType(position);
            convertView = getInflatedLayoutForType(type, parent, false);
            viewHolder = new ViewHolder();
            //convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie_relative, parent, false);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvMovieName);
            viewHolder.tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);
            viewHolder.ivPoster = (ImageView) convertView.findViewById(R.id.ivPosterImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Lookup view for data population
        viewHolder.ivPoster.setImageResource(0);
        // Populate the data into the template view using the data object
        if (viewHolder.tvTitle != null)
            viewHolder.tvTitle.setText(movie.title);
        if (viewHolder.tvDesc != null)
            viewHolder.tvDesc.setText(movie.getOverview());
        String imageUri = null;
        if (isPortrait(getContext())) {
            imageUri = movie.getPosterImageUrl();
        } else {
            imageUri = movie.getBackdropPath();
            //     Picasso.with(getContext()).load(imageUri).fit().centerInside().into(viewHolder.ivPoster);
        }
        Picasso.with(getContext()).load(imageUri).placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder_error).transform(new RoundedCornersTransformation(10, 10)).into(viewHolder.ivPoster);
        viewHolder.ivPoster.setTag(position);


        //ivPoster.setBackground(movie.getPosterImageUrl());
        // Return the completed view to render on screen
        Log.d("MoviesAdapter", "position is :" + position);
        int type = getItemViewType(position);
        if (type == 1) {
            viewHolder.ivPoster.setOnClickListener(
                    new AdapterView.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int position = (Integer) view.getTag();
                            Movie movie = getItem(position);
                            Intent detailIntent = new Intent(view.getContext(), VideoActivity.class);
                            detailIntent.putExtra("movie", movie);
                            view.getContext().startActivity(detailIntent);
                        }
                    });
        } else {
            viewHolder.ivPoster.setOnClickListener(
                    new AdapterView.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int position = (Integer) view.getTag();
                            Movie movie = getItem(position);
                            Intent detailIntent = new Intent(view.getContext(), DetailActivity.class);
                            detailIntent.putExtra("movie", movie);
                            view.getContext().startActivity(detailIntent);
                        }
                    });
        }
        return convertView;
    }

    private View getInflatedLayoutForType(int type, ViewGroup parent, boolean b) {
        switch (type) {
            case 0:
                return LayoutInflater.from(getContext()).inflate(R.layout.item_movie_relative, parent, b);
            case 1:
                return LayoutInflater.from(getContext()).inflate(R.layout.item_movie_banner_relative, parent, b);
            default:
                return LayoutInflater.from(getContext()).inflate(R.layout.item_movie_relative, parent, b);
        }
    }

    private boolean isPortrait(Context context) {
        int orientation = context.getResources().getConfiguration().orientation;
        switch (orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                return false;
            case Configuration.ORIENTATION_PORTRAIT:
                return true;
            default:
                return true;
        }
    }

}
