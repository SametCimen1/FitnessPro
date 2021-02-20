package com.fitness.fitnesspro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;

public class Activity2 extends AppCompatActivity {


    private  TextView textView98;
    private  Button Button21, Button212;
    private DatePicker datePicker;
    private int day = 0;
    private int month = 0;
    private int year = 0;
    private String gender;


private String date = "";

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN , (WindowManager.LayoutParams.FLAG_FULLSCREEN) );

gender = getIntent().getStringExtra("genderKey");

        setContentView ( R.layout.activity_2 );
        Button21 = ( Button ) findViewById ( R.id.button21 );
        datePicker= findViewById ( R.id.datePicker );
        Button212= findViewById ( R.id.button212 );
        textView98=findViewById ( R.id.textView98 );
        Button212.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                 day= datePicker.getDayOfMonth ();
                 month= datePicker.getMonth ();
                 year= datePicker.getYear ();

                textView98.setText ( day + "/" + month + "/" + year );
                String StringDay = Integer.toString(day);
                String StringMonth = Integer.toString(month);
                String StringYear = Integer.toString(year);

                date = StringDay + StringMonth + StringYear;

            }
        } );

        Button21.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                openLB ( );

            }
        } );
    }

    public void openLB ( ) {
        if(day==0||month==0||year==0){
            Toast.makeText(Activity2.this, "Please choose a date if you choose please dont forget to click 'select date'", Toast.LENGTH_LONG).show();
            return;
        }


        Intent intent = new Intent ( Activity2.this , LB.class );
        intent.putExtra("dateKey", date);
        intent.putExtra("genderKey", gender);

        startActivity (intent);
        overridePendingTransition ( R.anim.right , R.anim.left );

    }

}
