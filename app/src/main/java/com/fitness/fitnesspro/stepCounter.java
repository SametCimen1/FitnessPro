package com.fitness.fitnesspro;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorAdditionalInfo;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class stepCounter extends AppCompatActivity implements SensorEventListener {
    // private static final Class<? extends Object> SENSOR_DEVICE = ;
    private TextView Counter,Detector;
    private SensorManager sensormanager;
    private Sensor mStepCounter;
    private boolean isSensorPresent;
    private SensorAdditionalInfo sensorEvent;
    int steps=0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stepcounter);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Counter=findViewById(R.id.counter);
        Detector=findViewById(R.id.Detector);
        sensormanager=(SensorManager) getSystemService(SENSOR_SERVICE);
        if(sensormanager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null)
        {
            mStepCounter=sensormanager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isSensorPresent=true;
        }
        else{
            Counter.setText("Counter not active");
            isSensorPresent=false ;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(sensorEvent.sensor==mStepCounter)
        {
            steps=(int) sensorEvent.intValues[0];
            Counter.setText(String.valueOf(steps));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void onResume(){
        super.onResume();
        if(sensormanager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null)
        {
            sensormanager.registerListener(this,mStepCounter,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    public void onPause(){
        super.onPause();
        if(sensormanager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null)
        {
            sensormanager.unregisterListener(this,mStepCounter);

        }
    }
}