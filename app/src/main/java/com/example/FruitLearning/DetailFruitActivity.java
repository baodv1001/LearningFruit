package com.example.FruitLearning;

import androidx.annotation.IntRange;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.FruitLearning.data.FruitsInfo;
import com.example.FruitLearning.adapters.FruitsAdapter;
import com.example.FruitLearning.models.Fruit;
import com.example.FruitLearning.utils.Array;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class DetailFruitActivity extends AppCompatActivity {

    private static final String TAG = "log";
    private RecyclerView rvFruits;
    private Button btnBack;
    private ArrayList<Fruit> mFruits = new ArrayList<Fruit>() {
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_fruit);

        rvFruits = findViewById(R.id.rv_fruits);
        btnBack = findViewById(R.id.btnBack);
        Bundle b = getIntent().getExtras();
        float[] classes = b.getFloatArray("classes");

        if (classes != null && classes.length > 0) {

            LoadFromLocal(classes);

            if (getConnectionType(this) != 0)
            {
                LoadFromFireBase(classes);
            }
            else
            {
                LoadFromLocal(classes);
            }

        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailFruitActivity.this, StoragePredictionActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }

    @IntRange(from = 0, to = 3)
    public static int getConnectionType(Context context) {
        int result = 0; // Returns connection type. 0: none; 1: mobile data; 2: wifi
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (cm != null) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        result = 2;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        result = 1;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                        result = 3;
                    }
                }
            }
        } else {
            if (cm != null) {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    // connected to the internet
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        result = 2;
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        result = 1;
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_VPN) {
                        result = 3;
                    }
                }
            }
        }
        return result;
    }

    private void LoadFromFireBase(float[] classes)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://learningfruit-209f2-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Fruits");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (float value : classes) {
                    Fruit fruit = dataSnapshot.child(String.valueOf(Math.round(value))).getValue(Fruit.class);
                    mFruits.add(fruit);
                }

                LoadToRecycleView();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void LoadFromLocal(float[] classes)
    {
        Fruit fruits[] = FruitsInfo.fruits;
        for (float value: classes
             ) {
            for (Fruit fruit: fruits
                 ) {
                if (String.valueOf(Math.round(value)).equals(fruit.getId()))
                {
                    mFruits.add(fruit);
                    break;
                }
            }
        }
        LoadToRecycleView();
    }

    private void LoadToRecycleView()
    {
        mFruits = Array.removeDuplicates(mFruits);
        FruitsAdapter adapter = new FruitsAdapter(mFruits);
        rvFruits.setAdapter(adapter);

        rvFruits.setLayoutManager(new LinearLayoutManager(DetailFruitActivity.this));
    }
}