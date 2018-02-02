package com.example.govardhan.wearpracticeconnection;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


/**
 * Created by Govardhan on 20/5/2017.
 */

public class BackgroundService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    TextSpeech textSpeech = new TextSpeech();
    public  static final String TAG="com.example.govardhan.wearpracticeconnection";
    //private final int REQ_CODE_SPEECH_INPUT = 100;
    public BackgroundService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        //String action = intent.getAction();
        textSpeech.promptSpeechInput();
        Log.i(TAG,"service started");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
