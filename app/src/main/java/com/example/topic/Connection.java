package com.example.topic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Connection extends AppCompatActivity {

    private void initLayout(){
        TextView phone = findViewById(R.id.phone);
        TextView email = findViewById(R.id.email);
        TextView address = findViewById(R.id.address);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        initLayout();
    }
}