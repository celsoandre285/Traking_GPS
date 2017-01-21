package traking.celsoandre.com.br.traking;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Celso André on 21/01/2017.
 */

public class GPS_Service extends Service {
    private LocationListener locationListener;
    private LocationManager locationManager;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //Cria a Location Listener
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //Informa a atualização a cada movimento
                Intent intent = new Intent("location_update");
                Log.i("local", String.valueOf(location.getAltitude()));
                int cont = 1;
                intent.putExtra("coordenadas",cont+1+ " "+ location.getLongitude() +" "+location.getLatitude());
                sendBroadcast(intent);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i  = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        };
        //Configuração de tempo e distancia de configuração do GPS
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        int tempo = 3000;
        int distancia_minima = 1;
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, tempo, distancia_minima, locationListener);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationManager != null){
            locationManager.removeUpdates(locationListener);
        }

    }
}
