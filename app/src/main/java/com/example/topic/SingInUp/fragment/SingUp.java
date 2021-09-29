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
import com.example.topic.SingInUp.LoginNavigationHost;
import com.example.topic.SystemStyle;

import java.util.Objects;

public class SingUp extends Fragment implements View.OnClickListener {

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
//                ((LoginNavigationHost) getActivity()).loginNavigateTo(new SingIn(),true,"SignIn");
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


}
