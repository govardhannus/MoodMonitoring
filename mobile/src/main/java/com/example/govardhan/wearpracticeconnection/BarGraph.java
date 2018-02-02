package com.example.govardhan.wearpracticeconnection;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.govardhan.wearpracticeconnection.Database.Constant;
import com.example.govardhan.wearpracticeconnection.Database.DBHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class BarGraph extends AppCompatActivity {

    BarChart barChart;
    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    BarDataSet barDataSet;
    ArrayList<BarEntry> barEntries;
    ArrayList<Angry> dates;
    Random random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        dbHelper = new DBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();
       // barChart = (BarChart) findViewById(R.id.barGraph);
    }

    public void createRandomBarGraph(String Date1, String Date2) {

        SimpleDateFormat simpleDateFormat = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        }

        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Date date1 = simpleDateFormat.parse(Date1);
                Date date2 = simpleDateFormat.parse(Date2);

                Calendar mDate1 = Calendar.getInstance();
                Calendar mDate2 = Calendar.getInstance();
                mDate1.clear();
                mDate2.clear();

                mDate1.setTime(date1);
                mDate2.setTime(date2);

                dates = new ArrayList<>();
                dates = getList(mDate1, mDate2);

                barEntries = new ArrayList<>();
                float max = 0f;
                float values = 0f;
                random = new Random();

                for(int j=0; j <dates.size();j++ )
                {
                    max = 50f;
                    values = random.nextFloat()*max;
                    barEntries.add(new BarEntry(values,j));

                }

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        BarDataSet barDataSet = new BarDataSet(barEntries,"Dates");
       // BarData barData = new BarData(dates,barDataSet);
        //barChart.setData(barData);
        barChart.setDescription("Anger Tracking Report");
    }

    public ArrayList<Angry> getList(Calendar startDate, Calendar endDate) {
        ArrayList<Angry> list = new ArrayList<>();
        while (startDate.compareTo(endDate) <= 0) {
            //list.add(getDate(startDate));
            list.addAll(getDate(startDate));
            startDate.add(Calendar.DAY_OF_MONTH, 1);
        }
        return list;
    }

    private ArrayList<Angry> getDate(Calendar startDate) {

        String[] columns = {Constant.ANGRY_DATE,Constant.ANGRY};
        Cursor cursor = sqLiteDatabase.query(Constant.ANGRY_Table,columns,null,null,null,null,null);

        ArrayList<Angry> angry = new ArrayList<>(cursor.getCount());

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String angryperson = cursor.getString(1);
            String date = cursor.getString(2);

            Angry angryguy = new Angry(id,angryperson, date);

            angry.add(angryguy);
        }

        return angry;

    }
}
