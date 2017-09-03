package net.glm.fusedlocationprovider;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    static final String MY_LOGS = "My_Logs";
    static final Integer PERMISSION_LOCATION_REQUEST_CODE = 102;
    private boolean permissionGranted = false;

    TextView tvLatitude, tvLongitude;

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private FusedLocationProviderApi locationProviderApi = LocationServices.FusedLocationApi;

    private Double myLatitude;
    private Double myLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLatitude = (TextView) findViewById(R.id.tvLatitude);
        tvLongitude = (TextView) findViewById(R.id.tvLongitude);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        Log.d(MY_LOGS, " - In onCreate the result is : " + googleApiClient.toString());

        locationRequest = new LocationRequest();
        locationRequest.setInterval(2 * 1000);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(MY_LOGS, " - In onConnected  "  + googleApiClient.isConnected());
        requestLocationUpdate();
        //Log.d(MY_LOGS, " - In onConnected  After run requestLocationUpdate "  + googleApiClient.isConnected());


    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(MY_LOGS, " - In onConnectionSuspended  " );


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(MY_LOGS, " - In onConnectionFailed  " + connectionResult );

    }

    @Override
    public void onLocationChanged(Location location) {
        myLatitude = location.getLatitude();
        myLongitude = location.getLongitude();
        tvLatitude.setText(" Latitude : " + myLatitude.toString());
        tvLongitude.setText(" Longitude : " + myLongitude.toString());


    }

    private void requestLocationUpdate() {
        Log.d(MY_LOGS, " - In requestLocationUpdate number 1");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
          
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_LOCATION_REQUEST_CODE);
            }else permissionGranted = true;

        }
        Log.d(MY_LOGS, " - In requestLocationUpdate number 2");
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 102){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                permissionGranted = true;
            }else {
                permissionGranted = false;
                Toast.makeText(this, " This App request location Permission to be granted ", Toast.LENGTH_SHORT).show();
                tvLatitude.setText(" Latitude :  Permission not granted");
                tvLongitude.setText(" Longitude :  Permission not granted");

            }
        }
//        if(permissionGranted = true){
//            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        googleApiClient.connect();
        Log.d(MY_LOGS, " - In onStart the result is : " + googleApiClient.isConnected());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(MY_LOGS, " - In onResume the result is : " + googleApiClient.isConnected());
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }
}
