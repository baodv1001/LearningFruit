package com.example.FruitLearning.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.FruitLearning.DetailFruitActivity;
import com.example.FruitLearning.LoginActivity;
import com.example.FruitLearning.MainActivity;
import com.example.FruitLearning.R;
import com.example.FruitLearning.models.Fruit;

import java.util.List;

public class FruitsAdapter extends RecyclerView.Adapter<FruitsAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_fruit, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit = mFruits.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.name_tv;
        textView.setText(fruit.name);
    }

    @Override
    public int getItemCount() {
        return mFruits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name_tv;
        public ViewHolder(View item){
            super(item);

            name_tv = (TextView) item.findViewById(R.id.fruit_name);
        }
    }

    private List<Fruit> mFruits;
    public FruitsAdapter(List<Fruit> fruits)
    {
        mFruits = fruits;
    }
}
