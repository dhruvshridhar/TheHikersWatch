package in.blogspot.tecnopandit.thehikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
LocationManager locationManager;
LocationListener locationListener;
String datatoshow="";
TextView textView;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textView2);
        locationManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        getSupportActionBar().hide();
        locationListener= new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                datatoshow="Latitude: "+location.getLatitude()+"\n\n"+"Longitude: "+location.getLongitude()+"\n\n"+"Accuracy: "+location.getAccuracy()+"\n\n"+"Altitude: "+location.getAltitude()+"\n\n";
                textView.setText(datatoshow);
                Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> addresses= (List<Address>) geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),5);
                    String addres="Address: ";
                    Log.i("Address  :   ",addresses.toString());
                    if (addresses.get(0).getThoroughfare()!=null)
                    {
                        addres+=addresses.get(0).getThoroughfare()+", ";
                    }
                    if (addresses.get(0).getLocality()!=null)
                    {
                        addres+=addresses.get(0).getLocality()+", ";
                    }
                    if (addresses.get(0).getAdminArea()!=null)
                    {
                        addres+=addresses.get(0).getAdminArea()+", ";
                    }
                    if (addresses.get(0).getCountryName()!=null)
                    {
                        addres+=addresses.get(0).getCountryName()+"\n";
                    }
                    textView.append(addres);
                    if (addresses.get(0).getPostalCode()!=null)
                    {
                        String pin="PIN: "+addresses.get(0).getPostalCode();
                        textView.append(pin);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }
    }

}
