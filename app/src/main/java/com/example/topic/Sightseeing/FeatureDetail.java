package com.example.topic.Sightseeing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.topic.MainActivity;
import com.example.topic.R;

public class FeatureDetail extends AppCompatActivity {

    TextView titleLabel,addressLabel,infoLabel;
    ImageView image;

    private void init() {

        titleLabel = findViewById(R.id.detail_title);
        addressLabel = findViewById(R.id.detail_address);
        infoLabel = findViewById(R.id.detail_info);
        image = findViewById(R.id.detail_image);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature_detail);

        init();

        Intent intent = getIntent();

        addressLabel.setText(intent.getStringExtra("address"));
        titleLabel.setText(intent.getStringExtra("title"));
        infoLabel.setText(intent.getStringExtra("info"));

        Glide.with(FeatureDetail.this).load(MainActivity.imageUrl+"uploads/" + intent.getStringExtra("image")).into(image);

    }
}