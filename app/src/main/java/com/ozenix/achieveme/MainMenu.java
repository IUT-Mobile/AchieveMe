package com.ozenix.achieveme;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainMenu extends AppCompatActivity {

    Button button_logout;
    Button button_map;
    Button button_profile;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    public static Profile profile;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            /*Log.i("BOB", name);
            Log.i("BOB", email);
            Log.i("BOB", photoUrl.toString());*/

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String userId = user.getUid();

            profile = new Profile(userId, email, name, photoUrl);

            mDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(profile.getUserId());

            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.exists()) {
                        //create new user
                        mDatabase.child("Name").setValue(profile.getUsername());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_test);

        button_logout = findViewById(R.id.logout_btn);
        button_map = findViewById(R.id.map_btn);
        button_profile = findViewById(R.id.profile_btn);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(MainMenu.this, MainActivity.class));
                }
            }
        };

        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
            }
        });

        button_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenu.this, MapsActivity.class));
            }
        });

        button_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenu.this, ProfileAchievement.class));
            }
        });
    }
}
