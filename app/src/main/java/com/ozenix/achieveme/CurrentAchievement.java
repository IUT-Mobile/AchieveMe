package com.ozenix.achieveme;

import android.os.Debug;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CurrentAchievement extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<Achievement> cities = new ArrayList<>();

    private DatabaseReference mDatabase;
    private ListView achievementList;
    private ArrayList<String> achievements = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_test);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Achievement");
        achievementList = findViewById(R.id.achievement_list);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, achievements);
        achievementList.setAdapter(arrayAdapter);


        //Contient les cards
        recyclerView = findViewById(R.id.recyclerView);

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //pour adapter en grille comme une RecyclerView, avec 2 cellules par ligne
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));


        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                achievements.add(value);
                cities.add(new Achievement(value,"https://img.ev.mu/images/villes/5580/1605x642/5580.jpg"));
                arrayAdapter.notifyDataSetChanged();

                //puis créer un MyAdapter, lui fournir notre liste de villes.
                //cet adapter servira à remplir notre recyclerview
                recyclerView.setAdapter(new Adapter(cities));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void ajouterVilles() {
        //cities.add(new Achievement("France","https://img.ev.mu/images/villes/5580/1605x642/5580.jpg"));
        //cities.add(new Achievement("Angleterre","https://images.salaun-holidays.com/(Image)-image-Angleterre-Londres-22-it_56320684-09032017.jpg"));
        //cities.add(new Achievement("Allemagne","http://tanned-allemagne.com/wp-content/uploads/2012/10/pano_rathaus_1280.jpg"));
        //cities.add(new Achievement("Espagne","https://thebettervacation.com/wp-content/uploads/2018/02/Park-Guell-Barcelona.jpg"));
        //cities.add(new Achievement("Italie","http://retouralinnocence.com/wp-content/uploads/2013/05/Hotel-en-Italie-pour-les-Vacances2.jpg"));
        //cities.add(new Achievement("Russie","http://www.choisir-ma-destination.com/uploads/_large_russie-moscou2.jpg"));
        //cities.add(new Achievement("Sénas","https://www.provence7.com/wp-content/uploads/2015/08/S%C3%A9nas.-Eglise.-P.-Verlinden.jpg"));

    }

}
