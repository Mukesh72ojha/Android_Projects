package com.example.rituapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    final String APP_ID = "6b80eed50a89d26136ed9bfb56d1cf2e";
    final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";

    final long MIN_TIME = 5000;
    final float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 101;

    String Location_Provider = LocationManager.GPS_PROVIDER;

    TextView NameOfCity, WeatherState, Temperature;
    ImageView mWeatherIcon;

    RelativeLayout mCityFinder;

    LocationManager mLocationManager;
    LocationListener mLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NameOfCity = findViewById(R.id.cityName);
        WeatherState = findViewById(R.id.weatherCondition);
        Temperature = findViewById(R.id.temperature);
        mWeatherIcon = findViewById(R.id.weatherIcon);
        mCityFinder = findViewById(R.id.cityFinder);

        mCityFinder.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, cityfinder.class);
            startActivity(intent);
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        getCurrentLocationWeatherInfo();
//    }


    @Override
    protected void onResume() {
        super.onResume();

        Intent mintent = getIntent();
        String city = mintent.getStringExtra("City");
        if (city != null){
            getWeatherForNewCity(city);
        }else {
            getCurrentLocationWeatherInfo();
        }
    }
    private void getWeatherForNewCity(String city){
        RequestParams params = new RequestParams();
        params.put("q",city);
        params.put("AppId",APP_ID);
        doSomeNetworking(params);
    }

    private void getCurrentLocationWeatherInfo() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = location -> {
            String Latitude = String.valueOf(location.getLatitude());
            String Longitude = String.valueOf(location.getLongitude());
            RequestParams params = new RequestParams();
            params.put("lat",Latitude);
            params.put("log",Longitude);
            params.put("appId",APP_ID);
            doSomeNetworking(params);
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        mLocationManager.requestLocationUpdates(Location_Provider, MIN_TIME, MIN_DISTANCE, mLocationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this, "Getting information", Toast.LENGTH_SHORT).show();
                getCurrentLocationWeatherInfo();
            }
            else Toast.makeText(MainActivity.this, "Give permission", Toast.LENGTH_SHORT).show();
        }
    }
    private void doSomeNetworking(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
               // Toast.makeText(MainActivity.this, "Showing data", Toast.LENGTH_SHORT).show();
                weatherData weatherD = weatherData.fromJson(response);
                assert weatherD != null;
                updateUI(weatherD);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }
        });
    }
    public void updateUI(weatherData weather){
        Temperature.setText(weather.getmTemperature());
        NameOfCity.setText(weather.getmCity());
        WeatherState.setText(weather.getmWeathetType());
        int resourceID = getResources().getIdentifier(weather.getMicon(),"drawable",getPackageName());
        mWeatherIcon.setImageResource(resourceID);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLocationManager != null){
            mLocationManager.removeUpdates(mLocationListener);
        }
    }
}