package com.ozenix.achieveme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ozenix.achieveme.mechanisms.Achievement;

import android.Manifest;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    protected static final int REQUEST_CHECK_SETTINGS = 1;
    private GoogleMap mMap;

    private DatabaseReference mDatabase;
    private ArrayList<Achievement> availableAchievements = new ArrayList<>();
    private HashMap<String, Marker> markers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        createLocationRequest();
        check();


        //GetDatabaseAchievements
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Achievement").child("Location").child("Europe").child("Achievement").child("France").child("Achievement").child("Aix-en-Provence").child("Achievement");

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i("BOB", dataSnapshot.getKey());

                //for (DataSnapshot achievements : dataSnapshot.getChildren()) {

                String name = dataSnapshot.child("Name").getValue(String.class);
                Double lat = dataSnapshot.child("Lat").getValue(Double.class);
                Double lng = dataSnapshot.child("Lng").getValue(Double.class);

                Achievement achievement = new Achievement(name, lat, lng);

                availableAchievements.add(achievement);

                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(achievement.getLatitude(), achievement.getLongitude()))
                        .title(achievement.getName()));
                markers.put(name, marker);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("Name").getValue(String.class);

                Marker marker = markers.get(name);
                marker.remove();
                markers.remove(name);

                testAchievementGet();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*// Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    public void check() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    protected void createLocationRequest() {
        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.

                //  move the camera to Aix
                LatLng aix = new LatLng(43.526429, 5.445454);
                //LatLngBounds AIX = new LatLngBounds(
                //      new LatLng(43.50, 5.4), new LatLng(43.55, 5.6));
                // Constrain the camera target to the Aix bounds.
                // mMap.setLatLngBoundsForCameraTarget(AIX);
                //mMap.setMinZoomPreference(16.0f);
                // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.526429, 5.445454), 16));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(aix, 10));


            }
        });


        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(MapsActivity.this,
                                REQUEST_CHECK_SETTINGS);

                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                for (Achievement achievement : availableAchievements){
                    Location achievementLocation = new Location(achievement.getName());
                    achievementLocation.setLatitude(achievement.getLatitude());
                    achievementLocation.setLongitude(achievement.getLongitude());

                    if (location.distanceTo(achievementLocation) < 100) {

                        //Ajouter dans la BD
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(MainMenu.profile.getUserId()).child("Achievement");
                        Date date = new Date();
                        mDatabase.child(achievement.getName()).setValue(date.toString());


                        AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this).create();
                        alertDialog.setTitle("Nouveau succès : " + achievement.getName());
                        alertDialog.setMessage("Vous avez découvert un nouveau lieu !");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }

                }



            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 20, locationListener);


    }

    public void testAchievementGet(){
        for (Achievement achievement : availableAchievements){
            Location achievementLocation = new Location(achievement.getName());
            achievementLocation.setLatitude(achievement.getLatitude());
            achievementLocation.setLongitude(achievement.getLongitude());

            if (achievement.getName().equals("Hippopotamus")) {

                //Ajouter dans la BD
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(MainMenu.profile.getUserId()).child("Achievement");
                Date date = new Date();
                mDatabase.child(achievement.getName()).setValue(date.toString());


                AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this).create();
                alertDialog.setTitle("Nouveau succès : " + achievement.getName());
                alertDialog.setMessage("Vous avez découvert un nouveau lieu !");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

        }
    }
    public static Intent makeIntent(Context context) {
        return new Intent(context, MapsActivity.class);
    }
}
