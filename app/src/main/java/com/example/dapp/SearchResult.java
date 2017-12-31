package com.example.dapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;


/**
 * Created by Administrator on 2017/12/28.
 */

public class SearchResult extends AppCompatActivity {
    private TextView fruitNameText;
    private String fruitName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fruit_message);
        fruitNameText = findViewById(R.id.searchResult_name);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        fruitName = bundle.getString("fruit_name");
        fruitNameText.setText(fruitName);
    }
}
