package com.example.govardhan.wearpracticeconnection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MonitorFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    VoiceSpeech voiceSpeech;

    String mHighTimeStamp;
    String mHighValue;
    String mCurrentValue;
    String mLowValue;
    String mLowTimeStamp;
    String mCurrentTime;

    @Bind(R.id.current_Heart_Rate)
    TextView mCurrentHeartRate;

    @Bind(R.id.current_Heart_Rate_Time_Stamp)
    TextView mCurrentTimeStamp;
    public MonitorFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_monitor_recreated, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        NavigationUtility.addAnimationToHardwareBackButtonForFragment(this);
        readSharePreferencesAndUpdateUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences preferences = getActivity().getSharedPreferences("MONITORING", 0);
        preferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        SharedPreferences preferences = getActivity().getSharedPreferences("MONITORING", 0);
        preferences.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        readSharePreferencesAndUpdateUI();
    }

    public void readSharePreferencesAndUpdateUI(){
        SharedPreferences preferences = getActivity().getSharedPreferences("MONITORING", 0);
        mCurrentTime = preferences.getString("CurrentTimeStamp","01/01/2999 12:12:12");
        mCurrentValue = preferences.getString("CurrentValue","73");
        heartRateValueSet();
    }
    public void heartRateValueSet(){
        mCurrentHeartRate.setText(mCurrentValue);
        mCurrentTimeStamp.setText(mCurrentTime);
        changeHeartRateIndication();
    }

    public void changeHeartRateIndication(){

        if(Integer.parseInt(mCurrentValue) >= 65 ) {
            mCurrentHeartRate.setTextColor(Color.parseColor("#00796B"));
            Intent intent = new Intent(getActivity(), TextSpeech.class);
            startActivity(intent);
//            Intent intent = new Intent(getActivity(),BackgroundService.class);
//            getActivity().startService(intent);
        } else {
            mCurrentHeartRate.setTextColor(Color.parseColor("#FFB300"));
        }

    }

}
