package com.GProject.DiabetesApp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import SearchDao.UserDao;
import SearchDao.UserIntakeDao;


public class FoodReport extends AppCompatActivity {
    private String userId;
    private UserDao userDao;
    private String userNickname;
    private String today;
    private SimpleDateFormat simpleDateFormat;
    private WebView webView;
    private final static String TAG = "FoodReport";

    @SuppressLint({"SimpleDateFormat", "SetJavaScriptEnabled"})
    @JavascriptInterface
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.food_report);
        userDao = new UserDao(this);
        Toolbar toolbar = findViewById(R.id.reportToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodReport.this.finish();
            }
        });
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            userId = bundle.getString("from_Login_User_id");
            userNickname = userDao.getNickname(userId);
        }
        webView = findViewById(R.id.foodReport);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setBuiltInZoomControls(true);
        webView.loadUrl("file:///android_asset/web/foodreport.html");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:getUserInfo('" + transUserInfo() + "')");
                webView.loadUrl("javascript:showNutrition('" + transNutritionReport() + "')");
            }
        });
        Log.d(TAG, transNutritionReport());

    }

    private float[] getNutritions(int decrease) {
        UserIntakeDao userIntakeDao = new UserIntakeDao(this);
        FoodSelected foodSelected = new FoodSelected();
        int len = 0;
//        加数
        float[] nutritions;
//        被加数
        float[] result = null;
        today = foodSelected.initDate();
        for (int i = 0; i < decrease; i++) {
            if (i == 0) {
//                当天的数值
                result = userIntakeDao.fromUserIntake(userId, today);
                len = result.length;
            } else {
//                昨天，前天数值
                nutritions = userIntakeDao.fromUserIntake(userId, today);
                for (int m = 0; m < len; m++) {
                    result[m] = nutritions[m] + result[m];
//                    Log.d("result", String.valueOf(result[m]));
                }
            }
//            Log.d("date", today);
            //            减一天
            today = decreaseTime(today);
        }
        return result;
    }

    //    日期减天数
    private String decreaseTime(String day) {
//        code391
        Date date = null;
        try {
            date = simpleDateFormat.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        减天数
        Date date_1 = null;
        if (date != null) {
            date_1 = new Date(date.getTime() - 24 * 60 * 60 * 1000);
        }
        return simpleDateFormat.format(date_1);
    }

    ////        包装用户ID和用户昵称
    private String transUserInfo() {
        String jsonUserInfo;
        Gson gson = new Gson();
        HashMap<String, String> userInfo = new HashMap<>();
        userInfo.put("UserId", userId);
        userInfo.put("UserNickName", userNickname);
        jsonUserInfo = gson.toJson(userInfo);
        return jsonUserInfo;
    }

    private String transNutritionReport() {
        Gson gson = new Gson();
        HashMap<String, Float> nutritionMap = new HashMap<>();
        String[] nutritionName = {"能量", "蛋白质", "脂肪", "膳食纤维", "碳水化物", "水分", "维生素A",
                "维生素B1", "维生素B2", "维生素B3", "维生素E", "维生素C", "铁", "钙", "钠", "胆固醇", "钾",
                "镁", "锌", "磷", "嘌呤"};
        for (int i = 0; i < nutritionName.length; i++) {
            nutritionMap.put(nutritionName[i], getNutritions(7)[i]);
        }
        return gson.toJson(nutritionMap);
    }
}
