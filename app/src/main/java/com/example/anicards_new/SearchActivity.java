package com.example.anicards_new;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.anicards_new.ui.Anime;
import com.example.anicards_new.ui.MyRecyclerViewAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    RecyclerAdapter adapter;
    RecyclerView recyclerView;

    static ArrayList<Animes> animes;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        animes = new ArrayList<>();

        bottomNavigation = findViewById(R.id.bottom_navigation);

        final ImageButton search = findViewById(R.id.search);

        final TextView txtResult = findViewById(R.id.results);

        final RequestQueue mQueue = Volley.newRequestQueue(this);


        // Evento no clique de cada menu item
        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_item_profile:
                                Log.d("MENU_DEBUG", "onNavigationItemSelected: " + "Profile");
                                /*openFragment(HomeFragment.newInstance("", ""));*/
                                startHomeAct();
                                return true;
                            case R.id.menu_item_payments:
                                Log.d("MENU_DEBUG", "onNavigationItemSelected: " + "Payments");
                                /*openFragment(SearchActivity.newInstance("", ""));*/
                                startSearchAct();
                                return true;
                            case R.id.menu_item_saved:
                                Log.d("MENU_DEBUG", "onNavigationItemSelected: " + "Saved");
                                /*openFragment(ProfileActivity.newInstance("", ""));*/
                                startProfileAct();
                                return true;
                        }
                        return false;
                    }
                });


        //Criação de feed de recomendados
        //url a dar fetch
        final String url = "https://kitsu.io/api/edge/anime?filter[categories]=new";

        //Chamada da API e interpretaçao do resultado
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {


                        JSONObject data = jsonArray.getJSONObject(i);

                        JSONObject attributes = data.getJSONObject("attributes");

                        JSONObject pic = attributes.getJSONObject("posterImage");
                        String picUrl = pic.getString("original");
                        String title = attributes.getString("canonicalTitle");
                        String synopsis = attributes.getString("synopsis");
                        String rating = attributes.getString("averageRating") + "%";
                        String ageRate = attributes.getString("ageRatingGuide");
                        String launchDate = attributes.getString("startDate");
                        String endDate = attributes.getString("endDate");
                        String episodeLength = attributes.getString("episodeLength") + "mins";


                        Log.d("title", title);
                        Log.d("syn", synopsis);
                        Log.d("Url", picUrl);
                        Log.d("rating", rating);
                        Log.d("ageRate", ageRate);
                        Log.d("launchDate", launchDate);
                        Log.d("endDate", endDate);
                        Log.d("episodeLength", episodeLength);


                        animes.add(new Animes(title, synopsis, picUrl, rating, ageRate, launchDate, endDate, episodeLength));

                        Log.d("TAG", "onCreate: " + animes);


                    }

                    search();
                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(request);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animes = new ArrayList<>();

                //chamada de campo de texto
                EditText query = findViewById(R.id.queryText);
                String queryTxt = query.getText().toString();


                //toast do termo de pesquisa
                String message = "Showing results for " + queryTxt;
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();

                //url a dar fetch
                final String url = "https://kitsu.io/api/edge/anime?filter[text]=" + queryTxt;

                //Chamada da API e interpretaçao do resultado
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {


                                JSONObject data = jsonArray.getJSONObject(i);

                                JSONObject attributes = data.getJSONObject("attributes");

                                JSONObject pic = attributes.getJSONObject("posterImage");
                                String picUrl = pic.getString("original");
                                String title = attributes.getString("canonicalTitle");
                                String synopsis = attributes.getString("synopsis");
                                String rating = attributes.getString("averageRating") + "%";
                                String ageRate = attributes.getString("ageRatingGuide");
                                String launchDate = attributes.getString("startDate");
                                String endDate = attributes.getString("endDate");
                                String episodeLength = attributes.getString("episodeLength") + "mins";


                                Log.d("title", title);
                                Log.d("syn", synopsis);
                                Log.d("Url", picUrl);
                                Log.d("rating", rating);
                                Log.d("ageRate", ageRate);
                                Log.d("launchDate", launchDate);
                                Log.d("endDate", endDate);
                                Log.d("episodeLength", episodeLength);


                                animes.add(new Animes(title, synopsis, picUrl, rating, ageRate, launchDate, endDate, episodeLength));

                                Log.d("TAG", "onCreate: " + animes);


                            }

                            search();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                mQueue.add(request);

            }
        });


    }

    public void search() {
        recyclerView = findViewById(R.id.myRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(this, animes);
        recyclerView.setAdapter(adapter);

    }

    public void startHomeAct() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

    }

    public void startProfileAct() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);

    }

    public void startSearchAct() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);

    }
}
