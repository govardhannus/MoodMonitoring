package com.example.govardhan.wearpracticeconnection.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.govardhan.wearpracticeconnection.Angry;
import com.example.govardhan.wearpracticeconnection.Login;

/**
 * Created by Govardhan on 13/5/2017.
 */

public class DAO extends DBHelper{

    public DAO(Context context) {
        super(context);
    }

    public boolean AddLoign(Login login) {
        //consumption.getDate()
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues contantValues = new ContentValues();
            contantValues.put(Constant.LOGIN_ID, login.getId());
            contantValues.put(Constant.LOGIN_NAME, login.getName());
            contantValues.put(Constant.LOGIN_AGE, login.getAge());
            contantValues.put(Constant.LOGIN_GENDER, login.getGender());

            db.insert(Constant.Login_Table, null, contantValues);
            return true;
        } catch (SQLiteException e) {
            Log.i("Consumption Added", Constant.ErrorMsg_RecordNotAdded);
            return false;
        } finally {
            db.close();
        }
    }

//    public boolean AddAngry(Angry angry) {
//        //consumption.getDate()
//        SQLiteDatabase db = this.getWritableDatabase();
//        try {
//            ContentValues contantValues = new ContentValues();
//            contantValues.put(Constant.ANGRY_ID, angry.getId());
//            contantValues.put(Constant.LOGIN_NAME, angry.getAngry());
//            contantValues.put(Constant.LOGIN_AGE, angry.getDate());
//            contantValues.put(Constant.LOGIN_GENDER, angry.getTime());
//
//            db.insert(Constant.ANGRY_Table, null, contantValues);
//            return true;
//        } catch (SQLiteException e) {
//            Log.i("Consumption Added", Constant.ErrorMsg_RecordNotAdded);
//            return false;
//        } finally {
//            db.close();
//        }
//    }

    public boolean AddAngry(Angry angry) {
        //consumption.getDate()
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues contantValues = new ContentValues();
            contantValues.put(Constant.ANGRY_ID, angry.getId());
            contantValues.put(Constant.ANGRY_DATE, angry.getDate());
            contantValues.put(Constant.ANGRY, angry.getAngry());
            db.insert(Constant.ANGRY_Table, null, contantValues);
            return true;
        } catch (SQLiteException e) {
            Log.i("Angry Added", Constant.ErrorMsg_RecordNotAdded);
            return false;
        } finally {
            db.close();
        }
    }

}
