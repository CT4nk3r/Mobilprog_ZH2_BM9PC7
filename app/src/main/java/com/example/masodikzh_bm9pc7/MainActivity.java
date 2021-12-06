package com.example.masodikzh_bm9pc7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    TextView textViewTemperature;
    RadioButton radioButtonHot, radioButtonNormal, radioButtonCold;
    ConstraintLayout constraintLayout;
    Sensor temperatureSensor;
    Button btnMasodikFeladat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewTemperature = findViewById(R.id.textViewTemperature);
        radioButtonHot = findViewById(R.id.radioButtonHot);
        radioButtonNormal = findViewById(R.id.radioButtonNormal);
        radioButtonCold = findViewById(R.id.radioButtonCold);
        constraintLayout = findViewById(R.id.constraintLayout);
        btnMasodikFeladat = findViewById(R.id.btnMasodikFeladat);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        temperatureSensor= sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if (temperatureSensor == null) {
            textViewTemperature.setText("NOT_SUPPORTED_MESSAGE");
        }


        btnMasodikFeladat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MessageActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType()==Sensor.TYPE_AMBIENT_TEMPERATURE){
            if (sensorEvent.values[0] > 30){
                constraintLayout.setBackgroundColor(Color.RED);
                textViewTemperature.setText(String.valueOf(sensorEvent.values[0]));
                radioButtonHot.toggle();
            }else if (sensorEvent.values[0] < 30 && sensorEvent.values[0] > 15){
                constraintLayout.setBackgroundColor(Color.GREEN);
                radioButtonNormal.toggle();
                textViewTemperature.setText(String.valueOf(sensorEvent.values[0]));
            }else if (sensorEvent.values[0] < 15){
                constraintLayout.setBackgroundColor(Color.BLUE);
                textViewTemperature.setText(String.valueOf(sensorEvent.values[0]));
                radioButtonCold.toggle();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}