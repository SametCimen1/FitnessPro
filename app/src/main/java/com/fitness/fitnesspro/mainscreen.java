package com.fitness.fitnesspro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class mainscreen extends AppCompatActivity {
    private ImageButton arm, leg, abs;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_after_login);
        arm = findViewById(R.id.armbutton);
        leg = findViewById(R.id.legbutton);
        abs = findViewById(R.id.chestbutton);

        arm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           Intent intent = new Intent(mainscreen.this, armdays.class);
           startActivity(intent);
            }
        });

        leg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainscreen.this, legDays.class);
                startActivity(intent);
            }
        });

        abs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mainscreen.this, "Coming Soon!", Toast.LENGTH_LONG).show();
            }
        });
    }
}