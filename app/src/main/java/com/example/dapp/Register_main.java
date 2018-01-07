package com.example.dapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import Util.Fastblur;

/**
 * Created by Administrator on 2018/1/6.
 */

public class Register_main extends AppCompatActivity {
    private EditText username_register;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.register_app);
        Toolbar toolbar = findViewById(R.id.register_toolBar);
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register_main.this.finish();
            }
        });
        //模糊图片
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.warmbg);
//        Bitmap bitmap = bitmapDrawable.getBitmap();
//        RelativeLayout relativeLayout = findViewById(R.id.register_relative);
//        Drawable register_bg = setBlurBackground(bitmap);
//        relativeLayout.setBackground(register_bg);
    }

//    private Drawable setBlurBackground(Bitmap bmp) {
//        final Bitmap blurBmp = Fastblur.fastblur(Register_main.this, bmp, 20);//0-25，表示模糊值
//        final Drawable drawable = Register_main.getDrawable(Register_main.this, blurBmp);//将bitmap类型图片 转为 Drawable类型
//        return drawable;
//    }

//    //bitmap 转 drawable
//    public static Drawable getDrawable(Context context, Bitmap bm) {
//        BitmapDrawable bd = new BitmapDrawable(context.getResources(), bm);
//        return bd;
//    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.register_sure:
                break;
        }
        return true;
    }
}