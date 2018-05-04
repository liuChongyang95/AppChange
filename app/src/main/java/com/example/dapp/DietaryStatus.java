package com.example.dapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.sql.Array;
import java.util.Arrays;

import SearchDao.FoodRecordDao;

public class DietaryStatus extends AppCompatActivity {

    final int version = Build.VERSION.SDK_INT;
    private String userId;
    private String nowDay;
    private FoodSelected foodSelected;
    private FoodRecordDao foodRecordDao;
    private String dataJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.dietary_doughnut);
        Toolbar toolbar = findViewById(R.id.dietaryToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DietaryStatus.this.finish();
            }
        });
        WebView webView = findViewById(R.id.dietrayDoughnut);
        ShowWebView(webView);
        Intent intent=getIntent();
        Bundle bundleFromFAF=intent.getExtras();
        if (bundleFromFAF != null) {
//          用户id
            userId=bundleFromFAF.getString("from_Login_User_id");
        }
//        调用存在的方法，获取当前日期。
        foodSelected=new FoodSelected();
//        当前日期
        nowDay=foodSelected.initDate();
        foodRecordDao=new FoodRecordDao(DietaryStatus.this);
        dataJson=foodRecordDao.dayRecord(userId,nowDay);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @JavascriptInterface
    public void ShowWebView(final WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadUrl("file:///android_asset/web/Doughnut.html");
        if (version < 19) {
            webView.loadUrl("javascript:changeArr('"+ dataJson +"')");
        } else {
            webView.evaluateJavascript("javascript:changeArr('"+ dataJson +"')", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                }
            });
        }
    }
}
