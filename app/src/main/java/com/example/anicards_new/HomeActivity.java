package com.example.anicards_new;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.anicards_new.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    HomeAdapter adapter06;
    RecyclerView recyclerView06;

    ArrayList<AnimeHome> animes06;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GlobalUserId globalUserId = (GlobalUserId) getApplicationContext();
        final String userId = globalUserId.getUserIdGlobal();
        Log.d("USERID", userId);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();


        final Button delete = findViewById(R.id.delete);

        //Lógica de verificação se user é ou não admin para poder limpar o feed
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DocumentReference docRef = db.collection("users").document(userId);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                String admin = document.getString("admin");
                                Log.d("ADMIN?", admin);

                                if (admin.equals("true")){

                                    db.collection("activities")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            Log.d("ACTIVITY00", document.getId() + " => " + document.getData());

                                                            db.collection("activities").document(document.getId())
                                                                    .delete()
                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                            //Toast
                                                                            String message = "The activity feed was successfully cleaned! ";
                                                                            Context context = getApplicationContext();
                                                                            int duration = Toast.LENGTH_SHORT;
                                                                            Toast toast = Toast.makeText(context, message, duration);
                                                                            toast.show();
                                                                            Log.d("TAG", "DocumentSnapshot successfully deleted!");
                                                                        }
                                                                    })
                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Log.w("TAG", "Error deleting document", e);
                                                                        }
                                                                    });
                                                        }
                                                    } else {

                                                    }
                                                }
                                            });


                                } else {
                                    //Toast
                                    String message = "You do NOT have permission to perform this task!";
                                    Context context = getApplicationContext();
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, message, duration);
                                    toast.show();

                                    Log.d("NO", "You have no Permission to perfomr this action");
                                }


                                Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d("TAG", "No such document");
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });



            }
        });


        animes06 = new ArrayList<>();



        //Obtenção dos dados que geram o feed
        db.collection("activities")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("ACTIVITY", document.getId() + " => " + document.getData());

                                String title = document.getString("title");
                                Log.d("TITLE", title);
                                String author = document.getString("author");
                                String collection = document.getString("collection");
                                String date = document.getString("date");
                                String pfp = document.getString("pfp");
                                String picUrl = document.getString("picUrl");

                                String activity = author + " added " + title + " to the " + collection;

                                animes06.add(new AnimeHome(title, author, collection, activity, date, pfp, picUrl));


                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                        recyclerCall();
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
                                Log.d("MENU_DEBUG", "onNavigationItemSelected: " + "Profile");
                                startHomeAct();
                                return true;
                            case R.id.menu_item_payments:
                                Log.d("MENU_DEBUG", "onNavigationItemSelected: " + "Payments");
                                startSearchAct();
                                return true;
                            case R.id.menu_item_saved:
                                Log.d("MENU_DEBUG", "onNavigationItemSelected: " + "Saved");
                                startProfileAct();
                                return true;
                        }
                        return false;
                    }
                });


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


    private void recyclerCall() {

        recyclerView06 = findViewById(R.id.rvHome);
        recyclerView06.setHasFixedSize(true);
        recyclerView06.setLayoutManager(new LinearLayoutManager(this));
        adapter06 = new HomeAdapter(this, animes06);
        recyclerView06.setAdapter(adapter06);
    }
}
