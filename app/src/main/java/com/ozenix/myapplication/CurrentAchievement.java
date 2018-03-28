package com.ozenix.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CurrentAchievement extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<Achievement> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_achievement);

        //remplir la ville
        ajouterVilles();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //pour adapter en grille comme une RecyclerView, avec 2 cellules par ligne
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        //puis créer un MyAdapter, lui fournir notre liste de villes.
        //cet adapter servira à remplir notre recyclerview
        recyclerView.setAdapter(new Adapter(cities));
    }

    private void ajouterVilles() {
        cities.add(new Achievement("France","http://www.telegraph.co.uk/travel/destination/article130148.ece/ALTERNATES/w620/parisguidetower.jpg"));
        cities.add(new Achievement("Angleterre","http://www.traditours.com/images/Photos%20Angleterre/ForumLondonBridge.jpg"));
        cities.add(new Achievement("Allemagne","http://tanned-allemagne.com/wp-content/uploads/2012/10/pano_rathaus_1280.jpg"));
        cities.add(new Achievement("Espagne","http://www.sejour-linguistique-lec.fr/wp-content/uploads/espagne-02.jpg"));
        cities.add(new Achievement("Italie","http://retouralinnocence.com/wp-content/uploads/2013/05/Hotel-en-Italie-pour-les-Vacances2.jpg"));
        cities.add(new Achievement("Russie","http://www.choisir-ma-destination.com/uploads/_large_russie-moscou2.jpg"));
    }

}
