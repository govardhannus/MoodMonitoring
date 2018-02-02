package com.example.govardhan.wearpracticeconnection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.govardhan.wearpracticeconnection.Database.DAO;
import com.example.govardhan.wearpracticeconnection.Database.DBHelper;

public class LoginPageMain extends AppCompatActivity {

    EditText editTextusername, editTextuserage,editTextusergender;
    Button buttonStart;
    DBHelper dbHelper;
    boolean isinserted;
    DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        dbHelper = new DBHelper(this);
        editTextusername = (EditText) findViewById(R.id.textInputEditTextName);
        editTextuserage = (EditText) findViewById(R.id.textInputEditTextAge);
        editTextusergender = (EditText) findViewById(R.id.textInputEditTextGender);

        buttonStart = (Button) findViewById(R.id.appCompatButtonStart);
       // buttonStart.setOnClickListener(this);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isinserted =
                        dbHelper.AddLoign(editTextusername.getText().toString(),
                                editTextuserage.getText().toString(),
                                editTextusergender.getText().toString());
                {
                    if (isinserted == true) {
                        Log.i("LoginPageMain","isinserted value is : " +isinserted);
                        Toast.makeText(getApplicationContext(), "inserted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginPageMain.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Not inserted", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

//    @Override
//    public void onClick(View v) {
//        if ((v == buttonStart)) {
//            String userName = editTextusername.getText().toString().trim();
//            String userAge = editTextuserage.getText().toString().trim();
//
//            if (userName.equals("")) {
//                editTextusername.setError("Please enter username to proceed");
//
//            }
//
//            else if (userAge.equals(0)) {
//                editTextuserage.setError("User age cannot be set to 0");
//
//            }
//            else if (userAge.equals("")) {
//                editTextuserage.setError("User age cannot be blank");
//
//            }
//            else {
//                Intent intent = new Intent(LoginPageMain.this,MainActivity.class);
//                startActivity(intent);
//            }
//
//        }
//    }
}
