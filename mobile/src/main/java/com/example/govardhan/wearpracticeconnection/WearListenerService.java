package com.example.govardhan.wearpracticeconnection;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class WearListenerService extends WearableListenerService {
    private static Map<String, Integer> unSortMap = new LinkedHashMap<String, Integer>();
    public static String SERVICE_CALLED_WEAR = "WearListClicked";
    private static String TAG = "WearListenerService";

    private static String mCurrentTimestamp;
    private static String mCurrentValue;


    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);

        String event = messageEvent.getPath();

        Log.d(TAG, event);

        String[] message = event.split("--");

        if (message[0].equals(SERVICE_CALLED_WEAR)) {
            Log.d("APP", message[1]);
            add(Integer.parseInt(message[1]));
            saveInSharedPreference();
            // Throw a notification here.
            if(Integer.parseInt(message[1]) > 85) {
                PillNotificationUtility.notificationHighHeartRate(this, "Heart Beat Monitor", "The " + message[1] + " above the normal beat.", "");
            }
        }
    }

     public void saveInSharedPreference() {
     SharedPreferences settings = this.getSharedPreferences("MONITORING", 0);
     Gson gson = new Gson();
     String string = gson.toJson(unSortMap);
     if(string != null) {
         SharedPreferences.Editor editor = settings.edit();
         editor.putString("CurrentValue", mCurrentValue);
         editor.putString("CurrentTimeStamp", mCurrentTimestamp);
         editor.apply();
         editor.commit();
     }

 }
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> unsortMap) {

        List<Map.Entry<K, V>> list =
                new LinkedList<Map.Entry<K, V>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;

    }
    public static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey()
                    + " Value : " + entry.getValue());
        }
    }

    public static void add(Integer value){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        final String date = dateFormat.format(cal.getTime());
        unSortMap.put(date, value);
        System.out.println("Unsort Map......");
        printMap(unSortMap);
        currentRecordedValue(unSortMap);
    }

    public static void currentRecordedValue(Map<String, Integer> unsortMap){
        @SuppressWarnings("unchecked")
        Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) unsortMap.entrySet().toArray()[unsortMap.size() -1];
        mCurrentTimestamp = entry.getKey();
        mCurrentValue = entry.getValue().toString();
        System.out.println("Key : " + mCurrentTimestamp);
        System.out.println("Value : " + mCurrentValue);
    }


}
