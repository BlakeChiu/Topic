package com.example.topic.SingInUp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.topic.R;
import com.example.topic.SingInUp.LoginNavigationHost;
import com.example.topic.SystemStyle;

public class SingIn extends Fragment implements View.OnClickListener {

    SystemStyle systemStyle = new SystemStyle();

    EditText accountEdit,pwdEdit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.frag_singin,container,false);

        systemStyle.getScreenSize(requireActivity());
        initLayout(view);

        return view;
    }

    private void initLayout(View view){

        ImageView logo = view.findViewById(R.id.logo);
        logo.getLayoutParams().width = ((int) ((int) (systemStyle.Width * systemStyle.Density) * 0.48));
        logo.getLayoutParams().height = ((int) ((int) (systemStyle.Width * systemStyle.Density) * 0.44));

        accountEdit = view.findViewById(R.id.account_edit);
        pwdEdit = view.findViewById(R.id.pwd_edit);

        view.findViewById(R.id.gerPwd_text).setOnClickListener(this);
        view.findViewById(R.id.singIn_btn).setOnClickListener(this);
        view.findViewById(R.id.singUp_btn).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.singIn_btn:
                if(checkLoginInfo()){
                    ((LoginNavigationHost) requireActivity()).exit();
                }else{
                    Toast.makeText(getContext(), "輸入不完全", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.singUp_btn:
                ((LoginNavigationHost) requireActivity()).loginNavigateTo(new SingUp(),true,"SingUp");
                break;

            case R.id.gerPwd_text:
                ((LoginNavigationHost) requireActivity()).loginNavigateTo(new GetPassword(),true,"GetPassword");
                break;

            default:break;
        }
    }

    private boolean checkLoginInfo(){
        boolean status = false;

        if(accountEdit.getText().length() > 0 && pwdEdit.getText().length() > 0 ){
            status = true;
        }

        return status;
    }
}
