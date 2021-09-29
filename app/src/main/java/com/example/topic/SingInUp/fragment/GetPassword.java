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

public class GetPassword extends Fragment implements View.OnClickListener {

    EditText accEdit, nameEdit;

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

        view.findViewById(R.id.get_return).setOnClickListener(this);
        view.findViewById(R.id.get_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_return:
                ((LoginNavigationHost) requireActivity()).loginNavigateTo(new SingIn(),true,"SignIn");
                break;

            case R.id.get_btn:

                if (checkInputInfo()){

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
}
