package com.fitness.fitnesspro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.common.base.CharMatcher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class calenderclass extends AppCompatActivity {

    ArrayList<String> events = new ArrayList<String>();
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    private String email = null;
    private String sideLunge, squat;
    private String pushUp, armExtension;
    private String shouldIAdd;
    private ListView lvActivities;
    private  String[] activities;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender);

        shouldIAdd = getIntent().getStringExtra("shouldIAdd");
        firebaseAuth =  FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = firebaseAuth.getCurrentUser();
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

        CalendarView mCalendarView;
if(shouldIAdd!=null) {
    if (shouldIAdd.equals("add")) {
        addToArray();

    }
}
        if(user == null){
            Intent goBackIntent = new Intent(calenderclass.this, loginActivity.class);
            startActivity(goBackIntent);
            finish();
        }
        else{
            if(user!=null){
                email = user.getEmail();
            }
        }

        if(email == null)
            Toast.makeText(calenderclass.this, "email is null", Toast.LENGTH_SHORT).show();
// when play button clicked


        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                TextView pushUpText = findViewById(R.id.pushupView);
                TextView extensionView = findViewById(R.id.extension);
                TextView squat = findViewById(R.id.squatView);
                TextView sideLunge = findViewById(R.id.lunge);

                month++;
                String stringYear = String.valueOf(year);
                String stringMonth = String.valueOf(month);
                String stringDay = String.valueOf(dayOfMonth);
                String date = stringDay +"/"+ stringMonth;


               for(int i = 0; i<events.size(); i++){
                   if(events.get(i).contains(date)){
                      if(events.get(i).contains("arm")){
                          String text = events.get(i);
                          extensionView.setText(text);
                      }
                       if(events.get(i).contains("push")){
                           String text = events.get(i);
                           pushUpText.setText(text);
                       }

                       if(events.get(i).contains("side")){
                           String text = events.get(i);
                           sideLunge.setText(text);
                       }

                       if(events.get(i).contains("squat")){
                           String text = events.get(i);
                           squat.setText(text);
                      }

                   }
                   else{
                       extensionView.setText("");
                       pushUpText.setText("");
                   }

               }



                }


              });
            }



    public void addToArray(){
        fStore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .get().addOnCompleteListener(task -> {
            if(task.isSuccessful() && task.getResult() != null){


                pushUp = task.getResult().getString("pushUp");
                if(pushUp!=null) {
                   String push = pushUp.toString();
                    events.add(push);
                }


                armExtension = task.getResult().getString("armExtension");
                if(armExtension!=null) {
                   String arm = armExtension.toString();
                    events.add(arm);
                }


                sideLunge =  task.getResult().getString("sidelunge");
                if(sideLunge!=null) {
                   String side = sideLunge.toString();
                events.add(side);
                }


                squat  =  task.getResult().getString("squatJump");
                if(squat!=null){
                 String textSquat = squat.toString();
                 events.add(textSquat);
                }

            }else{
                //deal with error
            }
        });


    }


}
