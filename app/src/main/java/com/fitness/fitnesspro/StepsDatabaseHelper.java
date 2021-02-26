package com.fitness.fitnesspro;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


public class StepsDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "steps_table";
    private static final String COL1 = "Date";
    private static final String COL2 = "steps";

     public StepsDatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
       String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT)";
       db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
     onCreate(db);
    }
    public boolean addData(String item){
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues contentValues = new ContentValues();
         contentValues.put(COL2, item);
        Log.d(TAG, " addData : adding " + item  + " to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);
       // if(data inserted correctly return else)
        if(result ==-1){
            return false;
        }
       else{
           return true;
        }
     }
}
