package com.example.dapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import Adapter.FruitAdapter;
import JavaBean.Fruit;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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


        fruitDao = new FruitDao(this);
        fruitSearchList = fruitDao.searchFruit(searchFood_name);
        if (fruitSearchList.size() == 0) {
            Intent intent_null = new Intent(this, SearchNullResult.class);
            startActivity(intent_null);
            finish();
        } else {
            fruitAdapter = new FruitAdapter(FruitMainActivity.this, R.layout.fruit_item, fruitSearchList);
            listView.setAdapter(fruitAdapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (listView.getCount() > 0) {
                    Fruit fruit = fruitSearchList.get(i);
                    Intent intent = new Intent(FruitMainActivity.this, FruitSelect.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("fruit_name", fruit.getRi_Food_name());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

}
