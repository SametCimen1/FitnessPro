package com.fitness.fitnesspro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.Context;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class ArmWork extends AppCompatActivity {



    private Timer timer;
    private TextView date, dayTextView;
    private String today;
    private SharedPreferences sharedPreferences;
    private int dayCount;
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String TEXT = "text";
    private TextView pushUp, oneHand, armCircle, armFront;

    @Override
    protected void onCreate ( Bundle savedInstanceState ){
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_arm_work );
        getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN , (WindowManager.LayoutParams.FLAG_FULLSCREEN) );
//        String weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
//        Toast.makeText(ArmWork.this, weekday_name, Toast.LENGTH_SHORT).show();


       dayTextView = findViewById(R.id.textChangedate);

        pushUp = findViewById(R.id.textView12);
        oneHand = findViewById(R.id.textView1);
        armCircle = findViewById(R.id.textView17);
        armFront = findViewById(R.id.textView22);

        timer = new Timer();
        timer.schedule ( new TimerTask ( ) {
            @Override
            public void run ( ) {
                Intent intent = new Intent(ArmWork.this, timer.class);
                startActivity ( intent );
            }
        }, 5000);

          today = getIntent().getStringExtra("day");

          if(today.equals("day1")){
              pushUp.setText("15x recommended");
              oneHand.setText("30 sec hold recommended");
              armCircle.setText("circle for 1 min recommended");
              armFront.setText("30x recommended");
              dayTextView.setText("Day 1/7");


          }

        if(today.equals("day2")){
            pushUp.setText("20x recommended");
            oneHand.setText("45 sec hold recommended");
            armCircle.setText("circle for 90 seconds recommended");
            armFront.setText("45x recommended");
            dayTextView.setText("Day 2/7");
        }
        if(today.equals("day3")){
            pushUp.setText("25x recommended");
            oneHand.setText("1 minute hold recommended");
            armCircle.setText("circle for 2 min recommended");
            armFront.setText("60x recommended");
            dayTextView.setText("Day 3/7");
        }

        if(today.equals("day4")){
            pushUp.setText("30x recommended");
            oneHand.setText("90 seconds hold recommended");
            armCircle.setText("circle for 150 seconds recommended");
            armFront.setText("75x recommended");
            dayTextView.setText("Day 4/7");
        }


        if(today.equals("day5")){
            pushUp.setText("35x recommended");
            oneHand.setText("2 min hold recommended");
            armCircle.setText("circle for 3 min recommended");
            armFront.setText("90x recommended");
            dayTextView.setText("Day 5/7");
        }

        if(today.equals("day6")){

        }



        if(today.equals("day7")){

        }






    }


}