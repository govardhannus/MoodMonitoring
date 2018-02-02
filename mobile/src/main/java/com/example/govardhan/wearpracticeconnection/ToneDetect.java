package com.example.govardhan.wearpracticeconnection;

import android.os.AsyncTask;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

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
 * Created by ashu on 26-05-2017.
 */

public class ToneDetect {
    private AudioDispatcher dispatcher;
    //TextView txtAverage,txtMaximum,txtMinimum,txtSD,txtSize;
    //Button recButton;

    int count = 0;
    float text,pitchInHz,average,max,min;
    float pitch[] = new float[50];
    double sd,value;

    public void process()
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

            }
        }));
        new Thread(dispatcher,"Audio Dispatcher").start();
    }
    public void StartPitchRecord() {
        new RecordPitch().execute("1");
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
            System.out.println("The predicted value of instance is "+ value); //0 is angry
            ///////////////////////////////////////////////////test one instance

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}