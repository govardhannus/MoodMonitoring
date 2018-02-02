package com.example.govardhan.wearpracticeconnection;

import android.support.v4.app.Fragment;


public class MonitorActivity extends BaseSingleFragmentActivity {

    // Abstract Method Implementation

    protected Fragment createFragment() {
        return new MonitorFragment();
    }
}
