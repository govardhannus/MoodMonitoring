package com.example.govardhan.wearpracticeconnection;

import android.app.IntentService;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.util.Log;

import java.util.Locale;

/**
 * Created by Govardhan on 21/5/2017.
 */

public class BackgroundServicenew extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    TextSpeech textSpeech;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    public  static final String TAG="com.example.govardhan.wearpracticeconnection";
    //private final int REQ_CODE_SPEECH_INPUT = 100;
    public BackgroundServicenew(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        //String action = intent.getAction();
        //textSpeech.promptSpeechInput();
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


    public void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Hi speak something");
        try {
         //   startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }
}
