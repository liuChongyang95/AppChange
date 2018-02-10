package com.example.dapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import Adapter.FruitAdapter;
import JavaBean.Fruit;
import SearchDao.FruitDao;

/**
 * Created by Administrator on 2017/12/30.
 */

public class FoodSearch extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.food_search);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ListView searchListView = findViewById(R.id.fruit_search);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String searchFruitText = bundle.getString("searchText");
        FruitDao fruitDao = new FruitDao(FoodSearch.this);
        List<Fruit> fruitSearchList = fruitDao.searchFruit(searchFruitText);
        if (fruitSearchList.size() == 0) {
            Intent intent_null = new Intent(this, FoodSearchNull.class);
            startActivity(intent_null);
            finish();
        } else {
            FruitAdapter fruitAdapter = new FruitAdapter(FoodSearch.this, R.layout.food_item, fruitSearchList);
            searchListView.setAdapter(fruitAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
