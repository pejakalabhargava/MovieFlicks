package com.example.bkakran.flicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by bkakran on 5/17/16.
 */
public class Movie implements Serializable {
    public String title;
    public String posterImageUrl;
    public String popularity;
    public String overview;
    public String backdropPath;
    public float voteAverage;
    public String id;
    public String videoId;


    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterImageUrl = jsonObject.getString("poster_path");
        this.title = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.popularity = jsonObject.getString("popularity");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.voteAverage = (float) jsonObject.getDouble("vote_average");
        this.id = jsonObject.getString("id");
    }

    public static ArrayList<Movie> fromJsonArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<>();
        for (int x = 0; x < array.length(); x++) {
            try {
                results.add(new Movie(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    public Movie(String title, String posterImageUrl, String popularity, String overview) {
        this.title = title;
        this.posterImageUrl = posterImageUrl;
        this.popularity = popularity;
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterImageUrl() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterImageUrl);
    }

    public void setPosterImageUrl(String posterImageUrl) {
        this.posterImageUrl = posterImageUrl;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public static ArrayList<Movie> getFakeData() {
        ArrayList<Movie> movies = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            movies.add(new Movie("Civil War", "", "70", ""));
            movies.add(new Movie("Batman vs Superman", "", "40", ""));
        }
        return movies;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
