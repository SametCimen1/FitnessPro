package com.fitness.fitnesspro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class legDays extends AppCompatActivity {
    private Button day1, day2, day3, day4, day5, day6, day7;
    private String day;
    private FirebaseUser user;
    private SharedPreferences sharedPreferences;
    private String collectedDate;
    private static final String SHARED_PREFS = "sharedPrefs";
    private FirebaseAuth firebaseAuth;
    private String getDay;
    private String email = null;
    private static final String TEXT = "text";
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.legday);

        fStore = FirebaseFirestore.getInstance();
        firebaseAuth =  FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        if(user == null){
            Intent goBackIntent = new Intent(legDays.this, loginActivity.class);
            startActivity(goBackIntent);
            finish();
        }
        else{
            if(user!=null){
                email = user.getEmail();
            }
        }

        if(email == null)
            Toast.makeText(legDays.this, "email is null", Toast.LENGTH_SHORT).show();

        day1 = findViewById(R.id.ArmDay1);
        day2 = findViewById(R.id.ArmDay2);
        day3 = findViewById(R.id.ArmDay3);
        day4 = findViewById(R.id.ArmDay4);
        day5 = findViewById(R.id.ArmDay5);
        day6 = findViewById(R.id.ArmDay6);
        day7 = findViewById(R.id.ArmDay7);

        checkIfThereIsData();

        day1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData("day1");
                day = "day1";
                InsertData();
                Intent intent = new Intent(legDays.this, legworkouts.class);
                intent.putExtra("day", "day1");
                startActivity(intent);

            }
        });

        day2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day = "day2";
                InsertData();
                Intent intent = new Intent(legDays.this, legworkouts.class);
                intent.putExtra("day", "day2");
                startActivity(intent);


            }
        });

        day3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day = "day3";
                InsertData();
                saveData("day3");
                Intent intent = new Intent(legDays.this, legworkouts.class);
                intent.putExtra("day", "day3");
                startActivity(intent);

            }
        });


        day4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day = "day4";
                InsertData();
                saveData("day4");
                Intent intent = new Intent(legDays.this, legworkouts.class);
                intent.putExtra("day", "day4");

                startActivity(intent);

            }
        });

        day5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day = "day5";
                InsertData();
                saveData("day5");
                Intent intent = new Intent(legDays.this, legworkouts.class);
                intent.putExtra("day", "day5");

                startActivity(intent);

            }
        });

        day6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(legDays.this, "Today is Resting Day", Toast.LENGTH_LONG).show();
            }
        });

        day7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(legDays.this, "Today is Resting Day", Toast.LENGTH_LONG).show();
            }
        });


    }


    public void MakeText(String text){
        Toast.makeText(legDays.this, text, Toast.LENGTH_LONG).show();
    }

    public void saveData(String button) {

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT, button);
        editor.apply();

    }

    public String loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        day = sharedPreferences.getString(TEXT, "");
        return day;
    }

    public void InsertData(){


        DocumentReference newDocumentReference = fStore.collection("users").document(email);


        Map<String, Object> profile = new HashMap<>();
        profile.put("day", day);
        profile.put("currentDay", new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis()));
        newDocumentReference.update(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(legDays.this,"Error"+e.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void checkIfThereIsData(){
        fStore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .get().addOnCompleteListener(task -> {
            if(task.isSuccessful() && task.getResult() != null){

                collectedDate = task.getResult().getString("day");
                if(collectedDate!= null){
                    checkThedate(collectedDate);
                }

            }else{
                //deal with error
            }
        });
    }
    public void checkThedate(String collect){
        if(collect.equals("day1")){
            doDay1();
        }
        if(collect.equals("day2")){
            doDay2();
        }
        if(collect.equals("day3")){
            doDay3();
        }
        if(collect.equals("day4")){
            doDay4();
        }
        if(collect.equals("day5")){
            doDay5();
        }
        if(collect.equals("day5")){
            doDay5();
        }

    }

    public void doDay1(){
        day2.setClickable(false);
        day3.setClickable(false);
        day4.setClickable(false);
        day5.setClickable(false);

    }

    public void doDay2(){
        day1.setClickable(false);
        day3.setClickable(false);
        day4.setClickable(false);
        day5.setClickable(false);




    }

    public void doDay3(){
        day2.setClickable(false);
        day1.setClickable(false);
        day4.setClickable(false);
        day5.setClickable(false);



    }

    public void doDay4(){
        day2.setClickable(false);
        day3.setClickable(false);
        day1.setClickable(false);
        day5.setClickable(false);



    }


    public void doDay5(){
        day2.setClickable(false);
        day3.setClickable(false);
        day4.setClickable(false);
        day1.setClickable(false);


    }

    public void doDay6(){
        day2.setClickable(false);
        day3.setClickable(false);
        day4.setClickable(false);

        day5.setClickable(false);
        MakeText("Today is a rest day");
    }

    public String getToday(){

        fStore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .get().addOnCompleteListener(task -> {
            if(task.isSuccessful() && task.getResult() != null){

                getDay = task.getResult().getString("currentDay");


            }else{
                //deal with error
            }
        });
        return getDay;
    }

}

