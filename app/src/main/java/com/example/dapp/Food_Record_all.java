package com.example.dapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Food_Record_all extends AppCompatActivity {
    private String[] record_item = {"记录饮食", "记录修改", "记录删除"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_record_all);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Food_Record_all.this, android.R.layout.simple_list_item_1, record_item);
        ListView ls = findViewById(R.id.food_record_all);
        ls.setAdapter(adapter);

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
