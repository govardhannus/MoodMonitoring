package com.example.govardhan.wearpracticeconnection.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Govardhan on 13/5/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MoodMonitoring.db";
    private static final int DATABASE_VERSION = 1;
    SQLiteDatabase db;
    private static final String CREATE_LOGIN = "CREATE TABLE " + Constant.Login_Table
            + "("
            + Constant.LOGIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Constant.LOGIN_NAME + " TEXT NOT NULL, "
            + Constant.LOGIN_AGE + " INTEGER NOT NULL , "
            + Constant.LOGIN_GENDER + " TEXT NOT NULL );";

    private static final String CREATE_ANGRY = "CREATE TABLE " + Constant.ANGRY_Table
            + "("
            + Constant.ANGRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Constant.ANGRY + " TEXT NOT NULL , "
            + Constant.ANGRY_DATE + " TEXT NOT NULL );";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_LOGIN);
        db.execSQL(CREATE_ANGRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       //NO UPDATE
       // db.execSQL("INSERT INTO "+ Constant.ANGRY_Table+" VALUES (null, datetime()) ");
        Log.i("DATE IS CONNECTED", Constant.ErrorMsg_RecordNotAdded);
    }

    public boolean AddLoign(String name, String age , String gender ) {
        //consumption.getDate()
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues contantValues = new ContentValues();
            contantValues.put(Constant.LOGIN_NAME, name);
            contantValues.put(Constant.LOGIN_AGE, age);
            contantValues.put(Constant.LOGIN_GENDER, gender);

            db.insert(Constant.Login_Table, null, contantValues);
            return true;
        } catch (SQLiteException e) {
            Log.i("Login Added", Constant.ErrorMsg_RecordNotAdded);
            return false;
        } finally {
            db.close();
        }
    }

    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public long AddAngry(String angry,String date) {
        //Angry.getDate()
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues contantValues = new ContentValues();
            contantValues.put(Constant.ANGRY, angry);
            contantValues.put(Constant.ANGRY_DATE,date);
            long id =db.insertOrThrow(Constant.ANGRY_Table, null, contantValues);
            Log.d(DBHelper.class.getName(),"DB value" +id);
            return 0;
        } catch (SQLiteException e) {
            Log.i("Angry not Added", Constant.ErrorMsg_RecordNotAdded+e);
            return 1;
        } finally {
            db.close();
        }
    }

    public Cursor getAllAngry() {

        String[] columns = {Constant.ANGRY_ID,Constant.ANGRY, Constant.ANGRY_DATE};
        return db.query(Constant.ANGRY_Table, columns, null, null, null, null, null,null);
    }
}

