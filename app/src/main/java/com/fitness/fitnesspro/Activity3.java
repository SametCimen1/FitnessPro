package com.fitness.fitnesspro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Activity3 extends AppCompatActivity {
    private TextView textView, textView1, textView2;
    SeekBar Seekbar, seekBar2, seekbar3;
    private ImageButton next;
    private int lb = 0;
    private int feet = 0;
    private int inch = 0;
    FirebaseFirestore fStore;
    private String date = null;
    private String gender = null;
    private String email = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, (WindowManager.LayoutParams.FLAG_FULLSCREEN));
        setContentView(R.layout.activity_3);

        textView = (TextView) findViewById(R.id.textView233);
        textView1 = (TextView) findViewById(R.id.textView812);
        textView2 = (TextView) findViewById(R.id.textView8129);
        next = findViewById(R.id.imageButton3);
        Seekbar = (SeekBar) findViewById(R.id.seekBar);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekbar3 = (SeekBar) findViewById(R.id.seekBar3);

        fStore = FirebaseFirestore.getInstance();
        date = getIntent().getStringExtra("dateKey");
        gender = getIntent().getStringExtra("genderKey");

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        if (user == null && accessToken == null) {
            Intent goBackIntent = new Intent(Activity3.this, loginActivity.class);
            startActivity(goBackIntent);
            finish();
        } else {
            if (user != null) {
                email = user.getEmail();
            }
        }

        if (email == null)
            Toast.makeText(Activity3.this, "email is null", Toast.LENGTH_SHORT).show();




        Seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lb = progress;
                textView.setText("" + progress + "/500 LB");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                feet = progress;
                textView1.setText("" + progress + "/7 FEET");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        seekbar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                inch = progress;
                textView2.setText("" + progress + "/11 INCH");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feet == 0 || lb == 0) {
                    Toast.makeText(Activity3.this, "Please enter your weight and height", Toast.LENGTH_LONG).show();
                    return;
                } else {
InsertData();
                    Intent intent = new Intent(Activity3.this, AfterLogin.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    public void InsertData() {


        DocumentReference newDocumentReference = fStore.collection("users").document(email);
        Map<String, Object> profile = new HashMap<>();
        profile.put("gender", gender);
        profile.put("birthday", date);
        profile.put("preferred data Type", "lb/inch");
        profile.put("lb", lb);
        profile.put("feet", feet);
        profile.put("inch", inch);
        newDocumentReference.update(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Activity3.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}