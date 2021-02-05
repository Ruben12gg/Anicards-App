package com.example.anicards_new;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.common.collect.ObjectArrays;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigation;
    TextView tvName;
    TextView tvAnimeCount;
    ImageView ivPfp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final Button logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        GlobalUserId globalUserId = (GlobalUserId) getApplicationContext();
        String userId = globalUserId.getUserIdGlobal();
        Log.d("USERID", userId);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Obtenção de dados do perfil
        DocumentReference docRef = db.collection("users").document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String name = document.getString("name");
                        tvName = findViewById(R.id.userName);
                        tvName.setText(name);

                        String animeCount = document.getString("animeCount");
                        tvAnimeCount = findViewById(R.id.animeCount);
                        tvAnimeCount.setText(animeCount);

                        String pfpUrl = document.getString("pfp");
                        ivPfp = findViewById(R.id.pfp);
                        Picasso.get().load(pfpUrl).into(ivPfp);

                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });


        bottomNavigation = findViewById(R.id.bottom_navigation);

        // Evento no clique de cada menu item
        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_item_profile:
                                Log.d("MENU_DEBUG", "onNavigationItemSelected: " + "Home");

                                startHomeAct();
                                return true;
                            case R.id.menu_item_payments:
                                Log.d("MENU_DEBUG", "onNavigationItemSelected: " + "Search");

                                startSearchAct();
                                return true;
                            case R.id.menu_item_saved:
                                Log.d("MENU_DEBUG", "onNavigationItemSelected: " + "Profile");

                                startProfileAct();
                                return true;
                        }
                        return false;
                    }
                });

        //Criação da navegação das coleções
        ImageButton seen = findViewById(R.id.seen);
        ImageButton toWatch = findViewById(R.id.toWatch);
        ImageButton liked = findViewById(R.id.like);
        ImageButton disliked = findViewById(R.id.dislike);

        seen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Click on", "SEEN");
                getSeen();

            }
        });

        toWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Click on", "TOWATCH");
                getToWatch();

            }
        });

        liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Click on", "LIKED");
                getLiked();

            }
        });

        disliked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Click on", "DISLIKED");
                getDisliked();

            }
        });


    }

    private void logout() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

        //Toast
        String message = "You Logged out of your account.";
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private void getSeen() {
        Intent i = new Intent(this, SeenActivity.class);
        startActivity(i);
    }

    private void getToWatch() {
        Intent i = new Intent(this, ToWatchActivity.class);
        startActivity(i);

    }

    private void getLiked() {
        Intent i = new Intent(this, LikedActivity.class);
        startActivity(i);

    }

    private void getDisliked() {
        Intent i = new Intent(this, DislikedActivity.class);
        startActivity(i);

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
