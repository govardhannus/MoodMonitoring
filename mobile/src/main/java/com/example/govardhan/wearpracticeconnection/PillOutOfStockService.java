package com.example.govardhan.wearpracticeconnection;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PillOutOfStockService extends Service {

	public static final long serialVersionUID = 7526878481814893363L;
	public static PillOutOfStockService context = null;

    @Override
    public IBinder onBind(Intent intent) {
    	return null;
    }

    @Override
    public void onCreate() {
    	context = PillOutOfStockService.this;
    	super.onCreate();
    }
}