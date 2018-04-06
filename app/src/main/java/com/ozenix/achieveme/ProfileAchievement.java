package com.ozenix.achieveme;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ProfileAchievement extends AppCompatActivity {

    private Profile profile;

    private DatabaseReference mDatabase;
    private ListView achievementList;
    private ArrayList<String> achievements = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_achievement);

        /*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);*/

        profile = new Profile("1", "Guilhem");


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Profile");

        mDatabase.orderByChild("Name").equalTo("Guilhem").once("value",snapshot => {
            const userData = snapshot.val();
            if (userData){
                console.log("exists!");
            }
        });

        mDatabase.push().setValue("Guilhem");


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Profile").child(profile.getUserId()).child("Achievement");
        achievementList = findViewById(R.id.achievement_list);




        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, achievements);
        achievementList.setAdapter(arrayAdapter);

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                achievements.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                achievements.remove(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
