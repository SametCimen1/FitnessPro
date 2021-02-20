package com.fitness.fitnesspro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Activity4 extends AppCompatActivity {
    private TextView textView, textView1;
    SeekBar seekbar33, seekBar22;
private ImageButton nextButton;
private int kg = 0;
    FirebaseFirestore fStore;
private String date = null;
private String gender = null;
private int cm = 0;
private String email = null;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN , (WindowManager.LayoutParams.FLAG_FULLSCREEN) );
        setContentView ( R.layout.activity_4 );
nextButton = findViewById(R.id.imageButton3143);
        textView = ( TextView ) findViewById ( R.id.textView2334 );
        textView1 = ( TextView ) findViewById ( R.id.textView81212 );

        fStore= FirebaseFirestore.getInstance();
        date = getIntent().getStringExtra("dateKey");
        gender = getIntent().getStringExtra("genderKey");

        seekbar33 = ( SeekBar ) findViewById ( R.id.seekBar33 );
        seekBar22  = (SeekBar )  findViewById ( R.id.seekBar12 );
        FirebaseAuth  firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        if(user == null && accessToken == null){
            Intent goBackIntent = new Intent(Activity4.this, loginActivity.class);
            startActivity(goBackIntent);
            finish();
        }
        else{
            if(user!=null){
                email = user.getEmail();
            }
        }

        if(email == null)
            Toast.makeText(Activity4.this, "email is null", Toast.LENGTH_SHORT).show();





        seekbar33.setOnSeekBarChangeListener ( new SeekBar.OnSeekBarChangeListener ( ) {
            @Override
            public void onProgressChanged ( SeekBar seekBar , int progress , boolean fromUser ) {
                kg = progress;
                textView1.setText ( "" + progress + "/226 KG" );
            }

            @Override
            public void onStartTrackingTouch ( SeekBar seekBar ) {
            }

            @Override
            public void onStopTrackingTouch ( SeekBar seekBar ) {


            }
        } );
        seekBar22.setOnSeekBarChangeListener ( new SeekBar.OnSeekBarChangeListener ( ) {
            @Override
            public void onProgressChanged ( SeekBar seekBar , int progress , boolean fromUser ) {
                cm = progress;
                textView.setText ( "" + progress + "/218 CM" );
            }

            @Override
            public void onStartTrackingTouch ( SeekBar seekBar ) {
            }

            @Override
            public void onStopTrackingTouch ( SeekBar seekBar ) {
            }
        } );

nextButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(kg==0 || cm == 0){
            Toast.makeText(Activity4.this, "Please enter your weight and height", Toast.LENGTH_LONG).show();
       return;
        }
        else {

            InsertData();
            Intent intent = new Intent(Activity4.this, AfterLogin.class);
            startActivity(intent);
            finish();
        }
    }
});
    }
    public void InsertData(){


      DocumentReference newDocumentReference = fStore.collection("users").document(email);
        Map<String, Object> profile = new HashMap<>();
        profile.put("gender", gender);
        profile.put("birthday", date);
        profile.put("preferred data Type", "Kg/Cm");
        profile.put("kg", kg);
        profile.put("cm", cm);
        newDocumentReference.update(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Activity4.this,"Error"+e.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}