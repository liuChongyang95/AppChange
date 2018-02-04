package com.example.dapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import Fragment_fs.Fragment_FS_GI;
import Fragment_fs.Fragment_FS_nutritioninfo;
import Fragment_fs.Fragment_FS_titalinfo;
import JavaBean.Food;
import SearchDao.FoodDao;


/**
 * Created by Administrator on 2017/12/28.
 */

public class FruitSelect extends AppCompatActivity {
    private String fruitName;
    private Bundle bundle;
    private FoodDao foodDao = new FoodDao(this);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fruit_message);
        Toolbar toolbar = findViewById(R.id.toolBar_fS);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FruitSelect.this.finish();
            }
        });

        Intent intent = getIntent();
        bundle = intent.getExtras();
        fruitName = bundle.getString("fruit_name");

        TextView fruitNameText = findViewById(R.id.searchResult_title);
        fruitNameText.setText(fruitName);
        food_title();
        food_nutrition();
        food_gi();
    }

    private void food_title() {
        String foodEnergy = foodDao.find_energy(fruitName);
        Fragment_FS_titalinfo fragment_fs_titalinfo = (Fragment_FS_titalinfo) getSupportFragmentManager().findFragmentById(R.id.title_fragment);
        fragment_fs_titalinfo.loading_title(fruitName, foodEnergy);
    }

    private void food_nutrition() {
        String Energy = foodDao.find_energy(fruitName) + "千卡";
        String Protein = foodDao.find_protein(fruitName) + "克";
        String Fat = foodDao.find_fat(fruitName) + "克";
        String DF = foodDao.find_DF(fruitName) + "克";
        String CH = foodDao.find_CH(fruitName) + "克";
        Fragment_FS_nutritioninfo fsNutritioninfo = (Fragment_FS_nutritioninfo) getSupportFragmentManager().findFragmentById(R.id.nutrition_fragment);
        fsNutritioninfo.loading_nutrition(Energy, Protein, CH, DF, Fat);
        fsNutritioninfo.setArguments(bundle);
    }

    private void food_gi() {
        String GI = foodDao.find_GI(fruitName);
        String GI_per = foodDao.find_GI_per(fruitName);
        Fragment_FS_GI fragment_fs_gi = (Fragment_FS_GI) getSupportFragmentManager().findFragmentById(R.id.gi_fragment);
        fragment_fs_gi.loading_GI(GI, GI_per);
    }

}

