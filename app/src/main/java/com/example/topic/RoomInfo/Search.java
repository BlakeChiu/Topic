package com.example.topic.RoomInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.topic.MainActivity;
import com.example.topic.R;
import com.example.topic.RoomInfo.RecylerAdapter;
import com.example.topic.URL.Url;
import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.koushikdutta.ion.builder.Builders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;


public class Search extends AppCompatActivity {

    Url url = new Url();

    private RecyclerView recyclerView;
    public RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter mAdapter;
    private List<Product> products;

    Spinner spinner_blog, spinner_room;
    Button search_btn;
    EditText name_edit;

    final String[] spinnerBlogArr = new String[]{"房型", "姓名","全部"};
    final String[] spinnerRoomArr = new String[]{"雅房", "獨立套房", "分租套房"};
    ArrayAdapter<String> blogAdapter;
    ArrayAdapter<String> roomAdapter;
    String searchStr = "";
    String selectType = "0";

    private void init() {

        spinner_blog = findViewById(R.id.spinner_blog);
        spinner_room = findViewById(R.id.spinner_room);
        search_btn = findViewById(R.id.search_btn);
        name_edit = findViewById(R.id.edit_name);

        recyclerView = findViewById(R.id.products_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        manager = new GridLayoutManager(Search.this, 3);

        // 下拉式選單 - 選擇查詢模式：房型、姓名、全部
        blogAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, spinnerBlogArr);
        spinner_blog.setAdapter(blogAdapter);
        spinner_blog.setOnItemSelectedListener(blogListener);
        spinner_blog.setSelection(2);

        // 下拉式選單 - 選擇房間模式
        roomAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, spinnerRoomArr);
        spinner_room.setAdapter(roomAdapter);
        spinner_room.setSelection(0);
        spinner_room.setOnItemSelectedListener(roomListener);


        products = new ArrayList<>();

        search_btn.setOnClickListener(v -> {

            if (selectType.equals("1")) {
                searchStr = name_edit.getText().toString();
            }

            products.clear();
            mAdapter.notifyDataSetChanged();
            getProducts(selectType);
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();

    }

    @Override
    protected void onStart() {
        super.onStart();

        products.clear();
        getProducts(selectType);
    }

    private AdapterView.OnItemSelectedListener blogListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (spinnerBlogArr[position].equals("房型")) {

                spinner_room.setVisibility(View.VISIBLE);
                name_edit.setVisibility(View.INVISIBLE);
                name_edit.setText("");
                searchStr = spinnerRoomArr[0];
                selectType = "2";

            } else if (spinnerBlogArr[position].equals("姓名")) {

                spinner_room.setVisibility(View.INVISIBLE);
                name_edit.setVisibility(View.VISIBLE);
                name_edit.setText("");
                searchStr = "";
                selectType = "1";

            }else if(spinnerBlogArr[position].equals("全部")){
                spinner_room.setVisibility(View.INVISIBLE);
                name_edit.setVisibility(View.INVISIBLE);
                name_edit.setText("");
                searchStr = "";
                selectType = "0";
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener roomListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.i("TAGGGGG","roomListener onItemSelected = " + position);
            searchStr = spinnerRoomArr[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Log.i("TAGGGGG","onNothingSelected");
        }
    };


    /**
     * select_type = 0 -> 搜尋全部
     * select_type = 1 -> 搜尋姓名
     * select_type = 2 -> 搜尋房型
     **/
    private void getProducts(String select_type) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("select_type", select_type);

            switch (select_type) {
                case "1":
                    jsonObject.put("name", searchStr);
                    break;
                case "2":
                    jsonObject.put("housetype", searchStr);
                    break;
                default:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("TAGGGGG", "housetype = " + searchStr);

        Ion.with(this)
                .load(url.urlDataSelect)
                .setBodyParameter("action", jsonObject.toString())
                .asJsonArray()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonArray>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonArray> result) {

                        if (e != null) {

                        } else {
                            if (result.getHeaders().code() == 200) {
                                name_edit.setText("");
                                if (result.getResult() != null) {
                                    Log.i("TAGGGG",result.getResult().toString());
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

                String id = arr.getJSONObject(i).getString("id");
                String address = arr.getJSONObject(i).getString("address");
                String name = arr.getJSONObject(i).getString("name");
                String image = arr.getJSONObject(i).getString("image");
                String housetype = arr.getJSONObject(i).getString("housetype");
                String price = arr.getJSONObject(i).getString("price");
                String phone = arr.getJSONObject(i).getString("phone");
                String status = arr.getJSONObject(i).getString("status");

                Product product = new Product(id, address, name, image, housetype, price, phone , status);
                products.add(product);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mAdapter = new RecylerAdapter(Search.this, products);
        recyclerView.setAdapter(mAdapter);

    }
}