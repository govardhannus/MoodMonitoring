package com.example.govardhan.wearpracticeconnection;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.govardhan.wearpracticeconnection.Database.Constant;
import com.example.govardhan.wearpracticeconnection.Database.DBHelper;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView txtSpeechInput;
    private TextView textSpeach;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    LinearLayout mMonitor,mBarGraph;

    LinearLayout mSpeech;

    GraphView graphView;
    DBHelper dbhelper;
    SQLiteDatabase sqlitedatabase;
    //private int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMonitor = (LinearLayout)findViewById(R.id.monitor);
        mMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, MonitorActivity.class);
                startActivity(intent);
            }
        });

        dbhelper = new DBHelper(this);
        sqlitedatabase = dbhelper.getWritableDatabase();

        graphView = (GraphView) findViewById(R.id.graph);

//        graphView.getViewport().setXAxisBoundsManual(true); // These lines seem to be causing it
//        graphView.getViewport().setMinX(1);
//        graphView.getViewport().setMaxX(50);

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(getDatapoint());
        graphView.addSeries(series);
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });
        series.setSpacing(30);
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
//        Viewport viewport = graphView.getViewport();
//        viewport.setMinY(0);
//        viewport.setMaxY(10);
//        viewport.setScrollable(true);

//        graphView.setScaleX(2.0f);
//        graphView.setScaleY(2.0f);



//        mBarGraph = (LinearLayout)findViewById(R.id.barGraph);
//        mBarGraph.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, DrawGraph.class);
//                startActivity(intent);
//            }
//        });
    }

    private DataPoint[] getDatapoint() {

        String[] columns = {Constant.ANGRY,Constant.ANGRY_DATE};
        Cursor cursor = sqlitedatabase.query(Constant.ANGRY_Table,columns,null,null,null,null,null);


        DBHelper dbHelper = new DBHelper(this);
        Map<Date,Integer> Hmap = new HashMap<Date,Integer>();
      //  Cursor angrycursor = dbHelper.getAllAngry();
        while (cursor.moveToNext()){
             String date= cursor.getString(1);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date newdate = null;
            try {
                newdate = format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int angry = Integer.parseInt(cursor.getString(0));
             angry = (angry == 0 ? 1:0);

            if(Hmap.containsKey(newdate)) {
                int count = Hmap.get(newdate);
                Hmap.put(newdate,count+angry);
            }
            else {
                Hmap.put(newdate, angry);
            }
        }

        Log.d("hashmap",String.valueOf(Hmap));
// }

        DataPoint[] dp = new DataPoint[Hmap.size()];

            //cursor.moveToNext();
//           while (cursor.moveToNext()) {
        Iterator<Date> iterator = Hmap.keySet().iterator();
        int i = 0;
                   while(iterator.hasNext())
                   {
                       Date tempDate = iterator.next();
                       cursor.moveToFirst();
                       dp[i++] = new DataPoint(tempDate.hashCode(), Hmap.get(tempDate));

//              while (cursor.moveToNext()) {
//
//                   System.out.println("key=" + entry.getKey() + ", value=" + entry.getValue());
//               }
//               }
           }

        return dp;

    }

    private Date milliToDate(long milliSec){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSec);
        return calendar.getTime();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), ManualEntry.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
