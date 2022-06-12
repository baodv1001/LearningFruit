package com.example.FruitLearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.InputStream;

public class SplashActivity extends AppCompatActivity {

    ImageView logo_imgv;
    ImageView kid_imgv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        }, 3000);

        logo_imgv = findViewById(R.id.logo);
        kid_imgv = findViewById(R.id.kid);
        try {
            // get input stream

            InputStream ims = getAssets().open("images/Logo.png");
            InputStream ims1 = getAssets().open("images/kid.png");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            Drawable d1 = Drawable.createFromStream(ims1, null);
            // set image to ImageView
            logo_imgv.setImageDrawable(d);
            kid_imgv.setImageDrawable(d1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void nextActivity() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent;
        if (user == null) {
            intent = new Intent(this, LoginActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
    }
}