package com.example.FruitLearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.FruitLearning.adapters.FruitsAdapter;
import com.example.FruitLearning.models.Fruit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetailFruitActivity extends AppCompatActivity {

    private static final String TAG = "log";
    private RecyclerView rvFruits;
    private Button btnBack;
    private ArrayList<Fruit> mFruits = new ArrayList<Fruit>(){};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_fruit);

        rvFruits = findViewById(R.id.rv_fruits);
        btnBack = findViewById(R.id.btnBack);
        Bundle b = getIntent().getExtras();
        float[] classes = b.getFloatArray("classes");
        if (classes!=null && classes.length>0)
        {
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://learningfruit-209f2-default-rtdb.asia-southeast1.firebasedatabase.app");
            DatabaseReference myRef = database.getReference("Fruits");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for (float value: classes
                         ) {
                        Fruit fruit = dataSnapshot.child(String.valueOf(Math.round(value))).getValue(Fruit.class);
                        mFruits.add(fruit);
                    }

                    FruitsAdapter adapter = new FruitsAdapter(mFruits);
                    rvFruits.setAdapter(adapter);

                    rvFruits.setLayoutManager(new LinearLayoutManager(DetailFruitActivity.this));

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailFruitActivity.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

    }


}