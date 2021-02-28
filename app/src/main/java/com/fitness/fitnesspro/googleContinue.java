package com.fitness.fitnesspro;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.net.Uri;

import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class googleContinue extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    private Button signOut;
    private FirebaseAuth mAuth;
    private static int RC_SIGN_IN = 10;
    FirebaseFirestore fStore;
    private String email = null;
    private String TAG = "MainActivity";
    public String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_continue);
        getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN , (WindowManager.LayoutParams.FLAG_FULLSCREEN) );

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);


        signIn();


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);

            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                           
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(googleContinue.this, "sign in failed", Toast.LENGTH_LONG).show();
                            updateUI(null);

                        }

                        // ...
                    }

                    private void updateUI(FirebaseUser user) {
                        Intent intent = new Intent(googleContinue.this, AfterLogin.class);
                        startActivity(intent);
                        // get details of user who just signed in


                        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                        if (acct != null) {
                            String personName = acct.getDisplayName();
                            userName = personName;
                            String personGivenName = acct.getGivenName();
                            String personFamilyName = acct.getFamilyName();
                            String personEmail = acct.getEmail();
                            email = personEmail;

                            String personId = acct.getId();
                            Uri personPhoto = acct.getPhotoUrl();

                        }
                    }
                });
    }


    public String getEmail() {
        return email;
    }

    public String getName() {
        return userName;
    }


}
