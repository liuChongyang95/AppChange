package com.example.dapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Adapter.UserfoodAdapter;
import JavaBean.UserFood;
import SearchDao.FoodRecordDao;

public class FoodRecordListView extends AppCompatActivity {
    private Bundle bundleFrom_FAF;
    private String userId;
    private List<UserFood> foodList = new ArrayList<>();
    private ListView listView;
    private UserfoodAdapter adapter;
    private FoodRecordDao foodRecordDao;

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
        setContentView(R.layout.foodrecordlistview);
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
                FoodRecordListView.this.finish();
            }
        });

        listView = findViewById(R.id.frc_listView);
        foodRecordDao = new FoodRecordDao(this);
        foodList = foodRecordDao.getFoodrecord(userId);
        if (foodList.size() != 0) {
            adapter = new UserfoodAdapter(this, R.layout.foodrecord_item, foodList);
            listView.setAdapter(adapter);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserFood userFood = foodList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("User_id", userId);
                bundle.putString("intake_date", userFood.getFoodDate());
                bundle.putString("intake_class", userFood.getFoodClass());
                bundle.putString("food_id", userFood.getFoodId());
                bundle.putString("food_name", userFood.getFoodName());
                bundle.putString("itemId", String.valueOf(userFood.get_id()));
                bundle.putString("itemSize", userFood.getFoodIntake());
                bundle.putString("itemEnergy", userFood.getFoodNutri());
                Log.d("FoodRecordListView", userFood.getFoodId());
                Intent intent2 = new Intent(FoodRecordListView.this, FoodRecordItem.class);
                intent2.putExtras(bundle);
                startActivity(intent2);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        foodList = foodRecordDao.getFoodrecord(userId);
        adapter = new UserfoodAdapter(this, R.layout.foodrecord_item, foodList);
        listView.setAdapter(adapter);
    }
}
