package com.example.dapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Adapter.FruitAdapter;
import Model.Fruit;
import SearchDao.FruitDao;

/**
 * Created by Administrator on 2017/12/30.
 */

public class FruitSearch extends AppCompatActivity {

    private List<Fruit> fruitSearchList = new ArrayList<>();
    private ListView searchListView;
    private String searchFruitText;
    private FruitDao fruitDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fruit_search);
        searchListView = findViewById(R.id.fruit_search);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        searchFruitText = bundle.getString("searchText");
        fruitDao = new FruitDao(FruitSearch.this);
        fruitSearchList = fruitDao.searchFruit(searchFruitText);
        FruitAdapter fruitAdapter = new FruitAdapter(FruitSearch.this, R.layout.fruit_item, fruitSearchList);
        searchListView.setAdapter(fruitAdapter);
    }
}
