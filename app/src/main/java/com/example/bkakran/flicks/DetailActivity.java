package com.example.bkakran.flicks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.bkakran.flicks.models.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        final Movie movie = (Movie) getIntent().getSerializableExtra("movie");
        RatingBar ratingBar = (RatingBar) findViewById(R.id.movieRatingBar);
        if (ratingBar != null) {
            ratingBar.setRating((float) (movie.getVoteAverage()/2.00));
        }
        TextView popularityView = (TextView) findViewById(R.id.tvPopularityVal);
        if (popularityView != null) {
            popularityView.setText(movie.getPopularity());
        }
        TextView tvSynopsisView = (TextView) findViewById(R.id.tvSynopsisVal);
        if (tvSynopsisView != null) {
            tvSynopsisView.setText(movie.getOverview());
        }
        ImageView imagePoster = (ImageView) findViewById(R.id.ivPosterImage);
        Picasso.with(this).load(movie.getPosterImageUrl()).placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder_error).into(imagePoster);

        ImageView image = (ImageView) findViewById(R.id.ivPosterImage);

        image.setOnClickListener(
                new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent detailIntent = new Intent(view.getContext(), VideoActivity.class);
                        detailIntent.putExtra("movie", movie);
                        view.getContext().startActivity(detailIntent);
                    }
                });
    }
}
