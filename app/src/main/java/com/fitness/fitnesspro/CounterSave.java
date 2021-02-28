package com.fitness.fitnesspro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class CounterSave extends AppCompatActivity {

    TextView Value, valuee;
    Button button2;
    ImageView saveDataButton;
    int count = 0;
    int joe = 0 ;

    private String calendarPushUp, calendarArmExtension;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    private String email = null;
    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

    int currentYear = calendar.get(Calendar.YEAR);
    int currentMonth = calendar.get(Calendar.MONTH) + 1;
    int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_counter_save );
        getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN , (WindowManager.LayoutParams.FLAG_FULLSCREEN) );

        saveDataButton = findViewById(R.id.saveTheData);
        Value = ( TextView ) findViewById ( R.id.value );
        valuee = ( TextView ) findViewById ( R.id.valuee );
        fStore = FirebaseFirestore.getInstance();
        firebaseAuth =  FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        if(user == null){
            Intent goBackIntent = new Intent(CounterSave.this, loginActivity.class);
            startActivity(goBackIntent);
            finish();
        }
        else{
            if(user!=null){
                email = user.getEmail();
            }
        }

        if(email == null)
            Toast.makeText(CounterSave.this, "email is null", Toast.LENGTH_SHORT).show();

        saveDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertData();
               Intent intent = new Intent(CounterSave.this, calenderclass.class);
              intent.putExtra("shouldIAdd", "add");
               startActivity(intent);
               finish();
            }
        });
    }

    public void increment ( View v ) {
        count++;
        Value.setText ( "" + count );

    }

    public void decrement ( View v ) {
        if (count <= 0) count = 0;
        count--;
        Value.setText ( "" + count );


    }

    public void dex ( View v ) {
        joe++;
        valuee.setText ( "" + joe );
    }

    public void sex ( View v ) {
        if (joe <= 0) joe = 0;
        joe--;
        valuee.setText ( "" + joe );

    }

    public void InsertData(){


        DocumentReference newDocumentReference = fStore.collection("users").document(email);


        Map<String, Object> profile = new HashMap<>();
        String date = String.valueOf(currentDay)+"/" + String.valueOf(currentMonth);

        profile.put("pushUp", count+ " push up/ date: " + date);
        profile.put("armExtension", joe +  " arm extensions/date: "  + date);
        newDocumentReference.update(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CounterSave.this,"Error"+e.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }


}