package com.example.topic.Sightseeing;

import android.app.Activity;
import android.content.Intent;
import android.renderscript.ScriptGroup;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.topic.MainActivity;
import com.example.topic.R;
import com.example.topic.RoomInfo.Product;
import com.example.topic.RoomInfo.Product_Detail;
import com.example.topic.URL.Url;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ExampleViewHolder> {

    Url url = new Url();
    ArrayList<RecyclerViewPost> mData;
    RecyclerViewPost post;
    Activity mActivity;

    ExampleViewHolder holder;

    public RecyclerViewAdapter(ArrayList<RecyclerViewPost> data, Activity activity) {
        mData = data;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_cell, parent, false);
        holder = new ExampleViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ExampleViewHolder holder, int position) {
        final RecyclerViewPost post = mData.get(position);

        if(!Feature.showStatus && post.item.equals("風景")){
            holder.view.setVisibility(View.VISIBLE);
        }else if(Feature.showStatus && post.item.equals("名產")){
            holder.view.setVisibility(View.VISIBLE);
        } else {
            holder.view.setVisibility(View.GONE);
        }

        Glide.with(mActivity).load(url.urlGetImage + post.imageName).into(holder.img);
        holder.title.setText(post.title);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, FeatureDetail.class);

                intent.putExtra("image",post.getImageName());
                intent.putExtra("address",post.getAddress());
                intent.putExtra("title",post.getTitle());
                intent.putExtra("info",post.getInfo());

                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView title;
        View view;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView.findViewById(R.id.cellView_sightseeing);
            img = itemView.findViewById(R.id.cell_image_sightseeing);
            title = itemView.findViewById(R.id.cell_title);
        }
    }
}
