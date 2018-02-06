package com.example.dapp;


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
import Adapter.FruitAdapter;
import JavaBean.Food;
import JavaBean.Fruit;
import SearchDao.FoodDao;
import SearchDao.FruitDao;

public class FruitMainActivity extends AppCompatActivity {

    private ListView listView;
    private TextView searchEText;
    private FruitDao fruitDao;
    private Bundle bundle_from_FsTA;
    private String searchFood_name;
    private String userId;
    private List<Fruit> fruitSearchList = new ArrayList<>();
    private FruitAdapter fruitAdapter;
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
        setContentView(R.layout.fruit_main);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(255);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FruitMainActivity.this.finish();
            }
        });
        listView = findViewById(R.id.search_result);
        searchEText = findViewById(R.id.search_text);

        Intent intent = getIntent();
        bundle_from_FsTA = intent.getExtras();
        searchFood_name = bundle_from_FsTA.getString("searchFood_name");
        userId = bundle_from_FsTA.getString("from_Login_User_id");
        searchEText.setText(searchFood_name);


//        fruitDao = new FruitDao(this);
//        fruitSearchList = fruitDao.searchFruit(searchFood_name);
        foodDao = new FoodDao(this);
        foodSearchList = foodDao.findAllSeason(searchFood_name);
//        if (fruitSearchList.size() == 0) {
        if (foodSearchList.size() == 0) {
            Intent intent_null = new Intent(this, SearchNullResult.class);
            startActivity(intent_null);
            finish();
        } else {
            foodAdapter = new FoodAdapter(FruitMainActivity.this, R.layout.fruit_item, foodSearchList);
//            fruitAdapter = new FruitAdapter(FruitMainActivity.this, R.layout.fruit_item, fruitSearchList);
            listView.setAdapter(foodAdapter);
//      listView.setAdapter(fruitAdapter);
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (listView.getCount() > 0) {
//                    Fruit fruit = fruitSearchList.get(i);
                    Food food = foodSearchList.get(i);
                    Intent intent = new Intent(FruitMainActivity.this, FruitSelect.class);
                    Bundle bundle = new Bundle();
//                    bundle.putString("fruit_name", fruit.getRi_Food_name());
                    bundle.putString("fruit_name", food.getName());
                    bundle.putString("from_Login_User_id",userId);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

}
