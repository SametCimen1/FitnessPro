package com.fitness.fitnesspro;


import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
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
import java.util.Date;
import java.util.TimeZone;


public class AfterLogin extends AppCompatActivity   {
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
    private Button profile, goToSteps;
    private String email;
    private Button reset;
    private   boolean isChecked;
    private String preferred;
    private String gender, bDay;

    private String kg = "";
    private Boolean isThereData;
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
        goToSteps = findViewById(R.id.steps);
        emailFromSignUp = signUpActivity.getEmail();
        login = new loginActivity();
        profile = findViewById(R.id.profileButton);
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
                Intent intent = new Intent(AfterLogin.this, loginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                signOut();

            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterLogin.this, Profile.class);
                startActivity(intent);
            }
        });
        goToSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           Intent intent = new Intent(AfterLogin.this, stepCounter.class);
           startActivity(intent);

            }
        });
// getting data
        fStore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .get().addOnCompleteListener(task -> {
            if(task.isSuccessful() && task.getResult() != null){

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



            }else{
                //deal with error
            }
        });

//        Button steps = findViewById(R.id.step);
//        steps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setContentView(R.layout.stepcountertest);
//            }
//        });
        // calender
        Button go = findViewById(R.id.openCalendar);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView listView;

                listView = findViewById(R.id.listView);
                ArrayList<String> workouts = new ArrayList<String>();
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

                int currentYear = calendar.get(Calendar.YEAR);
                int currentMonth = calendar.get(Calendar.MONTH) + 1;
                int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
                String year = String.valueOf(currentYear);
                String month = String.valueOf(currentMonth);
                String day = String.valueOf(currentDay);
                String date = month+""+day+""+ year;

                setContentView(R.layout.calender);
                TextView theDate;
                CalendarView mCalendarView;
                theDate = findViewById(R.id.date);

// when play button clicked


                String workoutName = "WorkoutOne";
                String workoutName2 = "WorkoutTwo";
                String workoutName3 = "WorkoutThree";
                String workoutName4 = "WorkoutFour";
                workouts.add("2242021" + workoutName);
                workouts.add("2232021" + workoutName2);
                workouts.add("2252021" + workoutName3);
                workouts.add("2262021" + workoutName4);
                mCalendarView = (CalendarView) findViewById(R.id.calendarView);
                mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        month++;
                        String stringYear = String.valueOf(year);
                        String stringMonth = String.valueOf(month);
                        String stringDay = String.valueOf(dayOfMonth);
                        String date = stringMonth+""+stringDay+""+ stringYear;
                        int found = 0;

                        for(int i = 0; i<workouts.size(); i++){
                            String name = workouts.get(i).replaceAll("\\d","");
                            String dateOfWorkout = CharMatcher.inRange('0', '9').retainFrom(workouts.get(i));
                            if(date.equals(dateOfWorkout)){
                                found++;
                                theDate.setText(workouts.get(i));
                                Toast.makeText(AfterLogin.this,name , Toast.LENGTH_LONG).show();
                                Toast.makeText(AfterLogin.this,dateOfWorkout, Toast.LENGTH_LONG).show();

                            }
                        }
                        String [] list;
                        list = new String[found];
                        if(list.length==0){
                            Toast.makeText(AfterLogin.this,"No data available ", Toast.LENGTH_LONG).show();
                        }
//                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list);

                    }
                });
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
    public void checkIfThereIsData(){



    }

    }

