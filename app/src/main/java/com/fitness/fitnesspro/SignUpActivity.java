package com.fitness.fitnesspro;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import androidx.annotation.Nullable;

import android.app.ProgressDialog;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private  Button finalSignUp;

    private String userEmail = null;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private  EditText myName, myEmail, myPassword, myConfirmPassword;
    FirebaseFirestore fStore;
    private  char letter = 'x';
    private LoginButton facebookButton;
    private  boolean isThereNumber = false;
    private  SignInButton signUpGoogle;
    private  boolean isThereCapital = false;
    private String facebookEmail = null;
    private String facebookName = null;
    private  boolean isUserSignedInWithEmail = false;
    private  CallbackManager callbackManager;
    private TextView alreadyHaveAccount;
    public static final String EXTRA_EMAIL = "com.example.registersignup.EXTRA_EMAIL";
    private  boolean isLoggedInWithFacebook;
    private  FirebaseAuth mFirebaseAuth;
    private LoginManager loginManager;
    private GoogleSignInClient mGoogleSignInClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        checkIfUserIsLoggedIn();
        signInSilently();
        finalSignUp = findViewById(R.id.SignupButton);
        myName = findViewById(R.id.name);

        alreadyHaveAccount = findViewById(R.id.haveAccount);
        myEmail = findViewById(R.id.signUpEmail);
        facebookButton = findViewById(R.id.login_facebook);

        fStore= FirebaseFirestore.getInstance();

        myPassword = findViewById(R.id.signUpPassword);
        myConfirmPassword = findViewById(R.id.signUpPasswordConfirm);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        signUpGoogle = findViewById(R.id.googleButton);
        loginManager = LoginManager.getInstance();
        isLoggedInWithFacebook = isFacebookLoggedIn();
        mFirebaseAuth = FirebaseAuth.getInstance();

        finalSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });


        if (isLoggedInWithFacebook) {
            Intent intent = new Intent(SignUpActivity.this, AfterLogin.class);
            startActivity(intent);
            finish();
        }

        signUpGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, googleSignIn.class);
                startActivity(intent);
            }
        });

        callbackManager = CallbackManager.Factory.create();
        facebookButton.setReadPermissions(Arrays.asList("email","public_profile"));
        facebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {



            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,loginActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {
                    facebookName = object.getString("first_name");
                    facebookEmail = object.getString("email");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                DocumentReference documentReference = fStore.collection("users").document(facebookEmail);
                Map<String, Object> user = new HashMap<>();
                user.put("name", facebookName);
                user.put("email", facebookEmail);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SignUpActivity.this,"User is created", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields","email,first_name, id");
        request.setParameters(parameters);
        request.executeAsync();
        Intent intent = new Intent(SignUpActivity.this, AfterLogin.class);
        startActivity(intent);
        finish();
    }

//    private void loadUserProfile(AccessToken newAccessToken){
//        GraphRequest request =    GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
//            @Override
//            public void onCompleted(JSONObject object, GraphResponse response) {
////
//                    Toast.makeText(SignUpActivity.this, "in loadUserProfile", Toast.LENGTH_LONG).show();
//                    String emailText = object.getString("email");
//                    faceBookEmail = emailText;
//                    String name = object.getString("first_name");
//                    DocumentReference documentReference = fStore.collection("users").document(faceBookEmail);
//                    Map<String, Object> user = new HashMap<>();
//                    user.put("name", name);
//                    user.put("email", emailText);
//                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(SignUpActivity.this,"User is created", Toast.LENGTH_SHORT).show();
//                        }
//                    });

//
//            }
//        });
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "email,first_name, id");
//        request.setParameters(parameters);
//        request.executeAsync();
//
//
//    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken==null){


            }else {


            }
        }
    };

    private void Register() {
        String name = myName.getText().toString();
        String email = myEmail.getText().toString();
        String password = myPassword.getText().toString();
        String confirmPassword = myConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            myEmail.setError("enter your email");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            myPassword.setError("enter your Password");
            return;
        }

        if (TextUtils.isEmpty(name)) {
            myName.setError("enter your name");
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            myConfirmPassword.setError("enter your Confirm Password");
            return;
        }

        if (!isDifferent(password, confirmPassword)) {
            myConfirmPassword.setError("Different Password");
            return;
        }

        if (myConfirmPassword.length() < 6) {
            myPassword.setError("Password should be more than 6 characters");
            return;
        }
        if (!isValidEmail(email)) {
            myEmail.setError("Invalid Email");
            return;
        }

        if (!password.matches("[A-zA-Z]+")) { // only if contains normal letters
            isThereNumber = true;
        }


        if (!isThereNumber) {
            myPassword.setError("There should be at least 1 number in your password");
            return;

        }
        letter = password.charAt(0);
        for (int index = 0; index < password.length(); index++) {
            letter = password.charAt(0);
            if (Character.isUpperCase(letter)) {
                isThereCapital = true;
            }
        }
        if (!isThereCapital) {
            myPassword.setError("There should be at least 1 capital letter in your password");
            return;

        }
        userEmail = myEmail.getText().toString();;

        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        isUserSignedInWithEmail = true;
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(SignUpActivity.this, "Registered successfully, please check your email", Toast.LENGTH_SHORT).show();

                                DocumentReference documentReference = fStore.collection("users").document(userEmail);
                                Map<String, Object> user = new HashMap<>();
                                user.put("name", name);
                                user.put("email", email);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SignUpActivity.this,"Error"+e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });


                                Intent intent = new Intent(SignUpActivity.this, loginActivity.class);
                                intent.putExtra(EXTRA_EMAIL, email);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(SignUpActivity.this, "Error:" + task.getException(), Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Error:" + task.getException(), Toast.LENGTH_SHORT).show();
                }



                progressDialog.dismiss();
            }

        });
    }

    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private boolean isDifferent(String string1, String string2) {
        if (string1.equals(string2)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFacebookLoggedIn() {
        return AccessToken.getCurrentAccessToken() != null;
    }

    private void checkIfUserIsLoggedIn() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser fireBAseUser = firebaseAuth.getCurrentUser();

        // pass the currentUserEmail to the After login

        if (fireBAseUser != null) {

            // if user is logged in
            String currentUserEmail = fireBAseUser.getEmail();
            Intent intent = new Intent(SignUpActivity.this,AfterLogin.class);
            intent.putExtra(EXTRA_EMAIL, currentUserEmail);

            startActivity(intent);
            finish();
        }
        else{
            return;
        }
    }

    private void signInSilently() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
// user is connected
            Intent intent = new Intent(SignUpActivity.this, AfterLogin.class);
            startActivity(intent);
            finish();
        }

    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }
    public String getEmail() {
        return userEmail;
    }

}

