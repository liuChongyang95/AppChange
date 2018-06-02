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

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import SearchDao.UserIntakeDao;


public class FoodReport extends AppCompatActivity {
    private String userId;
    private String today;
    private SimpleDateFormat simpleDateFormat;
    private final static String TAG = "FoodReport";

    @SuppressLint("SimpleDateFormat")
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
        }
        getNutritions(3);
    }

    public void getNutritions(int decrease) {
        UserIntakeDao userIntakeDao = new UserIntakeDao(this);
        FoodSelected foodSelected = new FoodSelected();
        int len=0;
//        加数
        float[] nutritions;
//        被加数
        float[] result=null;
        Gson gson = new Gson();
        today = foodSelected.initDate();
        for (int i = 0; i < decrease; i++) {
            if (i == 0) {
//                当天的数值
                result =userIntakeDao.fromUserIntake(userId,today);
                len = result.length;
            } else {
//                昨天，前天数值
                nutritions = userIntakeDao.fromUserIntake(userId, today);
                for (int m = 0; m < len; m++) {
                    result[m] =nutritions[m] +result[m];
                    Log.d("result", String.valueOf(result[m]));
                }
            }
            Log.d("date", today);
            //            减一天
            today = decreaseTime(today);
        }

    }

    //    日期减天数
    public String decreaseTime(String day) {
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

}
