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

import java.text.NumberFormat;

import Database.DBHelper;
import SearchDao.FoodDao;
import SearchDao.UserIntakeDao;
import Util.Staticfinal_Value;

public class FoodRecordItem extends AppCompatActivity {
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private NumberFormat numberFormat;

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
        Staticfinal_Value sfv = new Staticfinal_Value();
        dbHelper = new DBHelper(this, "DApp.db", null, sfv.staticVersion());
        Toolbar toolbar = findViewById(R.id.FR_I_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodRecordItem.this.finish();
            }
        });
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
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
                Bundle bundle_from_FRLV = intent.getExtras();
                if (bundle_from_FRLV != null) {
                    String item_id = bundle_from_FRLV.getString("itemId");
                    String userId = bundle_from_FRLV.getString("User_id");
                    String UIclass = bundle_from_FRLV.getString("intake_class");
                    String UIdate = bundle_from_FRLV.getString("intake_date");
                    String foodName = bundle_from_FRLV.getString("food_name");
                    String foodSize = bundle_from_FRLV.getString("itemSize");
                    sqLiteDatabase = dbHelper.getWritableDatabase();
                    deleteRecord(foodName, foodSize, userId, UIclass, UIdate);
                    sqLiteDatabase.delete("UserFood", "_id=?", new String[]{item_id});
                }
                Toast.makeText(this, "记录删除", Toast.LENGTH_SHORT).show();
                dbHelper.close();
                sqLiteDatabase.close();
                FoodRecordItem.this.finish();
                break;
            default:
                break;
        }
        return true;
    }

    public void deleteRecord(String fruitName, String fruitSize, String userId, String UIclass, String UIdate) {
        FoodDao foodDao = new FoodDao(this);
        ContentValues values = new ContentValues();
        String[] nutArray = new String[23];
        //字典表里的营养值
        nutArray[0] = foodDao.find_energy(fruitName);
        nutArray[1] = foodDao.find_protein(fruitName);
        nutArray[3] = foodDao.find_DF(fruitName);
        nutArray[4] = foodDao.find_CH(fruitName);
        nutArray[2] = foodDao.find_fat(fruitName);
        nutArray[5] = foodDao.find_water(fruitName);
        nutArray[15] = foodDao.find_CLS(fruitName);
        nutArray[6] = foodDao.find_vA(fruitName);
        nutArray[7] = foodDao.find_vB1(fruitName);
        nutArray[8] = foodDao.find_vB2(fruitName);
        nutArray[9] = foodDao.find_vB3(fruitName);
        nutArray[10] = foodDao.find_vE(fruitName);
        nutArray[11] = foodDao.find_vC(fruitName);
        nutArray[12] = foodDao.find_Fe(fruitName);
        nutArray[14] = foodDao.find_Na(fruitName);
        nutArray[17] = foodDao.find_Mg(fruitName);
        nutArray[18] = foodDao.find_Zn(fruitName);
        nutArray[13] = foodDao.find_Ga(fruitName);
        nutArray[16] = foodDao.find_K(fruitName);
        nutArray[19] = foodDao.find_P(fruitName);
        nutArray[20] = foodDao.find_purine(fruitName);
        UserIntakeDao userIntakeDao = new UserIntakeDao(this);
        //营养表里的营养值
        String[] intakeNutri = userIntakeDao.getFromUserIntake(userId, UIclass, UIdate);
        //营养表里的营养名称
        String[] NutName = {"UI_energy", "UI_protein", "UI_fat", "UI_DF", "UI_CH", "UI_water", "UI_VA", "UI_VB1",
                "UI_VB2", "UI_VB3", "UI_VE", "UI_VC", "UI_Fe", "UI_Ga", "UI_Na", "UI_CLS", "UI_K", "UI_Mg",
                "UI_Zn", "UI_P", "UI_purine"};
        //每100克倍数
        float f_fruitSize = Float.valueOf(fruitSize);
        String percent = numberFormat.format(f_fruitSize / 100);
        float f_percent = Float.valueOf(percent);
        //临时赋值
        String result_ab;
        //要减去的所有营养
        for (int i = 0; i < 21; i++) {
            if (nutArray[i] != null && !nutArray[i].equals("…") && !nutArray[i].equals("Tr") && nutArray[i].length() > 0 && !nutArray[i].equals("—") && !nutArray[i].equals("┄") && !nutArray[i].equals("─")) {
                nutArray[i] = numberFormat.format(Float.valueOf(nutArray[i]) * f_percent);
                result_ab = numberFormat.format(Float.valueOf(intakeNutri[i]) - Float.valueOf(nutArray[i]));
                values.put(NutName[i], result_ab);
            }
            sqLiteDatabase.update("UserIntake", values, "User_id = ? and UI_date=? and UI_class=?", new String[]{userId, UIdate, UIclass});
        }
    }
}
