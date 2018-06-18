package com.GProject.DiabetesApp;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import Adapter.FoodAdapter;
import JavaBean.Food;
import SearchDao.FoodDao;
import Util.SlideLayout;


public class FoodMainActivity extends AppCompatActivity {

    private ListView listView;
    private TextView searchEText;
    private Bundle bundle_from_FsTA;
    private String searchFood_name;
    private String userId;
    private List<Food> foodSearchList = new ArrayList<>();
    private FoodDao foodDao;
    private FoodAdapter foodAdapter;

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
        setContentView(R.layout.food_main);
        new SlideLayout(this).bind();
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(255);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodMainActivity.this.finish();
            }
        });
        listView = findViewById(R.id.search_result);
        searchEText = findViewById(R.id.search_text);
        Intent intent = getIntent();
        bundle_from_FsTA = intent.getExtras();
        searchFood_name = bundle_from_FsTA.getString("searchFood_name");
        userId = bundle_from_FsTA.getString("from_Login_User_id");
        searchEText.setText(searchFood_name);
        foodDao = new FoodDao(this);
        foodSearchList = foodDao.findAllSeason(searchFood_name);
        if (foodSearchList.size() == 0) {
            Intent intent_null = new Intent(this, FoodSearchNull.class);
            startActivity(intent_null);
            finish();
        } else {
            foodAdapter = new FoodAdapter(FoodMainActivity.this, R.layout.food_item, foodSearchList);
            listView.setAdapter(foodAdapter);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Food food = foodSearchList.get(i);
                if (listView.getCount() > 0) {
                    if (bundle_from_FsTA.getString("activity") != null && bundle_from_FsTA.getString("activity").length() > 0) {
                        Intent intent=new Intent(FoodMainActivity.this,FoodSelected2.class);
                        bundle_from_FsTA.putString("fruit_name", food.getName());
                        bundle_from_FsTA.putString("fruit_id", food.getId());
                        intent.putExtras(bundle_from_FsTA);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(FoodMainActivity.this, FoodSelected.class);
                        bundle_from_FsTA.putString("fruit_name", food.getName());
                        bundle_from_FsTA.putString("fruit_id", food.getId());
                        intent.putExtras(bundle_from_FsTA);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}