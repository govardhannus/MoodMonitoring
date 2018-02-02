package com.example.govardhan.wearpracticeconnection;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.govardhan.wearpracticeconnection.Database.DBHelper;

import java.util.Calendar;

public class ManualEntry extends AppCompatActivity {

    private Spinner personmood;
    static EditText angrypersondate;
    private Button manualentry;
    private String[] personmoodArray;
    private String spinnerString;
    private int yourInt;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_entry);

        dbHelper = new DBHelper(this);

        personmood = (Spinner) findViewById(R.id.AddPersonMood);
        personmoodArray = getResources().getStringArray(R.array.person_mood);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, personmoodArray);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        personmood.setAdapter(dataAdapter);
        personmood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerString = String.valueOf(position);

                switch(position){
                    case 0:
                        yourInt = 0;
                        break;

                    case 1:
                        yourInt = 1;
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
       // personmood.setOnItemSelectedListener(this);

        angrypersondate = (EditText) findViewById(R.id.AddDate);
        angrypersondate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonDatePickerDialog(v);
            }
        });

        manualentry = (Button) findViewById(R.id.Save);
        manualentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.AddAngry(spinnerString,angrypersondate.getText().toString());
//                  save(spinnerString,angrypersondate.getText().toString());
//                 finish();
            }
        });

    }
//    private void save(String angryperson, String angrydate) {
//
//       DBHelper dbHelper = new DBHelper(this);
//
//        long result = dbHelper.AddAngry(angryperson,angrydate);
//        if (result > 0) {
//
//            personmood.setTag(spinnerString);
//            angrypersondate.setText("");
//        }
//        else
//        {
//            Toast.makeText(getApplicationContext(), "Unable to insert", Toast.LENGTH_SHORT).show();
//        }
//    }


    public void showTruitonDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            angrypersondate.setText(year + "-" + (month + 1) + "-" + day );
        }
    }

}
