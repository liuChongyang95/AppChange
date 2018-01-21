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
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Food_Record_all extends AppCompatActivity {
    private String[] record_item = {"记录饮食", "记录修改", "记录删除"};
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.food_record_all);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Food_Record_all.this, android.R.layout.simple_list_item_1, record_item);
        ListView ls = findViewById(R.id.food_record_all);
        ls.setAdapter(adapter);
        toolbar = findViewById(R.id.food_record_all_toolbar);
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Food_Record_all.this.finish();
            }
        });
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent intent = new Intent(Food_Record_all.this, Food_record_add.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
