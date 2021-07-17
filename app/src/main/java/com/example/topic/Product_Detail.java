package com.example.topic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.body.FilePart;
import com.koushikdutta.async.http.body.Part;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Product_Detail extends AppCompatActivity {

    private ConstraintLayout review_view, modify_view;
    private ImageView mImage;
    private TextView mAddress, mName, mPhone, mHouseType, mPrice;
    private EditText mAddress_edit, mName_edit, mPhone_edit, mPrice_edit;
    private Spinner mHouseType_spinner;
    private TextView type_text;
    private Button connect_btn, delete_btn;

    ArrayAdapter<String> roomAdapter;
    final String[] roomArr = new String[]{"雅房", "獨立套房", "分租套房"};

    private final String delete_URL = "http://192.168.1.9/Android/json_data_delete.php";
    private final String img_URL = "http://192.168.1.9/upload-image/picture_move.php";
    private final String update_URL = "http://192.168.1.9/Android/json_data_update.php";

    final int IMG_REQUEST = 100;
    Uri uri;
    String houseType = "";

    // 初始化元件
    private void init() {

        mImage = findViewById(R.id.prodouct_image);
        type_text = findViewById(R.id.text_btn);

        review_view = findViewById(R.id.review_view);
        mAddress = findViewById(R.id.product_address);
        mName = findViewById(R.id.product_name);
        mPhone = findViewById(R.id.product_phone);
        mHouseType = findViewById(R.id.product_houseType);
        mPrice = findViewById(R.id.product_price);

        modify_view = findViewById(R.id.modify_view);
        mAddress_edit = findViewById(R.id.product_address_edit);
        mName_edit = findViewById(R.id.product_name_edit);
        mPhone_edit = findViewById(R.id.product_phone_edit);
        mPrice_edit = findViewById(R.id.product_price_edit);
        mHouseType_spinner = findViewById(R.id.spinner_edit);

        connect_btn = findViewById(R.id.detail_btn);
        delete_btn = findViewById(R.id.delete_btn);

        roomAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, roomArr);
        mHouseType_spinner.setAdapter(roomAdapter);
        mHouseType_spinner.setOnItemSelectedListener(roomListener);
    }

    // 呼叫手機相簿
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, 100);
    }

    // 選擇照片回傳程式
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            mImage.setBackgroundResource(0);
            Glide.with(Product_Detail.this).load(getRealPathFromUri(this, uri)).into(mImage);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        //捕捉傳入的東西
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String address = intent.getStringExtra("address");
        String name = intent.getStringExtra("name");
        String image = intent.getStringExtra("image");
        String phone = intent.getStringExtra("phone");
        houseType = intent.getStringExtra("houseType");
        String price = intent.getStringExtra("price");

        init();

        Log.i("TAGGGGGG", " image = " + image);
        Log.i("TAGGGGGG", " image2 = " + image.equals(""));
        Log.i("TAGGGGGG", " image3 = " + image.isEmpty());

        if (intent != null) {
            mAddress.setText(address);
            mName.setText(name);
            mPhone.setText(phone);
            mHouseType.setText(houseType);
            mPrice.setText(price);

            mAddress_edit.setText(address);
            mName_edit.setText(name);
            mPhone_edit.setText(phone);
            mPrice_edit.setText(price);
            switch (houseType) {
                case "雅房":
                    mHouseType_spinner.setSelection(0);
                    break;
                case "獨立套房":
                    mHouseType_spinner.setSelection(1);
                    break;
                case "分租套房":
                    mHouseType_spinner.setSelection(2);
                    break;
            }

            Glide.with(Product_Detail.this).load("http://192.168.1.9/upload-image/uploads/" + image).into(mImage);

        }

        // 撥打電話-按鍵監聽
        connect_btn.setOnClickListener(v -> {
            if (type_text.getText().equals("修改")) {
                Toast.makeText(this, "請回預覽模式在撥打電話", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone)));
            }
        });

        // 刪除資料-按鍵監聽
        delete_btn.setOnClickListener(v -> {
            delete(id);
        });

        // 預覽/修改-按鍵監聽
        type_text.setOnClickListener(v -> {
            if (type_text.getText().equals("預覽")) {

                type_text.setText("修改");
                type_text.setTextColor(0xFFFF0000);
                review_view.setVisibility(View.INVISIBLE);
                modify_view.setVisibility(View.VISIBLE);
                delete_btn.setVisibility(View.VISIBLE);

            } else if (type_text.getText().equals("修改")) {
                try {
                    Log.i("TAGGGGG", " image = " + mImage.getBackground());
                    Update(id);
                    if (uri != null) {
                        UploadImg();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                type_text.setText("預覽");
                type_text.setTextColor(0xFF8E8E8E);
                review_view.setVisibility(View.VISIBLE);
                modify_view.setVisibility(View.INVISIBLE);
                delete_btn.setVisibility(View.INVISIBLE);
            }
        });

        // 圖片-按鍵監聽
        mImage.setOnClickListener(v -> {
            if (type_text.getText().equals("修改")) {
                selectImage();
            }
        });

    }

    private AdapterView.OnItemSelectedListener roomListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            houseType = roomArr[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void delete(String id) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Ion.with(this)
                .load(delete_URL)
                .setBodyParameter("action", jsonObject.toString())
                .asJsonArray()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonArray>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonArray> result) {

                        if (e != null) {

                        } else {
                            if (result.getHeaders().code() == 200) {
                                finish();
                            } else {
//                                Toast.makeText(Search.this, "上傳資料失敗", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void Update(String id) throws JSONException {

        String afterURI = "";

        //判斷照片uri是否為空
        if (uri != null) {
            String[] beforeURI = getRealPathFromUri(this, uri).split("/");
            afterURI = beforeURI[beforeURI.length - 1];
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("address", mAddress_edit.getText().toString());
        jsonObject.put("name", mName_edit.getText().toString());
        jsonObject.put("phone", mPhone_edit.getText().toString());
        jsonObject.put("housetype", houseType);
        jsonObject.put("price", mPrice_edit.getText().toString());
        jsonObject.put("image", afterURI);
        jsonObject.put("id", id);

        Ion.with(this)
                .load(update_URL)
                .setBodyParameter("action", jsonObject.toString())
                .asJsonObject()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonObject> result) {

                        if (e != null) {

                        } else {
                            if (result.getHeaders().code() == 200) {
                                Toast.makeText(Product_Detail.this, "上傳資料成功", Toast.LENGTH_SHORT).show();
                                mAddress.setText(mAddress_edit.getText().toString());
                                mName.setText(mName_edit.getText().toString());
                                mPhone.setText(mPhone_edit.getText().toString());
                                mPrice.setText(mPrice_edit.getText().toString());
                                mHouseType.setText(houseType);

                            } else {
                                Toast.makeText(Product_Detail.this, "上傳資料失敗", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }

    private void UploadImg() {

        ArrayList<Part> fileParts = new ArrayList<>();
        File fileaddress = new File(getRealPathFromUri(this, uri));
        fileParts.add(new FilePart("myupload", fileaddress));
        Ion.with(this)
                .load(img_URL)
                .addMultipartParts(fileParts)
                .asJsonObject()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonObject> result) {
                        if (e != null) {
                            Toast.makeText(Product_Detail.this, "上傳照片失敗", Toast.LENGTH_SHORT).show();
                        } else {
                            if (result.getHeaders().code() == 200) {
                                Toast.makeText(Product_Detail.this, "上傳照片成功", Toast.LENGTH_SHORT).show();
                                mImage.setImageURI(uri);
                                uri = null;
                            } else {
                                Toast.makeText(Product_Detail.this, "伺服器異常", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    public String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


}