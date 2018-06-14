package com.GProject.DiabetesApp;

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

    private String foodId;

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
//        多用循环,省事
        TextView[] textViews = new TextView[21];
        TextView scrollViewEnergy = findViewById(R.id.scrollView_energy);
        textViews[0] = scrollViewEnergy;
        TextView scrollViewProtein = findViewById(R.id.scrollView_protein);
        textViews[1] = scrollViewProtein;
        TextView scrollViewFat = findViewById(R.id.scrollView_fat);
        textViews[2] = scrollViewFat;
        TextView scrollViewDF = findViewById(R.id.scrollView_DF);
        textViews[3] = scrollViewDF;
        TextView scrollViewCH = findViewById(R.id.scrollView_CH);
        textViews[4] = scrollViewCH;
        TextView scrollViewWater = findViewById(R.id.scrollView_water);
        textViews[5] = scrollViewWater;
        TextView scrollViewVA = findViewById(R.id.scrollView_vA);
        textViews[6] = scrollViewVA;
        TextView scrollViewVB1 = findViewById(R.id.scrollView_vB1);
        textViews[7] = scrollViewVB1;
        TextView scrollViewVB2 = findViewById(R.id.scrollView_vB2);
        textViews[8] = scrollViewVB2;
        TextView scrollViewVB3 = findViewById(R.id.scrollView_vB3);
        textViews[9] = scrollViewVB3;
        TextView scrollViewVE = findViewById(R.id.scrollView_vE);
        textViews[10] = scrollViewVE;
        TextView scrollViewVC = findViewById(R.id.scrollView_vC);
        textViews[11] = scrollViewVC;
        TextView scrollViewFe = findViewById(R.id.scrollView_Fe);
        textViews[12] = scrollViewFe;
        TextView scrollViewGa = findViewById(R.id.scrollView_Ga);
        textViews[13] = scrollViewGa;
        TextView scrollViewNa = findViewById(R.id.scrollView_Na);
        textViews[14] = scrollViewNa;
        TextView scrollViewCLS = findViewById(R.id.scrollView_CLS);
        textViews[15] = scrollViewCLS;
        TextView scrollViewK = findViewById(R.id.scrollView_K);
        textViews[16] = scrollViewK;
        TextView scrollViewMg = findViewById(R.id.scrollView_Mg);
        textViews[17] = scrollViewMg;
        TextView scrollViewZn = findViewById(R.id.scrollView_Zn);
        textViews[18] = scrollViewZn;
        TextView scrollViewP = findViewById(R.id.scrollView_P);
        textViews[19] = scrollViewP;
        TextView scrollViewpurine = findViewById(R.id.scrollView_purine);
        textViews[20] = scrollViewpurine;

        String[] nutritions = foodDao.findNutritionByName(foodName);
        String[] units={"千卡","克","克","克","克","克","μgRE","毫克","毫克","毫克","毫克","毫克","毫克","毫克","毫克","毫克","毫克","毫克","毫克","毫克","毫克"};

        TextView toolbarText = findViewById(R.id.search_text_FAN);
        toolbarText.setText(getResources().getString(R.string.Nutrition_all_Title, foodName));

        TextView scrollViewpur_ps = findViewById(R.id.scrollView_purine_ps);

        for (int i=0;i<units.length;i++){
            textViews[i].setText(nutritions[i]+units[i]);
        }

        String purine_ps;
        if (!foodDao.find_purine(foodName).equals("—")) {
            if (Float.valueOf(foodDao.find_purine(foodName)) >= 100) {
                purine_ps = "高嘌呤食物";
            } else purine_ps = "正常";
            scrollViewpur_ps.setText(purine_ps);
        } else scrollViewpur_ps.setText("—");

    }
}
