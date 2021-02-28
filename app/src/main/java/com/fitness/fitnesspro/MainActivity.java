package com.fitness.fitnesspro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.varunest.sparkbutton.SparkButton;

public class MainActivity extends AppCompatActivity {
private String gender  = "";
    SparkButton male, female;


    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main);
        getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN , (WindowManager.LayoutParams.FLAG_FULLSCREEN) );


        female = findViewById ( R.id.spark_button12 );
        male = findViewById ( R.id.spark_button1 );

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "male";
                Intent intent = new Intent(MainActivity.this, Activity2.class);
                intent.putExtra("genderKey", gender);
                startActivity(intent);
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            gender = "female";
                Intent intent = new Intent(MainActivity.this, Activity2.class);
                intent.putExtra("genderKey", gender);
                startActivity(intent);
            }
        });


    }


}

