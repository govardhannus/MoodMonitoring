package com.example.govardhan.wearpracticeconnection;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.govardhan.wearpracticeconnection.Database.Constant;
import com.example.govardhan.wearpracticeconnection.Database.DBHelper;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.HashMap;

public class DrawGraph extends AppCompatActivity {

    GraphView graphView;
    DBHelper dbhelper;
    SQLiteDatabase sqlitedatabase;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbhelper = new DBHelper(this);
        sqlitedatabase = dbhelper.getWritableDatabase();

        graphView = (GraphView) findViewById(R.id.graph);

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(getDatapoint());
        graphView.addSeries(series);
    }

    private DataPoint[] getDatapoint() {

        String[] columns = {Constant.ANGRY,Constant.ANGRY_DATE};
        Cursor cursor = sqlitedatabase.query(Constant.ANGRY_Table,columns,null,null,null,null,null);

        DataPoint[] dp = new DataPoint[cursor.getCount()];

        HashMap<Integer,Integer> hashMap = new HashMap<>();
        hashMap.put(Integer.parseInt(cursor.getColumnName(2)),Integer.parseInt(cursor.getColumnName(1)));

//        for (i=0;i<cursor.getCount();i++);
//        {
//            cursor.moveToNext();
//            dp[i] = new DataPoint(cursor.getInt(0),cursor.getInt(1));
//        }
        return dp;
    }
}
