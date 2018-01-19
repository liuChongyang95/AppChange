package com.example.dapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class Food_record_add extends AppCompatActivity {
    private EditText foodRecrod_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_record_add);
        foodRecrod_name = findViewById(R.id.food_record_name);
        foodRecrod_name.setFocusable(false);
        foodRecrod_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Food_record_add.this, FruitMainActivity.class);
                startActivity(intent);
            }
        });
    }
}
