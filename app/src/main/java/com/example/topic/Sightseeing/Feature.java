package com.example.topic.Sightseeing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.topic.MainActivity;
import com.example.topic.R;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Feature extends AppCompatActivity {

    final String sightseeingUrl = MainActivity.dataUrl+"json_data_select_sightseeing.php";

    // 風景＝false   名產＝true
    public static Boolean showStatus = false;
    RecyclerViewAdapter mAdapter;
    RecyclerView recyclerView;
    EditText searchEdit;
    ArrayList<RecyclerViewPost> data;

    private  void initLayout(){

        recyclerView = findViewById(R.id.futureRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getText().equals("風景")){
                    showStatus = false;
                }else{
                    showStatus = true;
                }
                clearRecyclerView();
                getSightseeing();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // 未被選擇時觸發
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // 目標的tab 與當前的tab 一樣時
            }
        });

        data = new ArrayList<>();

        searchEdit = findViewById(R.id.device_search_edit);
        ImageView searchBtn = findViewById(R.id.device_search_img);

        searchBtn.setOnClickListener(v -> {
            clearRecyclerView();
            getSightseeing();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature);

        initLayout();

    }

    @Override
    protected void onStart() {
        super.onStart();

        data.clear();
        getSightseeing();
    }

    private void clearRecyclerView(){
        data.clear();
        mAdapter.notifyDataSetChanged();
    }


    private void getSightseeing() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("address", searchEdit.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Ion.with(this)
                .load(sightseeingUrl)
                .setBodyParameter("action", jsonObject.toString())
                .asJsonArray()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonArray>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonArray> result) {

                        if (e != null) {
                            Log.i("TAGGGGG","e = " + e.toString());
                        } else {
                            if (result.getHeaders().code() == 200) {
                                if (result.getResult() != null) {
                                    JsonParse(result.getResult().toString());
                                }
                            }
                        }
                    }
                });
    }

    private void JsonParse(String jsonObject) {

        try {
            JSONArray arr = new JSONArray(jsonObject);

            for (int i = 0; i < arr.length(); i++) {

                String item = arr.getJSONObject(i).getString("item");
                String title = arr.getJSONObject(i).getString("title");
                String image = arr.getJSONObject(i).getString("image");
                String address = arr.getJSONObject(i).getString("address");
                String introduce = arr.getJSONObject(i).getString("introduce");

                RecyclerViewPost product = new RecyclerViewPost(image, title, address, introduce, item);
                data.add(product);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mAdapter = new RecyclerViewAdapter(data, this);
        recyclerView.setAdapter(mAdapter);

    }
}