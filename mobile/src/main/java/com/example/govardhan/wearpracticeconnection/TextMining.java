package com.example.govardhan.wearpracticeconnection;

import android.support.v7.app.AppCompatActivity;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.lm.NGramProcessLM;
import com.aliasi.util.Files;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by ashu on 04-04-2017.
 */

public class TextMining extends AppCompatActivity {
    File mPolarityDir;
    String[] mCategories;
    DynamicLMClassifier<NGramProcessLM> mClassifier;

    TextMining() {
        System.out.println("\nBASIC POLARITY DEMO");
        mPolarityDir = new File("/sdcard/text");
        mCategories = mPolarityDir.list();
        int nGram = 3;
        mClassifier = DynamicLMClassifier.createNGramProcess(mCategories,nGram);
    }
    int run() throws ClassNotFoundException, IOException {
        train();
        //evaluate();
        return text_classify();
    }
    boolean isTrainingFile(File file) {
        //System.out.println(file.getName().charAt(2));
        return file.getName().charAt(1) != 'a';  // test on fold 9

    }
    void train() throws IOException {
        int numTrainingCases = 0;
        int numTrainingChars = 0;
        System.out.println("\nTraining.");
        for (int i = 0; i < mCategories.length; ++i) {
            String category = mCategories[i];
            Classification classification = new Classification(category);
            File file = new File(mPolarityDir,mCategories[i]);
            File[] trainFiles = file.listFiles();
            for (int j = 0; j < trainFiles.length; ++j) {
                File trainFile = trainFiles[j];
                if (isTrainingFile(trainFile)) {
                    ++numTrainingCases;
                    String review = Files.readFromFile(trainFile,"ISO-8859-1");
                    numTrainingChars += review.length();
                    Classified<CharSequence> classified =
                            new Classified<CharSequence>(review,classification);
                    mClassifier.handle(classified);
                }
            }
        }
        System.out.println("  # Training Cases=" + numTrainingCases);
        System.out.println("  # Training Chars=" + numTrainingChars);
    }
    void evaluate() throws IOException {
        System.out.println("\nEvaluating.");
        int numTests = 0;
        int numCorrect = 0;
        for (int i = 0; i < mCategories.length; ++i) {
            String category = mCategories[i];
            File file = new File(mPolarityDir,mCategories[i]);
            File[] trainFiles = file.listFiles();
            for (int j = 0; j < trainFiles.length; ++j) {
                File trainFile = trainFiles[j];
                if (!isTrainingFile(trainFile)) {
                    String review = Files.readFromFile(trainFile,"ISO-8859-1");
                    ++numTests;
                    Classification classification = mClassifier.classify(review);
                    if (classification.bestCategory().equals(category)){
                        ++numCorrect;
                        //System.out.println(classification.bestCategory());
                    }
                }
            }
        }
        System.out.println("  # Test Cases=" + numTests);
        System.out.println("  # Correct=" + numCorrect);
        System.out.println("  % Correct=" + ((double)numCorrect)/(double)numTests);
    }
    int text_classify() throws IOException {
        int flag = 0;
        File file = new File("/sdcard/Speech.txt");
        FileReader reader = new FileReader(file);
        int fileLen = (int)file.length();
        char[] chars = new char[fileLen];
        reader.read(chars);
        String txt = String.valueOf(chars);
        System.out.println(txt);
        Classification classification = mClassifier.classify(txt);
        //Toast.makeText(getApplicationContext()," classifying...", Toast.LENGTH_LONG).show();
        System.out.println(classification.bestCategory().toString());
        if (classification.bestCategory().equals("neg"))
            flag = 1;
        //System.out.println(flag);
        return flag;
    }
}
