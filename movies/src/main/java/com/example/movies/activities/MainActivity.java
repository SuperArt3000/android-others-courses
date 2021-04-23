package com.example.movies.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movies.R;
import com.example.movies.data.MovieAdapter;
import com.example.movies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movies;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movies = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        getMovies();
    }

    private void getMovies() {
        String url = "http://www.omdbapi.com/?apikey=86d7a3ae&s=superman";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("Search");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String title = jsonObject.getString("Title");
                    String year = jsonObject.getString("Year");
                    String posterUrl = jsonObject.getString("Poster");
                    Movie movie = new Movie();
                    movie.setTitle(title);
                    movie.setYear(year);
                    movie.setPosterUrl(posterUrl);
                    movies.add(movie);
                }
                movieAdapter = new MovieAdapter(MainActivity.this, movies);
                recyclerView.setAdapter(movieAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);
        requestQueue.add(request);
    }
}