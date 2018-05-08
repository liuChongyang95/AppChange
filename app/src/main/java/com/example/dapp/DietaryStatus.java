package com.example.dapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;

import JavaBean.Dietary;
import SearchDao.FoodDao;
import SearchDao.FoodRecordDao;


/*
 * String StringBuilder StringBuffer用不好，需要练习*/

public class DietaryStatus extends AppCompatActivity {

    final int version = Build.VERSION.SDK_INT;
    private String userId;
    private String nowDay;
    private FoodSelected foodSelected;
    private FoodRecordDao foodRecordDao;
    private String dataJson2JS;
    private static final String TAG = "DietaryStatus";
    private String foodName;
    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
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
        Intent intent = getIntent();
        Bundle bundleFromFAF = intent.getExtras();
        if (bundleFromFAF != null) {
//          用户id
            userId = bundleFromFAF.getString("from_Login_User_id");
        }
//        调用存在的方法，获取当前日期。
        foodSelected = new FoodSelected();
//        当前日期
        nowDay = foodSelected.initDate();
        foodRecordDao = new FoodRecordDao(DietaryStatus.this);
        String dataJson = foodRecordDao.dayRecord(userId, nowDay);
        Gson gson = new Gson();
        List<Dietary> dietaryList = gson.fromJson(dataJson, new TypeToken<List<Dietary>>() {
        }.getType());
        FoodDao foodDao = new FoodDao(this);
//        关于String和StringBuilder返回值的问题，会有资源开销
        int dLength = dietaryList.size();
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        int initItem = 1;
        for (int a = 0; a < dLength; a++) {
            Dietary dietaryA = dietaryList.get(a);
            if (dietaryA.getFoodId() != null) {
                foodName = foodDao.find_Name(dietaryA.getFoodId());
                if (a == dLength - 1) {
//                    foodName值被改变
                    stringIntegerHashMap.put(foodName, initItem);
                    initItem = 1;
                } else {
                    for (int b = a + 1; b < dLength; b++) {
                        Dietary dietaryB = dietaryList.get(b);
                        if (dietaryA.getFoodId().equals(dietaryB.getFoodId())) {
                            initItem = initItem + 1;
                            dietaryB.setFoodId(null);
                        }
                    }
                    stringIntegerHashMap.put(foodName, initItem);
                    initItem = 1;
                }
            }
        }
        dataJson2JS = gson.toJson(stringIntegerHashMap);
        webView = findViewById(R.id.dietrayDoughnut);
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webView.loadUrl("file:///android_asset/web/Doughnut.html");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript: endEXE('"+dataJson2JS+"')");
            }
        });
    }
}
