package com.example.zhouge.tensorflow;


import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Trace;

import org.opencv.core.Mat;
import org.tensorflow.contrib.android.TensorFlowInferenceInterface;


public class LeNet5 {
    private static final String MODEL_FILE = "file:///android_asset/lenet5.pb"; //模型存放路径

    //模型输入数据
    private float[] inputs = new float[784];
    private float[] keep_prob = new float[1];

    //模型输出数据
    private float[] outputs = new float[4];

    TensorFlowInferenceInterface inferenceInterface;

    static {
        //加载库文件
        System.loadLibrary("tensorflow_inference");
    }

    LeNet5(AssetManager assetManager) {
        //接口定义
        inferenceInterface = new TensorFlowInferenceInterface(assetManager, MODEL_FILE);
    }

    private float GetR(int color) {
        return (float) ((color & 0xff0000) >> 16);
    }

    public int Recognize(Mat m) {

       /* int[] pixels = new int[784];
        bmp.getPixels(pixels, 0, 28, 0, 0, 28, 28);


        float pixelmin = GetR(pixels[0]);
        float pixelmax = GetR(pixels[0]);
        for (int i = 0; i < 784; i++) {
            if (pixelmin > GetR(pixels[i])) {
                pixelmin = GetR(pixels[i]);
            }
            if (pixelmax < GetR(pixels[i])) {
                pixelmax = GetR(pixels[i]);
            }
        }

        for (int i = 0; i < 784; i++) {
            inputs[i] = (GetR(pixels[i]) - pixelmin) / (pixelmax - pixelmin);
        }*/


       byte pixels[] = new byte[784];
       m.get(0,0,pixels);
       for(int i=0; i < 784; i++)
           inputs[i]=(float)(pixels[i]&0xFF);

        keep_prob[0] = 1.0f;

        Trace.beginSection("feed");
        inferenceInterface.feed("input", inputs, 784);
        inferenceInterface.feed("keep_prob", keep_prob);
        Trace.endSection();

        Trace.beginSection("run");
        String[] outputNames = new String[]{"output"};
        inferenceInterface.run(outputNames);
        Trace.endSection();

        Trace.beginSection("fetch");
        inferenceInterface.fetch("output", outputs);
        Trace.endSection();

        int bestIdx = 0;
        float best = outputs[0];
        for (int i = 0; i < outputs.length; i++) {
            if (outputs[i] > best) {
                bestIdx = i;
                best = outputs[i];
            }
        }

        return bestIdx;
    }
}
