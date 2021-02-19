package com.fitness.fitnesspro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class main extends AppCompatActivity {
    Animation topanim;
    ImageView image, load;
    TextView logo;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        topanim = AnimationUtils.loadAnimation(main.this, R.anim.top_anim);
        image = findViewById(R.id.gifImageView3);
        load = findViewById(R.id.gifImageView4);
        logo = findViewById(R.id.textView2);

        image.setAnimation(topanim);

        load.setAnimation(topanim);
        logo.setAnimation(topanim);

        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Intent intent = new Intent(main.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2900);


    }
}