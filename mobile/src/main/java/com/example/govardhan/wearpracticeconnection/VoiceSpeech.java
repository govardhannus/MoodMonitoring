package com.example.govardhan.wearpracticeconnection;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Govardhan on 6/3/2017.
 */

public class VoiceSpeech extends AppCompatActivity {

    private TextView txtSpeechInput;
    private TextView textSpeach;
    private final int REQ_CODE_SPEECH_INPUT = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_speech);

        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        textSpeach = (TextView) findViewById(R.id.btnSpeak);

//        textSpeach.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                promptSpeechInput();
//            }
//        });

        promptSpeechInput();

    }

    // Showing google speech input dialog

    void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Hi speak something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    // Receiving speech input

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT:
            {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));





                    // Get the directory for the user's public pictures directory.
                    final File path =
                            Environment.getExternalStoragePublicDirectory
                                    (
                                            //Environment.DIRECTORY_PICTURES
                                            Environment.DIRECTORY_DCIM + "/Sujit/"
                                    );

                    // Make sure the path directory exists.
                    if(!path.exists())
                    {
                        // Make it, if it doesn't exit
                        path.mkdirs();
                    }

                    final File file = new File(path, "config.txt");

                    // Save your stream, don't forget to flush() it before closing it.

                    try
                    {
                        file.createNewFile();
                        FileOutputStream fOut = new FileOutputStream(file);
                        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                        myOutWriter.append((CharSequence) result);

                        myOutWriter.close();

                        fOut.flush();
                        fOut.close();
                    }
                    catch (IOException e)
                    {
                        Log.e("Exception", "File write failed: " + e.toString());
                    }
                }
                break;
            }
        }
    }
}