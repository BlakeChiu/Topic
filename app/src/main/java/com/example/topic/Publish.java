package com.example.topic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class Publish extends AppCompatActivity {

    private final int IMG_REQUEST = 1;
    private final String imgURL = "http://192.168.1.9/upload-image/picture_move.php";
    private final String dataURL = "http://192.168.1.9/android/json_data_upload.php";

    private String[] spinnerArr =new String[]{"雅房","獨立套房","分租套房"};
    private ArrayAdapter<String> arrayAdapter;

    EditText name_edit,phone_edit,address_edit,price_edit;
    ImageView imageView;
    Spinner spinner;

    String houseType;

    Uri uri;

    private void init(){

        name_edit = findViewById(R.id.nameEdit);
        phone_edit = findViewById(R.id.phoneEdit);
        address_edit = findViewById(R.id.addressEdit);
        imageView = findViewById(R.id.imageView);
        price_edit = findViewById(R.id.priceEdit);
        spinner = findViewById(R.id.spinner);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,spinnerArr);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(mListener);
        spinner.setSelection(0);
    }

    private AdapterView.OnItemSelectedListener mListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            houseType = spinnerArr[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void onClick(){

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        findViewById(R.id.uploadBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uri != null && name_edit.getText() != null && phone_edit.getText() != null && address_edit.getText() != null &&
                    price_edit.getText() != null){
                    try {
                        UploadData();
                        UploadImg();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        init();
        onClick();
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            imageView.setBackgroundResource(0);
            imageView.setImageURI(uri);
        }
    }

    private void UploadData() throws JSONException {

        String[] beforeURI = getRealPathFromUri(this,uri).split("/");
        String afterURI = beforeURI[beforeURI.length-1];

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("address", address_edit.getText().toString());
        jsonObject.put("name", name_edit.getText().toString());
        jsonObject.put("phone", phone_edit.getText().toString());
        jsonObject.put("housetype", houseType);
        jsonObject.put("price", price_edit.getText().toString());
        jsonObject.put("image", afterURI);

        Ion.with(this)
                .load(dataURL)
                .setBodyParameter("action", jsonObject.toString())
                .asJsonObject()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonObject> result) {

                        if (e != null) {

                        } else {
                            if (result.getHeaders().code() == 200) {
                                Toast.makeText(Publish.this, "上傳資料成功", Toast.LENGTH_SHORT).show();
                                name_edit.setText("");
                                address_edit.setText("");
                                phone_edit.setText("");
                                price_edit.setText("");
                                houseType = "";
                                spinner.setSelection(0);
                            } else {
                                Toast.makeText(Publish.this, "上傳資料失敗", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }

    private void UploadImg(){

        ArrayList<Part> fileParts = new ArrayList<>();
        File fileaddress = new File(getRealPathFromUri(this, uri));
        fileParts.add(new FilePart("myupload", fileaddress));
        Ion.with(this)
                .load(imgURL)
                .addMultipartParts(fileParts)
                .asJsonObject()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonObject> result) {
                        if(e != null){
                            Toast.makeText(Publish.this, "上傳照片失敗", Toast.LENGTH_SHORT).show();
                        }else{
                            if(result.getHeaders().code() == 200){
                                Toast.makeText(Publish.this, "上傳照片成功", Toast.LENGTH_SHORT).show();
                                imageView.setImageURI(null);
                                imageView.setBackgroundResource(R.mipmap.upload_img);
                                uri = null;
                            }else{
                                Toast.makeText(Publish.this, "伺服器異常", Toast.LENGTH_SHORT).show();
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