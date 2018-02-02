package com.example.govardhan.wearpracticeconnection;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UIUtils {


    public static final String MY_FORMAT = "dd/MM/yy";

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MY_FORMAT, Locale.US);
    private static String mExpDate;

    //Plus Icon handler
    public static String setPlusQuantity(EditText editText, int count) {
        if (editText.getText().toString().trim().length() <= 0)
            count = 0;
        else
            count = Integer.parseInt(editText.getText().toString());
        count++;
        String plusQuantity = String.valueOf(count);
        editText.setText(plusQuantity);
        return plusQuantity;    }

    //Minus Icon handler
    public static String setMinusQuantity(EditText editText, int count) {
        if (editText.getText().toString().trim().length() <= 0)
            count = 0;
        else
            count = Integer.parseInt(editText.getText().toString());
        if (count > 0 && count != 0) {
            count--;
        }
        String minusQuantity = String.valueOf(count);
        editText.setText(minusQuantity);
        return minusQuantity;
    }

    //To select Date from Calender View
    public static String selectCalender(Fragment fragment, final EditText editText) {
        final Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(fragment.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                mcurrentDate.set(Calendar.YEAR, selectedyear);
                mcurrentDate.set(Calendar.MONTH, selectedmonth);
                mcurrentDate.set(Calendar.DAY_OF_MONTH, selectedday);
                try {
                    mExpDate = simpleDateFormat.format(mcurrentDate.getTime());
                    editText.setText(mExpDate);
                    Date mDateFormatExpDate = simpleDateFormat.parse(simpleDateFormat.format(mcurrentDate.getTime()));
                } catch (Exception e) {
                    Log.i("DATE", "Date parsing error");
                }
            }
        }, mYear, mMonth, mDay);
        mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        mDatePicker.setTitle("Select date");
        mDatePicker.show();
        if (mExpDate == null)
            mExpDate = simpleDateFormat.format(mcurrentDate.getTime());
        return mExpDate;
    }

    //Checking Internet Connection
    public static boolean isNetworkAvailable(Fragment fragment) {
    ConnectivityManager connectivityManager
            = (ConnectivityManager) fragment.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
}
}
