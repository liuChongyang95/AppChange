package com.example.dapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Adapter.UserfoodAdapter;
import JavaBean.UserFood;
import SearchDao.FoodRecordDao;

public class FoodRecordChange extends AppCompatActivity {
    private Bundle bundleFrom_FAF;
    private String userId;
    private List<UserFood> foodList = new ArrayList<>();
    private ListView listView;
    private UserfoodAdapter adapter;

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
        setContentView(R.layout.foodrecord_change);
        Intent intent = getIntent();
        bundleFrom_FAF = intent.getExtras();
        if (bundleFrom_FAF != null) {
            userId = bundleFrom_FAF.getString("from_Login_User_id");
        }
        Toolbar toolbar = findViewById(R.id.frc_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodRecordChange.this.finish();
            }
        });

        listView = findViewById(R.id.frc_listView);
        FoodRecordDao foodRecordDao = new FoodRecordDao(this);
        foodList = foodRecordDao.getFoodrecord(userId);
        if (foodList.size() != 0) {
            adapter = new UserfoodAdapter(this, R.layout.foodrecord_item, foodList);
            listView.setAdapter(adapter);
        } else {
            Intent intent1 = new Intent(this, FoodSearchNull.class);
            startActivity(intent1);
        }

    }
}
