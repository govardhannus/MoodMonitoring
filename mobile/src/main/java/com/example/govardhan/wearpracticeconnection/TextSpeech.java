package com.example.govardhan.wearpracticeconnection;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.govardhan.wearpracticeconnection.Database.DBHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Locale;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;
import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

/**
 * Created by Govardhan on 1/4/2017.
 */

public class TextSpeech extends AppCompatActivity {

    private TextView txtSpeechInput,moodmonitoringinput;
    private TextView textSpeach;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    DBHelper dbHelper;
    boolean isinserted;


    //Tone part variables.
    private AudioDispatcher dispatcher;
    int count = 0;
    float text,pitchInHz,average,max,min;
    float pitch[] = new float[50];
    double sd,value;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_speech);
        dbHelper = new DBHelper(this);
        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        moodmonitoringinput = (TextView) findViewById(R.id.MoodMonitoringInput);


        promptSpeechInput();
        //ToneDetect();
    }

    // Showing google speech input dialog

    public void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
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

                    String filename="Speech.txt";
                    String data1=result.get(0);

                    FileOutputStream fos;
                    try {
                        File myFile = new File("/sdcard/"+filename);
                        myFile.createNewFile();
                        FileOutputStream fOut = new FileOutputStream(myFile);
                        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                        myOutWriter.append(data1);
                        myOutWriter.close();
                        fOut.close();
                        Toast.makeText(getApplicationContext(),filename + " saved", Toast.LENGTH_LONG).show();

                         //dbHelper.AddAngry(checkAnger().toString(),dbHelper.getDateTime().toString());
//                        if (isinserted == true) {
//                            Log.i("LoginPageMain","isinserted value is : " +isinserted);
//                            Toast.makeText(getApplicationContext(), " Angry is inserted", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Angry Not inserted", Toast.LENGTH_SHORT).show();
//                        }

                        ToneDetect();
                        //checkAnger();

                    } catch (FileNotFoundException e) {e.printStackTrace();}
                    catch (IOException e) {e.printStackTrace();}
                }
                break;
            }
        }
    }

    public String checkAnger() {
        String result = "";
        try {
            int i = new TextMining().run();
            if(i==1&&value==0) {
                Toast.makeText(getApplicationContext(),"You are angry!", Toast.LENGTH_LONG).show();
                txtSpeechInput.setText(String.valueOf(i));
                txtSpeechInput.setText( "Angry");
            }
            else if(i==1&&value==1) {
                Toast.makeText(getApplicationContext(),"You are angry!", Toast.LENGTH_LONG).show();
                txtSpeechInput.setText(String.valueOf(i));
                txtSpeechInput.setText( "Angry");
                result = "0";
                return result;
            }
            else {
                Toast.makeText(getApplicationContext(),"You are not angry!", Toast.LENGTH_LONG).show();
                txtSpeechInput.setText(String.valueOf(i));
                txtSpeechInput.setText(" Not Angry");
                result = "1";
                return result;
            }
        } catch (Throwable t) {
            System.out.println("Thrown: " + t);
            t.printStackTrace(System.out);
        }

        return result;
    }


    

    public void ToneDetect()
    {
        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050,1024,0);
        dispatcher.addAudioProcessor(new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, new PitchDetectionHandler() {

            @Override
            public void handlePitch(PitchDetectionResult pitchDetectionResult,
                                    AudioEvent audioEvent) {
                //final float pitchInHz = pitchDetectionResult.getPitch();
                pitchInHz = pitchDetectionResult.getPitch();
                //runOnUiThread(new Runnable() {
                //    @Override
                //    public void run() {
                //        TextView text = (TextView) findViewById(R.id.txtFrequency);
                //        text.setText("" + pitchInHz);
                //    }
                //});
//                new RecordPitch().execute("1");
            }
        }));
        new Thread(dispatcher,"Audio Dispatcher").start();
        new RecordPitch().execute("1");
    }

    public void classify() {
        Instances ins = null;

        Classifier cfs = null;
        try{

            File file= new File("/sdcard/DC.arff");
            ArffLoader loader = new ArffLoader();
            loader.setFile(file);
            ins = loader.getDataSet();


            //Instances A = ins.getDataSet();
            ins.instance(0).setValue(0,average);/////   pitch_ave   //////
            ins.instance(0).setValue(1,sd);/////    pitch_sd   //////
            System.out.println(ins.instance(0));

            ins.setClassIndex(ins.numAttributes()-1);

            ///////////////////////////////////////////////// test one instance
            Classifier cls = (Classifier) weka.core.SerializationHelper.read("/sdcard/Tone.model");// model from outside
            value=cls.classifyInstance(ins.instance(0));
            //System.out.println("The predicted value of instance is "+ value); //0 is angry
            //Toast.makeText(getApplicationContext(), "Tone Result=" + value , Toast.LENGTH_SHORT).show();
            checkAnger();
            dbHelper.AddAngry(checkAnger().toString(),dbHelper.getDateTime().toString());
            ///////////////////////////////////////////////////test one instance

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private class RecordPitch extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            Looper.prepare();

            //get pitch for 5 seconds
            count=0;
            int timer=0;
            while (timer < 50) {
                if(pitchInHz > 50 && pitchInHz < 400) {
                    pitch[count] = pitchInHz;
                    count++;
                    timer++;
                }
                else{
                    timer++;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //calculate average,mim and max pitch
            average = avg(pitch);
            max = max(pitch);
            min = min(pitch);
            sd = getStdDev(pitch);
            //Toast.makeText(getApplicationContext(), "Average=" + average + "\nSD=" + sd, Toast.LENGTH_SHORT).show();
            classify();
            //Toast.makeText(getApplicationContext(), "The predicted value of instance is "+ value, Toast.LENGTH_LONG).show();

            Looper.loop();
            return "sdff";
        }

//        @Override
//        protected void onPostExecute(String result) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    txtAverage.setText(String.format("%.2f", average));
//                    txtMaximum.setText(String.format("%.2f", max));
//                    txtMinimum.setText(String.format("%.2f", min));
//                    txtSD.setText(String.format("%.2f", sd));
//                    txtSize.setText(""+count);
//                }
//            });
//        }

        float avg(float[] arr){
            float total = 0;
            for (int i = 0; i < count; i++) {
                total+=arr[i];
            }
            return total/count;
        }

        float max(float[] arr){
            float maximum = arr[0];
            for (int i = 0; i < count; i++) {
                if (arr[i] > maximum) {
                    maximum = arr[i];
                }
            }
            return maximum;
        }

        float min(float[] arr){
            float minimum = arr[0];
            for (int i = 0; i < count; i++) {
                if (arr[i] < minimum) {
                    minimum = arr[i];
                }
            }
            return minimum;
        }
        float getVariance(float[] arr)
        {
            float mean = average;
            float temp = 0;
            for(float a :arr)
                temp += (a-mean)*(a-mean);
            return temp/count;
        }

        double getStdDev(float[] arr)
        {
            return Math.sqrt(getVariance(arr));
        }
    }

}