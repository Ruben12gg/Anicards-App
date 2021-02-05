package com.example.anicards_new;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class ToWatchActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    ToWatchAdapter adapter02;
    RecyclerView recyclerView02;

    ArrayList<AnimeToWatch> animes02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_watch);

        GlobalUserId globalUserId = (GlobalUserId) getApplicationContext();
        String userId = globalUserId.getUserIdGlobal();
        Log.d("USERID", userId);

        animes02 = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("users").document(userId).collection("collectionToWatch")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("ACTIVITY", document.getId() + " => " + document.getData());

                                String title = document.getString("title");
                                String picUrl = document.getString("picUrl");
                                String date = document.getString("date");

                                animes02.add(new AnimeToWatch(title, picUrl, date));

                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                        recyclerCall();
                    }
                });


    }

    private void recyclerCall() {

        recyclerView02 = findViewById(R.id.rvToWatch);
        recyclerView02.setHasFixedSize(true);
        recyclerView02.setLayoutManager(new LinearLayoutManager(this));
        adapter02 = new ToWatchAdapter(this, animes02);
        recyclerView02.setAdapter(adapter02);
    }
}
