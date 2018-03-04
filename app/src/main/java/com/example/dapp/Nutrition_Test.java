package com.example.dapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import junit.framework.Test;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import SearchDao.CareerDao;
import SearchDao.FoodDao;
import SearchDao.UserDao;
import SearchDao.UserIntakeDao;

public class Nutrition_Test extends AppCompatActivity {
    private CareerDao careerDao = new CareerDao(this);
    private FoodDao foodDao = new FoodDao(this);
    private UserDao userDao = new UserDao(this);
    private String rec_energy;
    private Bundle bundle_from_FS;
    private String userId;
    private String food_name;
    private TextView r_energy;
    private TextView f_energy;
    private TextView ps_energy;
    private TextView r_fat;
    private TextView f_fat;
    private TextView ps_fat;
    private TextView f_protein;
    private TextView r_protein;
    private TextView ps_protein;
    private TextView f_water;
    private TextView f_CH;
    private TextView f_DF;
    private TextView f_CLS;
    private TextView f_VA;
    private TextView f_VB1;
    private TextView f_VB2;
    private TextView f_VB3;
    private TextView f_VE;
    private TextView f_VC;
    private TextView f_NA;
    private TextView f_GA;
    private TextView f_MG;
    private TextView f_K;
    private TextView f_P;
    private TextView f_FE;
    private TextView f_ZN;
    private String nf_percent;
    private NumberFormat nf;
    private FoodSelected foodSelected;

    private TextView f_purine;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.nutrition_test);
        Toolbar toolbar = findViewById(R.id.VS_nutrition_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nutrition_Test.this.finish();
            }
        });

        f_energy = findViewById(R.id.VS_user_energy);
        r_energy = findViewById(R.id.VS_rec_energy);
        ps_energy = findViewById(R.id.VS_energy_ps);
        f_fat = findViewById(R.id.VS_user_fat);
        r_fat = findViewById(R.id.VS_rec_fat);
        ps_fat = findViewById(R.id.VS_fat_ps);
        f_protein = findViewById(R.id.VS_user_protein);
        r_protein = findViewById(R.id.VS_rec_protein);
        ps_protein = findViewById(R.id.VS_protein_ps);
        f_water = findViewById(R.id.VS_user_water);
        TextView toolbarText_NT = findViewById(R.id.search_text_NT);

        TextView r_water = findViewById(R.id.VS_rec_water);
        String ps_water;
        f_CH = findViewById(R.id.VS_user_CH);
        TextView r_CH = findViewById(R.id.VS_rec_CH);
        String ps_CH;
        f_DF = findViewById(R.id.VS_user_DF);
        TextView r_DF = findViewById(R.id.VS_rec_DF);
        String ps_DF;
        f_CLS = findViewById(R.id.VS_user_CLS);
        TextView r_CLS = findViewById(R.id.VS_rec_CLS);
        String ps_CLS;
        f_VA = findViewById(R.id.VS_user_vA);
        TextView r_VA = findViewById(R.id.VS_rec_vA);
        String ps_VA;
        f_VB1 = findViewById(R.id.VS_user_vB1);
        TextView r_VB1 = findViewById(R.id.VS_rec_vB1);
        String ps_VB1;
        f_VB2 = findViewById(R.id.VS_user_vB2);
        TextView r_VB2 = findViewById(R.id.VS_rec_vB2);
        String ps_VB2;
        f_VB3 = findViewById(R.id.VS_user_vB3);
        TextView r_VB3 = findViewById(R.id.VS_Rec_vB3);
        String ps_VB3;
        f_VE = findViewById(R.id.VS_user_vE);
        TextView r_VE = findViewById(R.id.VS_rec_vE);
        String ps_VE;
        f_VC = findViewById(R.id.VS_user_vC);
        TextView r_VC = findViewById(R.id.VS_rec_vC);
        String ps_VC;
        f_NA = findViewById(R.id.VS_user_Na);
        TextView r_NA = findViewById(R.id.VS_rec_Na);
        String ps_NA;
        f_GA = findViewById(R.id.VS_user_Ga);
        TextView r_GA = findViewById(R.id.VS_rec_Ga);
        String ps_GA;
        f_MG = findViewById(R.id.VS_user_Mg);
        TextView r_MG = findViewById(R.id.VS_rec_Mg);
        String ps_MG;
        f_K = findViewById(R.id.VS_user_K);
        TextView r_K = findViewById(R.id.VS_rec_K);
        String ps_K;
        f_P = findViewById(R.id.VS_user_P);
        TextView r_P = findViewById(R.id.VS_rec_P);
        String ps_P;
        f_FE = findViewById(R.id.VS_user_Fe);
        TextView r_FE = findViewById(R.id.VS_rec_Fe);
        String ps_FE;
        f_ZN = findViewById(R.id.VS_user_Zn);
        TextView r_ZN = findViewById(R.id.VS_rec_Zn);
        String ps_ZN;

        f_purine = findViewById(R.id.VS_user_purine);


        nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);

        Intent intent = getIntent();
        bundle_from_FS = intent.getExtras();
        userId = bundle_from_FS.getString("from_Login_User_id");
        float food_quantity = bundle_from_FS.getInt("food_quantity");
        food_name = bundle_from_FS.getString("fruit_name");

        toolbarText_NT.setText(food_name + "营养试算");
        float percent = food_quantity / 100;
        nf_percent = nf.format(percent);
        Test_energy();
        Test_protein();
        Test_fat();
        Test_protein();
        Test_Ga();
        Test_DF();
        Test_CH();
        Test_Fe();
        Test_K();
        Test_Mg();
        Test_Na();
        Test_P();
        Test_VA();
        Test_VB1();
        Test_VB2();
        Test_VB3();
        Test_VC();
        Test_VE();
        Test_water();
        Test_Zn();
        Test_CLS();
        Test_purine();
    }

    private void Test_energy() {
        UserIntakeDao userIntakeDao = new UserIntakeDao(this);
        float userWeight = Float.parseFloat(userDao.getWeight(userId));
        String career = userDao.getCareer(userId);
        String intensity = userDao.getIntensity(career);
        String shape = userDao.getShape(userId);
        int min_energy = careerDao.getMin_energy(shape, intensity, career);
        int max_energy = careerDao.getMax_energy(shape, intensity, career);
        float VS_min_energy = min_energy * (int) userWeight;
        float VS_max_energy = max_energy * (int) userWeight;
        foodSelected = new FoodSelected();
        String cumEnergy = userIntakeDao.getTodayenergy(userId, foodSelected.initDate()).replace(",", "");
        Log.d("Nutrition", cumEnergy);
        VS_min_energy = VS_min_energy - Float.valueOf(cumEnergy);
        VS_max_energy = VS_max_energy - Float.valueOf(cumEnergy);

        String g_energy = foodDao.find_energy(food_name);
        float fo_energy = Float.valueOf(nf_percent) * Integer.valueOf(g_energy);
        String nf_energy = "能量 " + nf.format(fo_energy) + "千卡";
        f_energy.setText(nf_energy);
        if (VS_max_energy != VS_min_energy)
            rec_energy = VS_min_energy + "—" + VS_max_energy + "千卡";
        else if (VS_min_energy <= 0 || VS_max_energy == VS_min_energy)
            rec_energy = VS_max_energy + "千卡";
        r_energy.setText(rec_energy);
        if (fo_energy <= VS_max_energy)
            ps_energy.setText("合理");
        else
            ps_energy.setText("超出范围");
    }

    private void Test_protein() {
        String info;
        String g_protein = foodDao.find_protein(food_name);
        if (g_protein != null && !g_protein.equals("…") && !g_protein.equals("Tr") && g_protein.length() > 0 && !g_protein.equals("—") && !g_protein.equals("┄") && !g_protein.equals("─")) {
            float fo_nutrition = Float.valueOf(nf_percent) * Float.valueOf(g_protein);
            info = "蛋白质 " + nf.format(fo_nutrition) + "克";
        } else info = "蛋白质 —克";
        f_protein.setText(info);
    }

    private void Test_fat() {
        String info;
        String g_protein = foodDao.find_fat(food_name);
        if (g_protein != null && !g_protein.equals("…") && !g_protein.equals("Tr") && g_protein.length() > 0 && !g_protein.equals("—") && !g_protein.equals("┄") && !g_protein.equals("─")) {
            float fo_nutrition = Float.valueOf(nf_percent) * Float.valueOf(g_protein);
            info = "脂肪 " + nf.format(fo_nutrition) + "克";
        } else info = "脂肪 —克";
        f_fat.setText(info);
    }

    private void Test_water() {
        String info;
        String g_protein = foodDao.find_water(food_name);
        if (g_protein != null && !g_protein.equals("…") && !g_protein.equals("Tr") && g_protein.length() > 0 && !g_protein.equals("—") && !g_protein.equals("┄") && !g_protein.equals("─")) {
            float fo_nutrition = Float.valueOf(nf_percent) * Float.valueOf(g_protein);
            info = "水分 " + nf.format(fo_nutrition) + "克";
        } else info = "水分 —克";
        f_water.setText(info);
    }

    private void Test_CH() {
        String info;
        String g_protein = foodDao.find_CH(food_name);
        if (g_protein != null && !g_protein.equals("…") && !g_protein.equals("Tr") && g_protein.length() > 0 && !g_protein.equals("—") && !g_protein.equals("┄") && !g_protein.equals("─")) {
            float fo_nutrition = Float.valueOf(nf_percent) * Float.valueOf(g_protein);
            info = "碳水化物 " + nf.format(fo_nutrition) + "克";
        } else info = "碳水化物 —克";
        f_CH.setText(info);
    }

    private void Test_DF() {
        String info;
        String g_protein = foodDao.find_DF(food_name);
        if (g_protein != null && !g_protein.equals("…") && !g_protein.equals("Tr") && g_protein.length() > 0 && !g_protein.equals("—") && !g_protein.equals("┄") && !g_protein.equals("─")) {
            float fo_nutrition = Float.valueOf(nf_percent) * Float.valueOf(g_protein);
            info = "膳食纤维 " + nf.format(fo_nutrition) + "克";
        } else info = "膳食纤维 —克";
        f_DF.setText(info);
    }

    private void Test_VA() {
        String info;
        String g_protein = foodDao.find_vA(food_name);
        if (g_protein != null && !g_protein.equals("…") && !g_protein.equals("Tr") && g_protein.length() > 0 && !g_protein.equals("—") && !g_protein.equals("┄") && !g_protein.equals("─")) {
            float fo_nutrition = Float.valueOf(nf_percent) * Float.valueOf(g_protein);
            info = "维生素A " + nf.format(fo_nutrition) + "毫克";
        } else info = "维生素A —毫克";
        f_VA.setText(info);
    }

    private void Test_VB1() {
        String info;
        String g_protein = foodDao.find_vB1(food_name);
        if (g_protein != null && !g_protein.equals("…") && !g_protein.equals("Tr") && g_protein.length() > 0 && !g_protein.equals("—") && !g_protein.equals("┄") && !g_protein.equals("─")) {
            float fo_nutrition = Float.valueOf(nf_percent) * Float.valueOf(g_protein);
            info = "维生素B1 " + nf.format(fo_nutrition) + "毫克";
        } else info = "维生素B1 —毫克";
        f_VB1.setText(info);
    }

    private void Test_VB2() {
        String info;
        String g_protein = foodDao.find_vB2(food_name);
        if (g_protein != null && !g_protein.equals("…") && !g_protein.equals("Tr") && g_protein.length() > 0 && !g_protein.equals("—") && !g_protein.equals("┄") && !g_protein.equals("─")) {
            float fo_nutrition = Float.valueOf(nf_percent) * Float.valueOf(g_protein);
            info = "维生素B2 " + nf.format(fo_nutrition) + "毫克";
        } else info = "维生素B2 —毫克";
        f_VB2.setText(info);
    }

    private void Test_VB3() {
        String info;
        String g_protein = foodDao.find_vB3(food_name);
        if (g_protein != null && !g_protein.equals("…") && !g_protein.equals("Tr") && g_protein.length() > 0 && !g_protein.equals("—") && !g_protein.equals("┄") && !g_protein.equals("─")) {
            float fo_nutrition = Float.valueOf(nf_percent) * Float.valueOf(g_protein);
            info = "维生素B3 " + nf.format(fo_nutrition) + "毫克";
        } else info = "维生素B3 —毫克";
        f_VB3.setText(info);
    }

    private void Test_VE() {
        String info;
        String g_protein = foodDao.find_vE(food_name);
        if (g_protein != null && !g_protein.equals("...") && !g_protein.equals("Tr") && g_protein.length() > 0 && !g_protein.equals("—") && !g_protein.equals("┄") && !g_protein.equals("─")) {
            float fo_nutrition = Float.valueOf(nf_percent) * Float.valueOf(g_protein);
            info = "维生素E " + nf.format(fo_nutrition) + "毫克";
        } else info = "维生素E —毫克";
        f_VE.setText(info);
    }

    private void Test_VC() {
        String info;
        String g_protein = foodDao.find_vC(food_name);
        if (g_protein != null && !g_protein.equals("…") && !g_protein.equals("Tr") && g_protein.length() > 0 && !g_protein.equals("—") && !g_protein.equals("┄") && !g_protein.equals("─")) {
            float fo_nutrition = Float.valueOf(nf_percent) * Float.valueOf(g_protein);
            info = "维生素E " + nf.format(fo_nutrition) + "毫克";
        } else info = "维生素E —毫克";
        f_VC.setText(info);
    }

    private void Test_Na() {
        String info;
        String g_protein = foodDao.find_Na(food_name);
        if (g_protein != null && !g_protein.equals("…") && !g_protein.equals("Tr") && g_protein.length() > 0 && !g_protein.equals("—") && !g_protein.equals("┄") && !g_protein.equals("─")) {
            float fo_nutrition = Float.valueOf(nf_percent) * Float.valueOf(g_protein);
            info = "钠 " + nf.format(fo_nutrition) + "毫克";
        } else info = "钠 —毫克";
        f_NA.setText(info);
    }

    private void Test_Fe() {
        String info;
        String g_protein = foodDao.find_Fe(food_name);
        if (g_protein != null && !g_protein.equals("…") && !g_protein.equals("Tr") && g_protein.length() > 0 && !g_protein.equals("—") && !g_protein.equals("┄") && !g_protein.equals("─")) {
            float fo_nutrition = Float.valueOf(nf_percent) * Float.valueOf(g_protein);
            info = "铁 " + nf.format(fo_nutrition) + "毫克";
        } else info = "铁 —毫克";
        f_FE.setText(info);
    }

    private void Test_Ga() {
        String info;
        String g_protein = foodDao.find_Ga(food_name);
        if (g_protein != null && !g_protein.equals("…") && !g_protein.equals("Tr") && g_protein.length() > 0 && !g_protein.equals("—") && !g_protein.equals("┄") && !g_protein.equals("─")) {
            float fo_nutrition = Float.valueOf(nf_percent) * Float.valueOf(g_protein);
            info = "钙 " + nf.format(fo_nutrition) + "毫克";
        } else info = "钙 —毫克";
        f_GA.setText(info);
    }

    private void Test_Mg() {
        String info;
        String g_protein = foodDao.find_Mg(food_name);
        if (g_protein != null && !g_protein.equals("…") && !g_protein.equals("Tr") && g_protein.length() > 0 && !g_protein.equals("—") && !g_protein.equals("┄") && !g_protein.equals("─")) {
            float fo_nutrition = Float.valueOf(nf_percent) * Float.valueOf(g_protein);
            info = "镁 " + nf.format(fo_nutrition) + "毫克";
        } else info = "镁 —毫克";
        f_MG.setText(info);
    }

    private void Test_P() {
        String info;
        String g_protein = foodDao.find_P(food_name);
        if (g_protein != null && !g_protein.equals("…") && !g_protein.equals("Tr") && g_protein.length() > 0 && !g_protein.equals("—") && !g_protein.equals("┄") && !g_protein.equals("─")) {
            float fo_nutrition = Float.valueOf(nf_percent) * Float.valueOf(g_protein);
            info = "磷 " + nf.format(fo_nutrition) + "毫克";
        } else info = "磷 —毫克";
        f_P.setText(info);
    }

    private void Test_K() {
        String info;
        String g_protein = foodDao.find_K(food_name);
        if (g_protein != null && !g_protein.equals("…") && !g_protein.equals("Tr") && g_protein.length() > 0 && !g_protein.equals("—") && !g_protein.equals("┄") && !g_protein.equals("─")) {
            float fo_nutrition = Float.valueOf(nf_percent) * Float.valueOf(g_protein);
            info = "钾 " + nf.format(fo_nutrition) + "毫克";
        } else info = "钾 —毫克";
        f_K.setText(info);
    }

    private void Test_Zn() {
        String info;
        String g_protein = foodDao.find_Zn(food_name);
        if (g_protein != null && !g_protein.equals("…") && !g_protein.equals("Tr") && g_protein.length() > 0 && !g_protein.equals("—") && !g_protein.equals("┄") && !g_protein.equals("─")) {
            float fo_nutrition = Float.valueOf(nf_percent) * Float.valueOf(g_protein);
            info = "锌 " + nf.format(fo_nutrition) + "毫克";
        } else info = "锌 —毫克";
        f_ZN.setText(info);
    }

    private void Test_CLS() {
        String info;
        String g_protein = foodDao.find_CLS(food_name);
        if (g_protein != null && !g_protein.equals("…") && !g_protein.equals("Tr") && g_protein.length() > 0 && !g_protein.equals("—") && !g_protein.equals("┄") && !g_protein.equals("─")) {
            float fo_nutrition = Float.valueOf(nf_percent) * Float.valueOf(g_protein);
            info = "胆固醇 " + nf.format(fo_nutrition) + "毫克";
        } else info = "胆固醇 —毫克";
        f_CLS.setText(info);
    }

    private void Test_purine() {
        String info;
        String g_protein = foodDao.find_purine(food_name);
        if (g_protein != null && !g_protein.equals("…") && !g_protein.equals("Tr") && g_protein.length() > 0 && !g_protein.equals("—") && !g_protein.equals("┄") && !g_protein.equals("─")) {
            float fo_nutrition = Float.valueOf(nf_percent) * Float.valueOf(g_protein);
            info = "嘌呤 " + nf.format(fo_nutrition) + "毫克";
        } else info = "嘌呤 —毫克";
        f_purine.setText(info);
    }
}
