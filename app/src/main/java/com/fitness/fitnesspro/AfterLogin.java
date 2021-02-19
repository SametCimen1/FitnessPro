package com.fitness.fitnesspro;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        googleSign = new googleSignIn();
        signUpActivity = new SignUpActivity();
        Intent intent = getIntent();
        email = intent.getStringExtra(SignUpActivity.EXTRA_EMAIL);


        emailFromSignUp = signUpActivity.getEmail();
        login = new loginActivity();

        loginManager = LoginManager.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


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


}