package com.example.topic.SingInUp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.topic.R;
import com.example.topic.SingInUp.LoginActivity;
import com.example.topic.SingInUp.LoginNavigationHost;
import com.example.topic.URL.Url;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class SingUp extends Fragment implements View.OnClickListener {

    Url url = new Url();
    EditText accEdit, nameEdit, pwdEdit, pwd2Edit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_singup, container, false);

        initLayout(view);

        return view;
    }

    private void initLayout(View view) {

        accEdit = view.findViewById(R.id.singUp_acc_edit);
        nameEdit = view.findViewById(R.id.singUp_name_edit);
        pwdEdit = view.findViewById(R.id.singUp_pwd_edit);
        pwd2Edit = view.findViewById(R.id.singUp_pwd2_edit);

        view.findViewById(R.id.singUp_btn).setOnClickListener(this);
        view.findViewById(R.id.singUp_return).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.singUp_btn:
                if (checkInputInfo()) {
                    SingUp();
                } else {
                    Toast.makeText(getActivity(), "註冊資料輸入不完全", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.singUp_return:
                ((LoginNavigationHost) requireActivity()).loginNavigateTo(new SingIn(),true,"SignIn");
                break;
        }
    }

    private boolean checkInputInfo() {
        boolean status = false;

        if (accEdit.getText().length() > 0 && nameEdit.getText().length() > 0 &&
                pwdEdit.getText().length() > 0 && pwd2Edit.getText().length() > 0) {
            if (pwdEdit.getText().toString().equals(pwd2Edit.getText().toString())) {
                status = true;
            }
        }

        return status;
    }

    private void SingUp() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name",nameEdit.getText().toString());
            jsonObject.put("phone", accEdit.getText().toString());
            jsonObject.put("password",pwdEdit.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Ion.with(this)
                .load(url.urlSingUp)
                .setBodyParameter("action", jsonObject.toString())
                .asJsonObject()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonObject> result) {

                        if (e != null) {

                        } else {
                            if (result.getHeaders().code() == 200) {
                                JsonParse(result.getResult().toString());
                            }
                        }
                    }
                });
    }

    private void JsonParse(String jsonObject) {

        try {

            JSONObject json = new JSONObject(jsonObject);

            switch (json.getString("result")){
                case "ok":
                    Toast.makeText(getActivity(), "註冊成功", Toast.LENGTH_SHORT).show();
                    clearAllEdit();
                    break;
                case "系統錯誤":
                    Toast.makeText(getActivity(), "系統錯誤", Toast.LENGTH_SHORT).show();
                    break;
                case "此電話已註冊":
                    Toast.makeText(getActivity(), "此電話已註冊", Toast.LENGTH_SHORT).show();
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void clearAllEdit(){
        nameEdit.setText("");
        accEdit.setText("");
        pwdEdit.setText("");
        pwd2Edit.setText("");
    }

}
