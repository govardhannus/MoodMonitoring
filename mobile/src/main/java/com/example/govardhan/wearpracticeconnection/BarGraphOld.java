package com.example.govardhan.wearpracticeconnection;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class BarGraphOld extends AppCompatActivity {

    BarChart barChart;
    ArrayList<String> dates;
    Random random;
    ArrayList<BarEntry> barEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_graph_old);
        barChart = (BarChart) findViewById(R.id.barGraph);

        createRandomBarGraph("2017/05/05", "2017/06/01");
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
        BarData barData = new BarData(dates,barDataSet);
        barChart.setData(barData);
        barChart.setDescription("Anger Tracking Report");
    }

    public ArrayList<String> getList(Calendar startDate, Calendar endDate) {
        ArrayList<String> list = new ArrayList<>();
        while (startDate.compareTo(endDate) <= 0) {
            list.add(getDate(startDate));
            startDate.add(Calendar.DAY_OF_MONTH, 1);
        }
        return list;
    }

    public String getDate(Calendar cld) {
        String curDate = cld.get(Calendar.YEAR) + "/" + (cld.get(Calendar.MONTH) + 1) + "/" + cld.get(Calendar.DAY_OF_MONTH);
        try {
            Date date = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                date = new SimpleDateFormat("yyyy/MM/dd").parse(curDate);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                curDate = new SimpleDateFormat("yyyy/MM/dd").format(date);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }return curDate;

    }


}
