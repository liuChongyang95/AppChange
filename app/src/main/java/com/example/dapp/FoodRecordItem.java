package com.example.dapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import Database.DBHelper;
import Util.Staticfinal_Value;

public class FoodRecordItem extends AppCompatActivity {
    private Bundle bundle_from_FRLV;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Staticfinal_Value sfv;

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
        setContentView(R.layout.foodrecord_info);
        sfv = new Staticfinal_Value();
        dbHelper = new DBHelper(this, "DApp.db", null, sfv.staticVersion());
        Toolbar toolbar = findViewById(R.id.FR_I_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodRecordItem.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fri_tb, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteRecord:
                Intent intent = getIntent();
                bundle_from_FRLV = intent.getExtras();
                if (bundle_from_FRLV != null) {
                    String item_id = bundle_from_FRLV.getString("itemId");
                    Log.d("FoodRecordItem", item_id);
                    sqLiteDatabase = dbHelper.getWritableDatabase();
                    sqLiteDatabase.delete("UserFood", "_id=?", new String[]{item_id});
                }
                Toast.makeText(this, "记录删除", Toast.LENGTH_SHORT).show();
                sqLiteDatabase.close();
                FoodRecordItem.this.finish();
                break;
            default:
                break;
        }
        return true;
    }
}
