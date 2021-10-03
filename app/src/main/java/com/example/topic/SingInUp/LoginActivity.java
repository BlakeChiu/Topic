package com.example.topic.SingInUp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.topic.MainActivity;
import com.example.topic.R;
import com.example.topic.SingInUp.fragment.SingIn;
import com.example.topic.SystemStyle;

public class LoginActivity extends AppCompatActivity implements LoginNavigationHost{

    SystemStyle systemStyle = new SystemStyle();
    private InputMethodManager inputMethodManager;

    public static String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        systemStyle.systemStyle(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.login_fragment, new SingIn(), "SingIn")
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void loginNavigateTo(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.login_fragment, fragment, tag);

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.commitAllowingStateLoss();
    }

    @Override
    public void exit() {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void onClick(View v) {
        if (null == inputMethodManager) {
            inputMethodManager = (InputMethodManager)
                    this.getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}