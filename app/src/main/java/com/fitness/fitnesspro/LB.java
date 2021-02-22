package com.fitness.fitnesspro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.varunest.sparkbutton.SparkButton;

public class LB extends AppCompatActivity {

    SparkButton Button123, Button1232;
private String date = null;
private String gender = null;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );

        getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN , (WindowManager.LayoutParams.FLAG_FULLSCREEN) );
        setContentView ( R.layout.activity_l_b );
        date = getIntent().getStringExtra("dateKey");
        gender = getIntent().getStringExtra("genderKey");

        Button1232 = findViewById ( R.id.spark_button1232 );
        Button1232.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                openActivity4 ();
            }

        } );

        Button123 = findViewById(R.id.spark_button12323);
        Button123.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v) {
                openActivity3 ();
            }
        });
    }

    public void openActivity4 ( ) {
        Intent intent = new Intent (this, Activity4.class );
        intent.putExtra("dateKey", date);
        intent.putExtra("genderKey", gender);
                startActivity (intent);
        overridePendingTransition(R.anim.right, R.anim.left);
    }

    public void openActivity3() {
        Intent intent = new Intent(this, Activity3.class);
        intent.putExtra("dateKey", date);
        intent.putExtra("genderKey", gender);
        startActivity( intent);
        overridePendingTransition(R.anim.right, R.anim.left);


    }

}
