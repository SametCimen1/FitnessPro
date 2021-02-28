package com.fitness.fitnesspro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class legworkouts extends AppCompatActivity {
    Timer timer;
    private TextView sideLunge, squat, lateral, goblet, date;
    private String today;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_legworkouts );
        getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN , (WindowManager.LayoutParams.FLAG_FULLSCREEN) );

        timer = new Timer();
        timer.schedule ( new TimerTask ( ) {
            @Override
            public void run ( ) {
                Intent intent = new Intent(legworkouts.this, Squatlunge.class);
                startActivity ( intent );
            }
        }, 5000);
        date = findViewById(R.id.textChangedate);
        sideLunge = findViewById(R.id.textView10);
        squat = findViewById(R.id.textView1);
        lateral = findViewById(R.id.textView17);
        goblet = findViewById(R.id.textView22);
        today = getIntent().getStringExtra("day");

        if(today.equals("day1")){
            sideLunge.setText("15x recommended");
            squat.setText("30 sec hold recommended");
            lateral.setText("circle for 1 min recommended");
            goblet.setText("30x recommended");
            date.setText("Day 1/7");
        }

        if(today.equals("day2")){
            sideLunge.setText("20 recommended");
            squat.setText("1 min hold recommended");
            lateral.setText("circle for 2 min recommended");
            goblet.setText("40x recommended");
            date.setText("Day 2/7");
        }

        if(today.equals("day3")){
            sideLunge.setText("25x recommended");
            squat.setText("90 sec hold recommended");
            lateral.setText("circle for 3 min recommended");
            goblet.setText("50x recommended");
            date.setText("Day 3/7");
        }


        if(today.equals("day4")){
            sideLunge.setText("30x recommended");
            squat.setText(" 2 min hold recommended");
            lateral.setText("circle for 4 min recommended");
            goblet.setText("60x recommended");
            date.setText("Day 4/7");
        }

        if(today.equals("day5")){
            sideLunge.setText("35x recommended");
            squat.setText("150 sec hold recommended");
            lateral.setText("circle for 5 min recommended");
            goblet.setText("70x recommended");
            date.setText("Day 5/7");
        }


    }
}