package com.example.bkakran.flicks;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.bkakran.flicks.adapters.MoviesAdapter;
import com.example.bkakran.flicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import cz.msebera.android.httpclient.Header;

public class MoviesActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    MoviesAdapter moviesAdapter;
    private SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                moviesAdapter.clear();
                getMovieData();
                swipeContainer.setRefreshing(false);

            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        ListView lvMovies = (ListView) findViewById(R.id.lvMovies);
        movies = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(this, movies);
        if (lvMovies != null) {
            lvMovies.setAdapter(moviesAdapter);
        }
        getMovieData();
    }


    private void getMovieData() {
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray movieJsonResults = response.getJSONArray("results");
                    ArrayList<Movie> movieResults = Movie.fromJsonArray(movieJsonResults);
                    populateVideoSources(movieResults);
                    Collections.shuffle(movieResults);
                    movies.addAll(movieResults);
                    moviesAdapter.notifyDataSetChanged();
                    Log.d("DEBUG", movieJsonResults.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            private void populateVideoSources(final ArrayList<Movie> movieJsonResults) {
                String trailerUrl = "https://api.themoviedb.org/3/movie/%s/trailers?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

                for (final Movie m : movieJsonResults) {
                    AsyncHttpClient client = new AsyncHttpClient();
                    trailerUrl = String.format(trailerUrl, m.getId());
                    client.get(trailerUrl, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                JSONArray movieTrailers = response.getJSONArray("youtube");
                                for (int x = 0; x < movieTrailers.length(); x++) {
                                    try {
                                        JSONObject jsonObj = movieTrailers.getJSONObject(x);
                                        if (jsonObj.getString("type").equals("Trailer")) {
                                            m.setVideoId(jsonObj.getString("source"));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                Log.d("DEBUG", movieTrailers.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                        }
                    });

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }
}
