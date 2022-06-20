package com.example.FruitLearning.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.FruitLearning.R;
import com.example.FruitLearning.ViewFruitDetailActivity;
import com.example.FruitLearning.models.Fruit;

import java.io.IOException;
import java.io.InputStream;
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
        TextView name_tv = holder.name_tv;
//        TextView info_tv = holder.info_tv;
        ImageView imageView = holder.imageView;
        name_tv.setText(fruit.name);
//        info_tv.setText(fruit.description);

        try {
            // get input stream

            InputStream ims = context.getAssets().open(fruit.image);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mFruits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name_tv;
        public TextView info_tv;
        public ImageView imageView;

        public ViewHolder(View item){
            super(item);

            name_tv = (TextView) item.findViewById(R.id.fruit_name);
//            info_tv = (TextView) item.findViewById(R.id.tv_infoFruit);
            imageView = (ImageView) item.findViewById(R.id.imageview_fruit);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            Fruit temp = mFruits.get(position);

            Intent intent = new Intent(context, ViewFruitDetailActivity.class);
            intent.putExtra("fruit", temp.getId());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    private List<Fruit> mFruits;
    private Context context;
    public FruitsAdapter(Context context, List<Fruit> fruits)
    {
        this.context = context;
        mFruits = fruits;
    }
}
