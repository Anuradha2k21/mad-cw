package com.example.mad_cw.map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.mad_cw.R;
import com.example.mad_cw.course.CourseRecyclerView;
import com.example.mad_cw.user.ProfileActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap branchMap;
    private final int FINE_PERMISSION_CODE = 1;
    private Location currentLocation;
    private Marker userMarker;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_map);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.menu_home) {
                startActivity(new Intent(getApplicationContext(), CourseRecyclerView.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.menu_map) {
                return true;
            } else if (itemId == R.id.menu_profile) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(MapActivity.this);

    }

    private void getLastLocation(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null){
                currentLocation = location;

                // Ensure map is ready and then call onMapReady
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                if (mapFragment != null) {
                    mapFragment.getMapAsync(MapActivity.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        branchMap = googleMap;

        branchMap.setOnMarkerClickListener(marker -> {
            // Skip showing bottom sheet if the clicked marker is the current location marker (userMarker)
            if (marker.equals(userMarker)) {
                return false; // Returning false allows the default InfoWindow to display
            }

            // Show bottom sheet for other markers
            showBottomSheet(marker);
            return true; // Returning true prevents default InfoWindow from showing
        });

        // Enable current location layer
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            branchMap.setMyLocationEnabled(true);
        }

        // Define branch locations and their names
        LatLng[] branchLocations = {
                new LatLng(5.9452665280030885, 80.5488137366161), // Mathara
                new LatLng(7.206462993333538, 79.84133448805265), // Negambo
                new LatLng(6.906508212834678, 79.870848151968), // Colombo
                new LatLng(7.253198010400344, 80.34310112128072), // Kegalle
                new LatLng(9.713426441481374, 80.01801233934545), // Jaffna
                new LatLng(6.801539666520882, 79.92087583662209), // Piliyandala
                new LatLng(6.837850322950499, 79.96523089139943), // Kottawa
                new LatLng(7.2687431081903435, 80.60261265742827) // Kandy
        };

        String[] branchNames = {
                "Mathara",
                "Negambo",
                "Colombo",
                "Kegalle",
                "Jaffna",
                "Piliyandala",
                "Kottawa",
                "Kandy"
        };

        // Add markers for each branch location
        for (int i = 0; i < branchLocations.length; i++) {
            branchMap.addMarker(new MarkerOptions()
                    .position(branchLocations[i])
                    .title(branchNames[i]));
        }

        // Calculate the distance from current location to each branch and find the nearest one
        LatLng nearestBranchLocation = null;
        String nearestBranchName = null;
        double minDistance = Double.MAX_VALUE;

        if (currentLocation != null) {
            LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

            // Calculate distance to each branch location
            for (int i = 0; i < branchLocations.length; i++) {
                double distance = calculateDistance(currentLatLng, branchLocations[i]);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestBranchLocation = branchLocations[i];
                    nearestBranchName = branchNames[i];
                }
            }

            // Move camera to the nearest branch location and add marker
            if (nearestBranchLocation != null && nearestBranchName != null) {

                // Add an InfoWindow to the nearest branch marker with a message
                Marker nearestBranchMarker = branchMap.addMarker(new MarkerOptions()
                        .position(nearestBranchLocation)
                        .title("Nearest Branch: " + nearestBranchName)
                        .snippet("This is the nearest branch to your current location."));

                // Focus camera on the nearest branch with zoom level
                branchMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nearestBranchLocation, 12));

                // Show an InfoWindow on the nearest branch marker
                assert nearestBranchMarker != null;
                nearestBranchMarker.showInfoWindow();

            }

            // Optionally, add a polyline from the user's current location to the nearest branch
            if (nearestBranchLocation != null) {
                branchMap.addPolyline(new PolylineOptions()
                        .add(currentLatLng)
                        .add(nearestBranchLocation)
                        .color(Color.BLUE) // Use blue color for the polyline
                        .width(5)); // Set the width of the polyline
            }

            // Add a custom blue marker for the user's current location
            MarkerOptions userMarkerOptions = new MarkerOptions()
                    .position(currentLatLng)
                    .title("My Position")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)); // Use blue color marker

            userMarker = branchMap.addMarker(userMarkerOptions);
        }

        // Enable zoom controls and compass
        branchMap.getUiSettings().setZoomControlsEnabled(true);
        branchMap.getUiSettings().setCompassEnabled(true);

    }

    /**
     * Calculate the distance between two LatLng points using the Haversine formula.
     * @param point1 First point
     * @param point2 Second point
     * @return Distance in kilometers
     */
    private double calculateDistance(LatLng point1, LatLng point2) {
        final double EARTH_RADIUS = 6371.0; // Radius of the Earth in kilometers

        double lat1 = point1.latitude;
        double lon1 = point1.longitude;
        double lat2 = point2.latitude;
        double lon2 = point2.longitude;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }
            else {
                Toast.makeText(this, "Location Permission is denied, Please allow the permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showBottomSheet(Marker marker) {
        // Create a BottomSheetDialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.activity_info_window, null);
        bottomSheetDialog.setContentView(view);

        // Get references to UI elements
        TextView titleTextView = view.findViewById(R.id.info_window_title);
        ImageButton directionsButton = view.findViewById(R.id.directions_button);

        // Set the text for the bottom sheet UI elements
        titleTextView.setText(marker.getTitle());

        // Set a click listener for the directions button
        directionsButton.setOnClickListener(v -> {
            LatLng markerPosition = marker.getPosition();
            openGoogleMapsDirections(markerPosition.latitude, markerPosition.longitude);
        });

        // Show the bottom sheet
        bottomSheetDialog.show();
    }

    private void openGoogleMapsDirections(double destinationLat, double destinationLng) {

        Uri uri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=" + destinationLat + "," + destinationLng);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}