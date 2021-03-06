package com.moviles.domiciliosmoviles.Services;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import com.moviles.domiciliosmoviles.MainActivity;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GPSIntentService extends IntentService {
    // GETPOSITION accion para traer la posicion actual
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String GETPOSITION = "com.moviles.domiciliosmoviles.Services.action.GETPOS";

    public GPSIntentService() {
        super("GPSIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (GETPOSITION.equals(action)) {
                handleGPSAction();
            }
        }
    }

    private void handleGPSAction() {
        LocationManager mlocManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location mLastLocation = mlocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(mLastLocation!=null){
            Intent result = new Intent(MainActivity.GPS_FILTER);
            result.putExtra("position", mLastLocation.getLatitude() + " - "+mLastLocation.getLongitude());
            sendBroadcast(result);
        } else{
            Intent result = new Intent(MainActivity.GPS_FILTER);
            result.putExtra("position", "Not found");
            sendBroadcast(result);
        }

    }
}