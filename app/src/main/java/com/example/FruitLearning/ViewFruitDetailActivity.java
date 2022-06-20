package com.example.FruitLearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.FruitLearning.data.FruitsInfo;
import com.example.FruitLearning.models.Fruit;

import java.io.IOException;
import java.io.InputStream;

public class ViewFruitDetailActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textViewInfo;
    TextView textViewName;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fruit_detail);

        imageView = findViewById(R.id.imageView);
        textViewInfo = findViewById(R.id.textViewInfo);
        textViewName = findViewById(R.id.textViewName);
        btnBack = findViewById(R.id.btnBack);

        Intent intent = getIntent();
        String id = intent.getStringExtra("fruit");
        Fruit fruits[] = FruitsInfo.fruits;
        Fruit temp = null;
        for (Fruit fruit: fruits
        ) {
            if (id.equals(fruit.getId()))
            {
                temp = fruit;
                break;
            }
        }

        textViewName.setText(temp.getName());
        textViewInfo.setText(temp.getDescription());

        try {
            // get input stream

            InputStream ims = getAssets().open(temp.image);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewFruitDetailActivity.this.finish();
            }
        });
    }
}