package com.example.topic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.example.topic.RoomInfo.Publish;
import com.example.topic.RoomInfo.Search;
import com.example.topic.Sightseeing.Feature;

public class MainActivity extends AppCompatActivity {

    final public static String dataUrl = "http://192.168.1.9/android/";

    final public static String imageUrl = "http://192.168.1.9/upload-image/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AllPermission();

        //畫面切換
        CardView search = (CardView) findViewById(R.id.search);
        CardView publish = (CardView) findViewById(R.id.publish);
        CardView connection = (CardView) findViewById(R.id.connection);
        CardView feature = (CardView) findViewById(R.id.feature);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Search.class);
                startActivity(intent);
            }
        });
        publish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View t) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Publish.class);
                startActivity(intent);
            }
        });
        connection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Connection.class);
                startActivity(intent);
            }
        });
        feature.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Feature.class);
                startActivity(intent);
            }
        });
    }

    public void AllPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }
}