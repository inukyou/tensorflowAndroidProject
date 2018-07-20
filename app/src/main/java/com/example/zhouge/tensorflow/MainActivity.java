package com.example.zhouge.tensorflow;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;




public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Used to load the 'native-lib' library on application startup.
    static {
        //System.loadLibrary("native-lib");
        OpenCVLoader.initDebug();
        System.loadLibrary("tensorflow_inference");
    }

    PaletteView paletteView;

    Button ok, clear;

    TextView resultText;
    Bitmap bitmap;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        paletteView = (PaletteView) findViewById(R.id.draw_view);
        ok = (Button) findViewById(R.id.ok);
        clear = (Button) findViewById(R.id.clear);
        imageView = (ImageView) findViewById(R.id.image_view);

        paletteView.setPenRawSize(15);

        ok.setOnClickListener(this);
        clear.setOnClickListener(this);

        resultText = (TextView) findViewById(R.id.result_text);

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok: {
                bitmap = paletteView.buildBitmap();
                Mat m = new Mat();
                Utils.bitmapToMat(bitmap, m);

                //bitmap.recycle();
                Size size = new Size(28, 28);
                Mat grayMat = new Mat();
                Mat smallMat = new Mat();

                Imgproc.resize(m, smallMat, size, 0, 0, Imgproc.INTER_AREA);
                Imgproc.cvtColor(smallMat, grayMat, Imgproc.COLOR_BGRA2GRAY);

                Bitmap newBitMap = Bitmap.createBitmap(28, 28, Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(smallMat, newBitMap);

                imageView.setImageBitmap(newBitMap);

                Toast.makeText(this, grayMat.toString(), Toast.LENGTH_LONG).show();

                LeNet5 leNet5 = new LeNet5(getAssets());
                int result = leNet5.Recognize(smallMat);
                switch (result){
                    case 0:resultText.setText("猫");break;
                    case 1:resultText.setText("圆圈");break;
                    case 2:resultText.setText("鱼");break;
                    case 3:resultText.setText("花");break;
                }



                break;
            }
            case R.id.clear:
                paletteView.clear();
                break;
        }
    }

}