package com.fitness.fitnesspro;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.EventListener;
import java.util.Objects;

public class AfterLogin extends AppCompatActivity {
    private Button signOut;
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
    private String email;
    private Button reset;
    private String preferred;
    private String gender, bDay;

    private String kg = "";
    private String cm = "";
    private String feet, inch, lb;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN , (WindowManager.LayoutParams.FLAG_FULLSCREEN) );

        reset = findViewById(R.id.resetButton);
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


        signOut = findViewById(R.id.signOutButton);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // what happens when you click signout
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
// getting data
        fStore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .get().addOnCompleteListener(task -> {
            if(task.isSuccessful() && task.getResult() != null){
                gender = task.getResult().getString("gender");
                 bDay = task.getResult().getString("birthday");
                preferred = task.getResult().getString("preferred data Type");
                if(preferred == null){
                    Intent goBackIntent = new Intent(AfterLogin.this, MainActivity.class);
                    startActivity(goBackIntent);
                    finish();
                 return;
                }

                else if(preferred.equals("Kg/Cm")){
                    kg = task.getResult().getString("kg");
                    cm =  task.getResult().getString("cm");
                }
                else{
//                     feet inch lb
                            lb  =  task.getResult().getString("lb");
                                        inch = task.getResult().getString("inch");
                                        feet = task.getResult().getString("feet");

                }
             // you can call users info here after setting them
               Toast.makeText(AfterLogin.this,"gender "+ gender, Toast.LENGTH_LONG).show();
                Toast.makeText(AfterLogin.this, "birthday: "+ bDay, Toast.LENGTH_LONG).show();
                if(preferred.equals("Kg/Cm")){
                    Toast.makeText(AfterLogin.this,"kg "+ kg, Toast.LENGTH_LONG).show();
                    Toast.makeText(AfterLogin.this,"cm "+ cm, Toast.LENGTH_LONG).show();
                }
                else if(preferred.equals("lb/inch")){
                    Toast.makeText(AfterLogin.this,"lb "+ lb, Toast.LENGTH_LONG).show();
                    Toast.makeText(AfterLogin.this,"inch "+ inch, Toast.LENGTH_LONG).show();
                    Toast.makeText(AfterLogin.this,"feet "+ feet, Toast.LENGTH_LONG).show();
                }


            }else{
                //deal with error
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