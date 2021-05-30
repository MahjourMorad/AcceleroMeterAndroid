package com.mahjour.accello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometreSensor;
    public static DecimalFormat DECIMAL_FORMATTER;

    private TextView x;
    private TextView y;
    private TextView z;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometreSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (mAccelerometreSensor == null) {
            Toast.makeText(getApplicationContext(), "Ce capteur n'est pas supporté", Toast.LENGTH_LONG).show();
        }
        x = findViewById(R.id.x);
        y = findViewById(R.id.y);
        z = findViewById(R.id.z);
        result = findViewById(R.id.result);
        // define decimal formatter
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setDecimalSeparator('.');
        DECIMAL_FORMATTER = new DecimalFormat("#.000", symbols);

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float magX = event.values[0];
            float magY = event.values[1];
            float magZ = event.values[2];
            // set value on the screen
            x.setText("Valeur de x :" + DECIMAL_FORMATTER.format(magX));
            y.setText("Valeur de y :" + DECIMAL_FORMATTER.format(magY));
            z.setText("Valeur de z :" + DECIMAL_FORMATTER.format(magZ));


            if (magZ > 10) {
                result.setText("Vous etes entrain de sauter ");
            } else if (magY < 1.9 && magX > 0.8) {
                result.setText("Vous etes entrain de marcher ");
            } else if (magY < 4) {
                result.setText("Vous etes assis ");
            } else {
                result.setText("Vous etes debout");
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public MainActivity() {
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometreSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);

    }


}