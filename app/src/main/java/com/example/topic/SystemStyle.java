package com.example.topic;

import android.app.Activity;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class SystemStyle {

    public void systemStyle(Activity activity){
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public void systemTextLight(Activity activity){
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }

    public int Width;
    public int Height;
    public float Density;

    public void ScreenInfo(int h , int w, float density ){
        this.Width = w;
        this.Height = h;
        this.Density = density;
    }

    public void getScreenSize(Activity activity)
    {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        int screenHeight = dm.heightPixels;
        int screenWidth = dm.widthPixels;
        float density = dm.density;

        pxToDp(screenHeight,screenWidth,density);


    }

    private void pxToDp(int h , int w, float density )
    {
        ScreenInfo((int)(h/density),
                (int)(w/density),
                density);
    }
}
