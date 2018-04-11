package com.ozenix.achieveme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ozenix.achieveme.fragments.AchievementFragment;
import com.ozenix.achieveme.fragments.MainMenuFragment;
import com.ozenix.achieveme.fragments.MapFragment;
import com.ozenix.achieveme.fragments.ProfileFragment;
import com.ozenix.achieveme.fragments.TutorialFragment;
import com.ozenix.achieveme.mechanisms.Profile;

public class MainMenu extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    /*Button button_logout;
    Button button_map;
    Button button_profile;*/
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

            //Header porfile setup

            /*ImageView imageViewPic = findViewById(R.id.picUserGoogle);
            imageViewPic.setImageURI(MainMenu.profile.getImageUrl());

            TextView textViewName = findViewById(R.id.usernameGoogle);
            textViewName.setText(MainMenu.profile.getUsername());

            TextView textViewMail = findViewById(R.id.mailGoogle);
            textViewMail.setText(MainMenu.profile.getEmail());*/


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
        setContentView(R.layout.main);

        /*button_logout = findViewById(R.id.logout_btn);
        button_map = findViewById(R.id.map_btn);
        button_profile = findViewById(R.id.profile_btn);*/



        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(MainMenu.this, MainActivity.class));
                }
            }
        };

        /*button_logout.setOnClickListener(new View.OnClickListener() {
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
        });*/

        mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        NavigationView mDrawer = (NavigationView) findViewById(R.id.navigationView);
        setupDrawerContent(mDrawer);

//        mDrawer.setCheckedItem(R.id.menu);

        //Launch the tutorial just the first time !
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);
        if (!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(getString(R.string.pref_previously_started), Boolean.TRUE);
            edit.apply();
            showTutorial();
        }
    }

    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
        }

    private void showTutorial() {
        Intent intent = Tutorial.makeIntent(MainMenu.this);
        startActivity(intent);
    }

    public void selectItemDrawer(MenuItem menuItem) {
        android.support.v4.app.Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()){
            case (R.id.menu):
                fragmentClass = MainMenuFragment.class;
                break;
            case (R.id.profil):
                fragmentClass = ProfileFragment.class;
                break;
            case (R.id.succes):
                fragmentClass = AchievementFragment.class;
                Intent intentSuccess = ProfileAchievement.makeIntent(MainMenu.this);
                startActivity(intentSuccess);
                break;
            case (R.id.carte):
                fragmentClass = MapFragment.class;
                Intent intentMap = MapsActivity.makeIntent(MainMenu.this);
                startActivity(intentMap);
                break;
            case (R.id.tutoriel):
                //Need to find a proper way to close the fragment. Working atm, will look for it later BP
                fragmentClass = TutorialFragment.class;
                showTutorial();
                finish();
                break;
            case (R.id.menu_logout):
                mAuth.signOut();
            default:
                fragmentClass = MainMenuFragment.class;
        }
        try {
            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayoutContent,fragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawers();

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return true;
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, MainMenu.class);
    }
}
