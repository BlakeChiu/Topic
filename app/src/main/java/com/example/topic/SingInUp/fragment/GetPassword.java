package com.example.topic.SingInUp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.topic.R;
import com.example.topic.SingInUp.LoginActivity;
import com.example.topic.SingInUp.LoginNavigationHost;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class GetPassword extends Fragment implements View.OnClickListener {

    private final String getPwd_URL = LoginActivity.loginUrl + "member_forget.php";

    EditText accEdit, nameEdit;
    TextView showPwd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_get_pwd,container,false);

        initLayout(view);

        return view;
    }

    private void initLayout(View view){
        accEdit = view.findViewById(R.id.get_acc_edit);
        nameEdit = view.findViewById(R.id.get_name_edit);
        showPwd = view.findViewById(R.id.get_showPwd);

        view.findViewById(R.id.get_return).setOnClickListener(this);
        view.findViewById(R.id.get_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_return:
                clearAllEdit();
                ((LoginNavigationHost) requireActivity()).loginNavigateTo(new SingIn(),true,"SignIn");
                break;

            case R.id.get_btn:

                if (checkInputInfo()){
                    GetPwd();
                }else{
                    Toast.makeText(getActivity(), "輸入資料不完全", Toast.LENGTH_SHORT).show();
                }
                break;

            default:break;
        }
    }

    private boolean checkInputInfo(){
        boolean status = false;

        if(accEdit.getText().length()>0 && nameEdit.getText().length()>0){
            status = true;
        }

        return status;
    }

    private void GetPwd() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", nameEdit.getText().toString());
            jsonObject.put("phone",accEdit.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Ion.with(this)
                .load(getPwd_URL)
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

            if(json.has("result")){
                clearAllEdit();
                Toast.makeText(getActivity(), ""+json.getString("result"), Toast.LENGTH_SHORT).show();
            }else{
                showPwd.setText("您的密碼為："+json.getString("password"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void clearAllEdit(){
        accEdit.setText("");
        nameEdit.setText("");
        showPwd.setText("");
    }
}
