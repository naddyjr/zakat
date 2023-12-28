package com.somnest.zakaat;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.somnest.zakaat.Appcompany.Privacy_Policy_activity;
import com.somnest.zakaat.PreferenceUtil.PrayerSharedPreference;
public class CompassActivity extends AppCompatActivity implements SensorEventListener {
    private float currentCompassDegree = 0.0f;
    private ImageView imgCompass;
    private ImageView imgCursor;
    private double latDouble;
    RelativeLayout layout;
    private double longDouble;
    private SensorManager mSensorManager;
    PrayerSharedPreference prayerSharedPreference;
    private TextView tvHeading;
    private TextView tvLocation;

    @Override 
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_compass);



        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.prayerSharedPreference = new PrayerSharedPreference(this);
        this.tvLocation = (TextView) findViewById(R.id.loc);
        this.imgCompass = (ImageView) findViewById(R.id.image_compass);
        this.imgCursor = (ImageView) findViewById(R.id.image_cursor);
        this.tvHeading = (TextView) findViewById(R.id.heading);
        this.tvLocation.setText(this.prayerSharedPreference.getLocation());
        this.longDouble = Double.parseDouble(this.prayerSharedPreference.getLongitude());
        this.latDouble = Double.parseDouble(this.prayerSharedPreference.getLatitude());
        this.mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override 
    public void onResume() {
        super.onResume();
        if (this.mSensorManager.getDefaultSensor(3) != null) {
            SensorManager sensorManager = this.mSensorManager;
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(3), 1);
            return;
        }
        Toast.makeText(getApplicationContext(), "Sorry the Qibla feature is not supported by your device", Toast.LENGTH_LONG).show();
    }

    @Override 
    public void onPause() {
        super.onPause();
        this.mSensorManager.unregisterListener(this);
    }

    @Override 
    public void onSensorChanged(SensorEvent sensorEvent) {
        float f = -Math.round(sensorEvent.values[0]);
        float calculateQibla = f + calculateQibla(this.latDouble, this.longDouble);
        TextView textView = this.tvHeading;
        textView.setText("Heading: " + calculateQibla + " degrees");
        RotateAnimation rotateAnimation = new RotateAnimation(this.currentCompassDegree, f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setDuration(210L);
        rotateAnimation.setFillAfter(true);
        this.imgCompass.startAnimation(rotateAnimation);
        this.imgCursor.setRotation(calculateQibla);
        this.currentCompassDegree = f;
    }

    public float calculateQibla(double d, double d2) {
        double d3 = (d * 3.141592653589793d) / 180.0d;
        double d4 = 0.6946410422937431d - ((d2 * 3.141592653589793d) / 180.0d);
        return (float) Math.round(Math.atan2(Math.sin(d4), (Math.cos(d3) * Math.tan(0.3735004599267865d)) - (Math.sin(d3) * Math.cos(d4))) * 57.29577951308232d);
    }

    @Override 
    public void onBackPressed() {
        super.onBackPressed();
        finish();
     
    }

    

    public boolean isOnline() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
