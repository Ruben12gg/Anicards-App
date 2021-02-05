package com.example.anicards_new;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;


import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AnimeActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime);

        final String title = getIntent().getStringExtra("title");
        String title02 = getIntent().getStringExtra("title");
        String releaseDate = getIntent().getStringExtra("launchDate");
        String endDate = getIntent().getStringExtra("endDate");
        String episodeLength = getIntent().getStringExtra("episodeLength");
        String rating = getIntent().getStringExtra("rating");
        String ageRating = getIntent().getStringExtra("ageRate");
        String synopsis = getIntent().getStringExtra("synopsis");
        final String picUrl = getIntent().getStringExtra("picUrl");

        TextView tvTitle = findViewById(R.id.animeTitle);
        tvTitle.setText(title);

        TextView tvTitle02 = findViewById(R.id.animeTitle02);
        tvTitle02.setText(title);

        TextView tvRelease = findViewById(R.id.release);
        tvRelease.setText(releaseDate);

        TextView tvEnd = findViewById(R.id.end);
        tvEnd.setText(endDate);

        TextView tvLength = findViewById(R.id.length);
        tvLength.setText(episodeLength);

        TextView tvRating = findViewById(R.id.rating);
        tvRating.setText(rating);

        TextView tvAge = findViewById(R.id.ageRating);
        tvAge.setText(ageRating);

        TextView tvSyn = findViewById(R.id.synopsis);
        tvSyn.setText(synopsis);

        ImageView cover = findViewById(R.id.cover);
        Picasso.get().load(picUrl).into(cover);

        Button add = findViewById(R.id.add);
        Button disliked = findViewById(R.id.addDisliked);
        Button seen = findViewById(R.id.addSeen);
        Button toWatch = findViewById(R.id.addToWatch);

        //Criação de um Map com as informações do anime selecionado para adicionar ás coleções
        final Map<String, Object> selectedAnime = new HashMap<>();
        selectedAnime.put("title", title);
        selectedAnime.put("picUrl", picUrl);
        Date currentDate01 = new Date();
        String date01 = "Added " + DateFormat.getInstance().format(currentDate01);
        selectedAnime.put("date", date01);

        //Chamar userId da variavel global
        GlobalUserId globalUserId = (GlobalUserId) getApplicationContext();
        final String userId = globalUserId.getUserIdGlobal();
        final String author = globalUserId.getUserName();
        final String pfp = globalUserId.getUserPfp();

        Log.d("USERID", userId);

        //dados para gerar atividade
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final Map<String, Object> activityAnime = new HashMap<>();
        activityAnime.put("title", title);
        activityAnime.put("picUrl", picUrl);

        Date currentDate = new Date();
        String date = DateFormat.getInstance().format(currentDate);
        Log.d("DATE", date);
        activityAnime.put("date", date);

        activityAnime.put("author", author);
        activityAnime.put("pfp", pfp);


        //Add LIKED
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("CLICK", "add collection");

                final FirebaseFirestore db = FirebaseFirestore.getInstance();


                db.collection("users").document(userId).collection("collectionLiked")
                        .add(selectedAnime)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error adding document", e);
                            }
                        });


                //Add activity
                activityAnime.put("collection", "Liked Collection");

                db.collection("activities")
                        .add(activityAnime)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error adding document", e);
                            }
                        });

                //Toast
                String message = "The anime was added to your Liked Collection";
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();


                //atualizar contagem de animes do perfil
                DocumentReference docRef = db.collection("users").document(userId);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                String animeCount = document.getString("animeCount");

                                int animeCountVal = Integer.parseInt(animeCount);

                                int animeCountTotal = animeCountVal + 1;

                                String animeCountTotalStr = String.valueOf(animeCountTotal);

                                Log.d("ANIMECOUNT", animeCountTotalStr);

                                Map<String, Object> user = new HashMap<>();
                                user.put("animeCount", animeCountTotalStr);


                                db.collection("users").document(userId)
                                        .update(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("TAG", "DocumentSnapshot successfully written!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("TAG", "Error writing document", e);
                                            }
                                        });


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

        //Add DISLIKED
        disliked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("CLICK", "add collection");

                final FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("users").document(userId).collection("collectionDisliked")
                        .add(selectedAnime)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error adding document", e);
                            }
                        });

                //Add activity
                activityAnime.put("collection", "Disliked Collection");

                db.collection("activities")
                        .add(activityAnime)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error adding document", e);
                            }
                        });

                //Toast
                String message = "The anime was added to your Disliked Collection";
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();

                //atualizar contagem de animes do perfil
                DocumentReference docRef = db.collection("users").document(userId);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                String animeCount = document.getString("animeCount");

                                int animeCountVal = Integer.parseInt(animeCount);

                                int animeCountTotal = animeCountVal + 1;

                                String animeCountTotalStr = String.valueOf(animeCountTotal);

                                Log.d("ANIMECOUNT", animeCountTotalStr);

                                Map<String, Object> user = new HashMap<>();
                                user.put("animeCount", animeCountTotalStr);


                                db.collection("users").document(userId)
                                        .update(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("TAG", "DocumentSnapshot successfully written!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("TAG", "Error writing document", e);
                                            }
                                        });


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

        //Add SEEN
        seen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("CLICK", "add collection");

                final FirebaseFirestore db = FirebaseFirestore.getInstance();


                db.collection("users").document(userId).collection("collectionSeen")
                        .add(selectedAnime)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error adding document", e);
                            }
                        });

                //Add activity
                activityAnime.put("collection", "Seen Collection");

                db.collection("activities")
                        .add(activityAnime)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error adding document", e);
                            }
                        });

                //Toast
                String message = "The anime was added to your Seen Collection";
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();

                //atualizar contagem de animes do perfil
                DocumentReference docRef = db.collection("users").document(userId);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                String animeCount = document.getString("animeCount");

                                int animeCountVal = Integer.parseInt(animeCount);

                                int animeCountTotal = animeCountVal + 1;

                                String animeCountTotalStr = String.valueOf(animeCountTotal);

                                Log.d("ANIMECOUNT", animeCountTotalStr);

                                Map<String, Object> user = new HashMap<>();
                                user.put("animeCount", animeCountTotalStr);


                                db.collection("users").document(userId)
                                        .update(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("TAG", "DocumentSnapshot successfully written!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("TAG", "Error writing document", e);
                                            }
                                        });


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


        //Add TOWATCH
        toWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("CLICK", "add collection");

                final FirebaseFirestore db = FirebaseFirestore.getInstance();


                db.collection("users").document(userId).collection("collectionToWatch")
                        .add(selectedAnime)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error adding document", e);
                            }
                        });

                //Add activity
                activityAnime.put("collection", "To Watch Collection");

                db.collection("activities")
                        .add(activityAnime)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error adding document", e);
                            }
                        });

                //Toast
                String message = "The anime was added to your To Watch Collection";
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();

                //atualizar contagem de animes do perfil
                DocumentReference docRef = db.collection("users").document(userId);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                String animeCount = document.getString("animeCount");

                                int animeCountVal = Integer.parseInt(animeCount);

                                int animeCountTotal = animeCountVal + 1;

                                String animeCountTotalStr = String.valueOf(animeCountTotal);

                                Log.d("ANIMECOUNT", animeCountTotalStr);

                                Map<String, Object> user = new HashMap<>();
                                user.put("animeCount", animeCountTotalStr);


                                db.collection("users").document(userId)
                                        .update(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("TAG", "DocumentSnapshot successfully written!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("TAG", "Error writing document", e);
                                            }
                                        });


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


    }
}
