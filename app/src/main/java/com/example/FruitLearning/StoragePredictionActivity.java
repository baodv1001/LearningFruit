package com.example.FruitLearning;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StoragePredictionActivity extends AppCompatActivity {
    private Button select_image;
    private Button view_detail;
    private Button btn_Back;
    private ImageView image_v;
    private objectDetectorClass objectDetectorClass;
    int SELECT_PICTURE=200;
    float[] classes = {};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_storage_prediction);

        select_image = findViewById(R.id.select_image);
        view_detail = findViewById(R.id.view_info_image);
        image_v = findViewById(R.id.image_v);
        btn_Back = findViewById(R.id.btnBack);
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StoragePredictionActivity.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        try{
            objectDetectorClass=new objectDetectorClass(getAssets(), "model.tflite","label.txt",320);
            Log.d("MainActivity","Model is successfully loaded");
        }
        catch (IOException e){
            Log.d("MainActivity","Getting some error");
            e.printStackTrace();
        }

        select_image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                chooseImage();
            }
        });

        view_detail.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                viewDetail();
            }
        });

        Bitmap bmp = null;
        String filename = getIntent().getStringExtra("image");
        try {
            FileInputStream is = this.openFileInput(filename);
            bmp = BitmapFactory.decodeStream(is);
            DetectionBitmap(bmp, false);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void viewDetail()
    {

            //Pop intent
            Bundle b = new Bundle();
            b.putFloatArray("classes", classes);
            Intent intent = new Intent(this, DetailFruitActivity.class);
            intent.putExtras(b);
            startActivity(intent);
    }

    private void chooseImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Pictrue"),SELECT_PICTURE);
    }

    public void onActivityResult (int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            if(requestCode==SELECT_PICTURE)
            {
                //success
                Uri selectedImageUri = data.getData();
                if(selectedImageUri != null)
                {
                    // check if empty or not
                    Log.d("Strage Prediction", "Output" + selectedImageUri);
                    // Read uri in bitmap format
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    // convert bitmap to Mat image
                    // 8UC4: RGBA image
                    // 8UC1: Grayscale image
                    DetectionBitmap(bitmap, false);
                }
            }
        }
    }

    public void DetectionBitmap(Bitmap bitmap, Boolean isRotate)
    {
        Mat selected_image = new Mat(bitmap.getHeight(), bitmap.getWidth(), CvType.CV_8UC4);
        Utils.bitmapToMat(bitmap, selected_image);

        Map.Entry e = isRotate ? objectDetectorClass.recognizeImage(selected_image) : objectDetectorClass.recognizePhoto(selected_image);
        selected_image = (Mat)e.getKey();
        List<Float> result  = (List<Float>) e.getValue();
        classes = new float[result.size()];
        int i = 0;

        for (Float f : result) {
            classes[i++] = (f != null ? f : Float.NaN); // Or whatever default you want.
        }

        // convert mat to bitmap
        Bitmap bitmap1 = null;
        bitmap1 = Bitmap.createBitmap(selected_image.cols(), selected_image.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(selected_image, bitmap1);
        // set bitmap to ImageView
        image_v.setImageBitmap(bitmap1);

        if (classes.length > 0) {
            view_detail.setEnabled(true);
        } else {
            view_detail.setEnabled(false);
        }
    }
}