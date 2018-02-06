package com.example.dapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import Fragment_fs.Fragment_FS_GI;
import Fragment_fs.Fragment_FS_nutritioninfo;
import Fragment_fs.Fragment_FS_titalinfo;
import JavaBean.Food;
import SearchDao.CareerDao;
import SearchDao.FoodDao;
import SearchDao.FruitDao;
import SearchDao.UserDao;


/**
 * Created by Administrator on 2017/12/28.
 */

public class FruitSelect extends AppCompatActivity implements View.OnClickListener {
    private String fruitName;
    private String Energy;

    private Bundle bundle_from_FMA;
    private CareerDao careerDao = new CareerDao(this);
    private FoodDao foodDao = new FoodDao(this);
    private UserDao userDao = new UserDao(this);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.fruit_message);
        LinearLayout VS_nutri_LL = findViewById(R.id.VS_LL);
        VS_nutri_LL.setOnClickListener(this);
        LinearLayout ADD_LL = findViewById(R.id.Add_LL);

        Toolbar toolbar = findViewById(R.id.toolBar_fS);
        toolbar.getBackground().setAlpha(0);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FruitSelect.this.finish();
            }
        });

        Intent intent = getIntent();
        bundle_from_FMA = intent.getExtras();
        fruitName = bundle_from_FMA.getString("fruit_name");
        TextView fruitNameText = findViewById(R.id.searchResult_title);
        fruitNameText.setText(fruitName);
        food_title();
        food_nutrition();
        food_gi();

    }

    private void food_title() {
        Energy = foodDao.find_energy(fruitName);
        Fragment_FS_titalinfo fragment_fs_titalinfo = (Fragment_FS_titalinfo) getSupportFragmentManager().findFragmentById(R.id.title_fragment);
        fragment_fs_titalinfo.loading_title(fruitName, Energy);
    }

    private void food_nutrition() {
        Energy = foodDao.find_energy(fruitName) + "千卡";
        String Protein = foodDao.find_protein(fruitName) + "克";
        String Fat = foodDao.find_fat(fruitName) + "克";
        String DF = foodDao.find_DF(fruitName) + "克";
        String CH = foodDao.find_CH(fruitName) + "克";
        Fragment_FS_nutritioninfo fsNutritioninfo = (Fragment_FS_nutritioninfo) getSupportFragmentManager().findFragmentById(R.id.nutrition_fragment);
        fsNutritioninfo.loading_nutrition(Energy, Protein, CH, DF, Fat);
        fsNutritioninfo.setArguments(bundle_from_FMA);
    }

    private void food_gi() {
        String GI = foodDao.find_GI(fruitName);
        String GI_per = foodDao.find_GI_per(fruitName);
        Fragment_FS_GI fragment_fs_gi = (Fragment_FS_GI) getSupportFragmentManager().findFragmentById(R.id.gi_fragment);
        fragment_fs_gi.loading_GI(GI, GI_per);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.VS_LL:
                VS();
                break;
        }
    }

    private void VS() {
        final EditText VS_editText = new EditText(FruitSelect.this);
        VS_editText.setHint("点击输入");
        VS_editText.setWidth(100);
        VS_editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        final AlertDialog.Builder VS_input = new AlertDialog.Builder(this);
        VS_input.setTitle("输入试算量(克)").setView(VS_editText);
        VS_input.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        VS_input.setPositiveButton("试算", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (VS_editText.length() > 0) {
                    int quantity = Integer.parseInt(VS_editText.getText().toString().trim());
                    Intent intent = new Intent(FruitSelect.this, Nutrition_Test.class);
                    bundle_from_FMA.putInt("food_quantity", quantity);
                    intent.putExtras(bundle_from_FMA);
                    startActivity(intent);
                }
            }
        });
        VS_input.show();
    }
}

