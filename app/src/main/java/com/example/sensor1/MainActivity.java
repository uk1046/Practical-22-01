package com.example.sensor1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    View t1 ;
    SensorManager s1;
    boolean iscolor=false;
    long updadte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1=findViewById(R.id.textView);
        t1.setBackgroundColor(Color.RED);
        s1=(SensorManager)getSystemService(SENSOR_SERVICE);
        updadte=System.currentTimeMillis();


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
    }


}

    private void getAccelerometer(SensorEvent event) {
        float[] values= event.values;

        float x = values[0];
        float y = values[1];
        float z = values[2];

        long actualTime = System.currentTimeMillis();

        float sqAcc = (x*x+y*y+z*z)/(SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        Toast.makeText(this, String.valueOf(sqAcc), Toast.LENGTH_SHORT).show();

        if(sqAcc>=2){
          if(actualTime-updadte < 200){
              return;
          }
        }
        updadte = actualTime;
        if (iscolor) {
            t1.setBackgroundColor(Color.GREEN);

        } else {
            t1.setBackgroundColor(Color.RED);
        }
        iscolor = !iscolor;
    }


    @Override
    protected void onResume() {
        super.onResume();
        s1.registerListener(this,s1.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        s1.unregisterListener(this);
    }
}