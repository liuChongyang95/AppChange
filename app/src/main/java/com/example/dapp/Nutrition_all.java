package com.example.dapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import SearchDao.FoodDao;

public class Nutrition_all extends AppCompatActivity {


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
        setContentView(R.layout.fs_all_nutriton);
        Toolbar allNutritionToolbar = findViewById(R.id.all_nutrition_toolbar);
        setSupportActionBar(allNutritionToolbar);
        allNutritionToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nutrition_all.this.finish();
            }
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String foodName = bundle.getString("all_nutrition_name");
        FoodDao foodDao = new FoodDao(this);

        TextView scrollViewEnergy = findViewById(R.id.scrollView_energy);
        TextView scrollViewCH = findViewById(R.id.scrollView_CH);
        TextView scrollViewDF = findViewById(R.id.scrollView_DF);
        TextView scrollViewFat = findViewById(R.id.scrollView_fat);
        TextView scrollViewProtein = findViewById(R.id.scrollView_protein);
        TextView scrollViewWater = findViewById(R.id.scrollView_water);
        TextView scrollViewCLS = findViewById(R.id.scrollView_CLS);
        TextView scrollViewVA = findViewById(R.id.scrollView_vA);
        TextView scrollViewVB1 = findViewById(R.id.scrollView_vB1);
        TextView scrollViewVB2 = findViewById(R.id.scrollView_vB2);
        TextView scrollViewVB3 = findViewById(R.id.scrollView_vB3);
        TextView scrollViewVC = findViewById(R.id.scrollView_vC);
        TextView scrollViewVE = findViewById(R.id.scrollView_vE);
        TextView scrollViewFe = findViewById(R.id.scrollView_Fe);
        TextView scrollViewGa = findViewById(R.id.scrollView_Ga);
        TextView scrollViewMg = findViewById(R.id.scrollView_Mg);
        TextView scrollViewK = findViewById(R.id.scrollView_K);
        TextView scrollViewP = findViewById(R.id.scrollView_P);
        TextView scrollViewZn = findViewById(R.id.scrollView_Zn);
        TextView scrollViewNa = findViewById(R.id.scrollView_Na);
        TextView toolbarText = findViewById(R.id.search_text_FAN);
        TextView scrollViewpurine = findViewById(R.id.scrollView_purine);
        TextView scrollViewpur_ps = findViewById(R.id.scrollView_purine_ps);

        toolbarText.setText(foodName + "营养信息");

        String energy = foodDao.find_energy(foodName) + "千卡";
        scrollViewEnergy.setText(energy);
        String CH = foodDao.find_CH(foodName) + "克";
        scrollViewCH.setText(CH);
        String DF = foodDao.find_DF(foodName) + "克";
        scrollViewDF.setText(DF);
        String fat = foodDao.find_fat(foodName) + "克";
        scrollViewFat.setText(fat);
        String protein = foodDao.find_protein(foodName) + "克";
        scrollViewProtein.setText(protein);
        String water = foodDao.find_water(foodName) + "克";
        scrollViewWater.setText(water);
        String CLS = foodDao.find_CLS(foodName) + "毫克";
        scrollViewCLS.setText(CLS);
        String vA = foodDao.find_vA(foodName) + "μgRE";
        scrollViewVA.setText(vA);
        String vB1 = foodDao.find_vB1(foodName) + "毫克";
        scrollViewVB1.setText(vB1);
        String vB2 = foodDao.find_vB2(foodName) + "毫克";
        scrollViewVB2.setText(vB2);
        String vB3 = foodDao.find_vB3(foodName) + "毫克";
        scrollViewVB3.setText(vB3);
        String vC = foodDao.find_vC(foodName) + "毫克";
        scrollViewVC.setText(vC);
        String vE = foodDao.find_vE(foodName) + "毫克";
        scrollViewVE.setText(vE);
        String Fe = foodDao.find_Fe(foodName) + "毫克";
        scrollViewFe.setText(Fe);
        String Ga = foodDao.find_Ga(foodName) + "毫克";
        scrollViewGa.setText(Ga);
        String Mg = foodDao.find_Mg(foodName) + "毫克";
        scrollViewMg.setText(Mg);
        String K = foodDao.find_K(foodName) + "毫克";
        scrollViewK.setText(K);
        String P = foodDao.find_P(foodName) + "毫克";
        scrollViewP.setText(P);
        String Zn = foodDao.find_Zn(foodName) + "毫克";
        scrollViewZn.setText(Zn);
        String Na = foodDao.find_Na(foodName) + "毫克";
        scrollViewNa.setText(Na);
        String pur = foodDao.find_purine(foodName) + "毫克";
        String purine_ps;
        scrollViewpurine.setText(pur);
        if (!foodDao.find_purine(foodName).equals("—")) {
            if (Float.valueOf(foodDao.find_purine(foodName)) >= 100) {
                purine_ps = "高嘌呤食物";
            } else purine_ps = "正常";
            scrollViewpur_ps.setText(purine_ps);
        } else scrollViewpur_ps.setText("—");
    }
}
