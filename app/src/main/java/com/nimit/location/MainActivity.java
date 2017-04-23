package com.nimit.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String TAG = "com.nimit.location";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int coarseper = ContextCompat.checkSelfPermission(this ,Manifest.permission.ACCESS_COARSE_LOCATION);
        int fineper = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(coarseper!= PackageManager.PERMISSION_GRANTED || fineper != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                                        Manifest.permission.ACCESS_FINE_LOCATION}, 432);
        }
        else
            getLocation();

    }
    void getLocation(){
        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        LocationListener locLis = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, location.getAltitude()+"");
                Log.d(TAG, "onLocationChanged: " + location.getAccuracy());
                Log.d(TAG, "onLocationChanged: "+location.getLatitude());
                Log.d(TAG, "onLocationChanged: "+ location.getLongitude());
                Log.d(TAG, "onLocationChanged: " + location.getSpeed());
                Log.d(TAG, "onLocationChanged: "+ location.getProvider());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 20000, 100, locLis);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locLis);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==432){
            for(int result : grantResults){
                if(result==PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this, "Permission DEnied", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            getLocation();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
