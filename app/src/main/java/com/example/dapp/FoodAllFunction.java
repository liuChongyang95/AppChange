package com.example.dapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodAllFunction extends AppCompatActivity implements View.OnClickListener {
    private String[] record_item = {"分析报告", "记录修改", "饮食情况"};
    private int[] record_pic = {R.drawable.analysis_a, R.drawable.reprot_re, R.drawable.report};
    private List<Map<String, Object>> record_list;
    private Toolbar toolbar;
    private GridView gridView;
    private SimpleAdapter sim_adapter;
    private LinearLayout add_food_LL;
    private Bundle bundle_from_MA;

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
        setContentView(R.layout.food_all_function);
        toolbar = findViewById(R.id.food_record_all_toolbar);
        gridView = findViewById(R.id.foodRecord_gridView);
        add_food_LL = findViewById(R.id.add_food_record_LL);
        record_list = new ArrayList<>();
        getData();
        String[] from = {"image_record", "text_record"};
        int[] to = {R.id.image_record, R.id.text_record};
        sim_adapter = new SimpleAdapter(this, record_list, R.layout.record_all_item, from, to);
        gridView.setAdapter(sim_adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        Intent intent = new Intent();
                        intent.setClass(FoodAllFunction.this, FoodRecordListView.class);
                        intent.putExtras(bundle_from_MA);
                        startActivity(intent);
                        break;
                    case 2:
                        break;
                }
            }
        });

        Intent intent = getIntent();
        bundle_from_MA = intent.getExtras();

        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodAllFunction.this.finish();
            }
        });


    }

    private List<Map<String, Object>> getData() {
        for (int i = 0; i < record_pic.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image_record", record_pic[i]);
            map.put("text_record", record_item[i]);
            record_list.add(map);
        }
        return record_list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_food_record_LL:
                Intent intent1 = new Intent(FoodAllFunction.this, FoodSearchToAdd.class);
                intent1.putExtras(bundle_from_MA);
                startActivity(intent1);
                break;
        }
    }
}
