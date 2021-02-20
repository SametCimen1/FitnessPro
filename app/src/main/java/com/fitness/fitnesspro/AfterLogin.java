package com.fitness.fitnesspro;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class AfterLogin extends AppCompatActivity {
    private Button signOut;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private SignUpActivity signUpActivity;
    private googleSignIn googleSign;
    private LoginManager loginManager;
    loginActivity login;
    private String emailFromSignUp;
    private TextView textEmail, textName;
    private TextView emailText;
    private String email;
    private Button reset;

    private int kg = 0;
    private int cm = 0;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
reset = findViewById(R.id.resetButton);

        SharedPreferences dataSave = getSharedPreferences("firstLog", 0);

        if(dataSave.getString("firstTime", "").toString().equals("no")){

        }
        else{
            //  this is the first run of application

            SharedPreferences.Editor editor = dataSave.edit();
            editor.putString("firstTime", "no");
            editor.commit();
            Intent intent = new Intent(AfterLogin.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = dataSave.edit();
                editor.putString("firstTime", "");
                editor.commit();
            }
        });

        googleSign = new googleSignIn();
        signUpActivity = new SignUpActivity();



        emailFromSignUp = signUpActivity.getEmail();
        login = new loginActivity();

        loginManager = LoginManager.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
if(user == null && accessToken == null){
    Intent goBackIntent = new Intent(AfterLogin.this, loginActivity.class);
    startActivity(goBackIntent);
    finish();
}
else{
if(user!=null){
    email = user.getEmail();
}
}
if(email == null)
     Toast.makeText(AfterLogin.this, "email is null", Toast.LENGTH_SHORT).show();
else
    Toast.makeText(AfterLogin.this, email, Toast.LENGTH_SHORT).show();



        signOut = findViewById(R.id.signOutButton);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginManager.logOut();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AfterLogin.this, SignUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                signOut();

            }
        });
    }

    //     not using this because using  FirebaseAuth.getInstance().signOut();
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(AfterLogin.this, SignUpActivity.class));
                    }
                });
    }

public void checkIfFirstTime(){
        
}
}