package com.fitness.fitnesspro;


import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.CharMatcher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.TimeZone;


public class AfterLogin extends AppCompatActivity   {

    GoogleSignInClient mGoogleSignInClient;
    FirebaseFirestore fStore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private SignUpActivity signUpActivity;
    private googleSignIn googleSign;
    private LoginManager loginManager;
    loginActivity login;
    private String emailFromSignUp;
    private TextView textEmail, textName;
    private TextView emailText;
    private ImageButton profile, calendar, exercise;
    private ImageButton arm, leg;
    private String email;


    private String preferred;
    private String gender, bDay;

    private String kg = "";
    private Boolean isThereData;
    private String cm = "";
    private String feet, inch, lb;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
        getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN , (WindowManager.LayoutParams.FLAG_FULLSCREEN) );


       fStore = FirebaseFirestore.getInstance();

       // check if first time logging in
        SharedPreferences dataSave = getSharedPreferences("firstLog", 0);
        if (dataSave.getString("firstTime", "").toString().equals("no")) {

        } else {
            //  this is the first run of application

            SharedPreferences.Editor editor = dataSave.edit();
            editor.putString("firstTime", "no");
            editor.commit();
            Intent intent = new Intent(AfterLogin.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
// reset password


        googleSign = new googleSignIn();
        signUpActivity = new SignUpActivity();
leg = findViewById(R.id.legbutton);

        emailFromSignUp = signUpActivity.getEmail();
        login = new loginActivity();

        loginManager = LoginManager.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        arm  = findViewById(R.id.armbutton);
        user = firebaseAuth.getCurrentUser();
// checking if user is connected if not go to login
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (user == null && accessToken == null) {
            Intent goBackIntent = new Intent(AfterLogin.this, loginActivity.class);
            startActivity(goBackIntent);
            finish();
        } else {
            if (user != null) {
                email = user.getEmail();
            }
        }
        if (email == null)
            Toast.makeText(AfterLogin.this, "email is null", Toast.LENGTH_SHORT).show();




        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);




        // what happens when you click signout
        calendar = findViewById(R.id.imageButton5);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterLogin.this, calenderclass.class);
                startActivity(intent);
            }
        });
        profile = findViewById(R.id.imageButton);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterLogin.this, Profile.class);
                startActivity(intent);
            }
        });
        exercise = findViewById(R.id.imageButton2);
        exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterLogin.this, mainscreen.class);
                startActivity(intent);
            }
        });



// getting data



    }

    }

