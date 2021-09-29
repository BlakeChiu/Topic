package com.example.topic.SingInUp;

import androidx.fragment.app.Fragment;

public interface LoginNavigationHost {

    void loginNavigateTo(Fragment fragment, boolean addToBackStack, String tag);

    void exit();
}
