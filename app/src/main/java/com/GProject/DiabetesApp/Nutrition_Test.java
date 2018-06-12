package com.GProject.DiabetesApp;

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
        TextView[] textViews = new TextView[21];
        Intent intent = getIntent();
        bundle_from_FS = intent.getExtras();
        userId = bundle_from_FS.getString("from_Login_User_id");
        float food_quantity = bundle_from_FS.getInt("food_quantity");
        food_name = bundle_from_FS.getString("fruit_name");


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

        textViews[0] = f_energy;
        textViews[1] = f_protein;
        textViews[2] = f_fat;
        textViews[3] = f_DF;
        textViews[4] = f_CH;
        textViews[5] = f_water;
        textViews[6] = f_VA;
        textViews[7] = f_VB1;
        textViews[8] = f_VB2;
        textViews[9] = f_VB3;
        textViews[10] = f_VE;
        textViews[11] = f_VC;
        textViews[12] = f_FE;
        textViews[13] = f_GA;
        textViews[14] = f_NA;
        textViews[15] = f_CLS;
        textViews[16] = f_K;
        textViews[17] = f_MG;
        textViews[18] = f_ZN;
        textViews[19] = f_P;
        textViews[20] = f_purine;
        String[] units = {"千卡", "克", "克", "克", "克", "克", "μgRE", "毫克", "毫克", "毫克", "毫克", "毫克", "毫克", "毫克", "毫克", "毫克", "毫克", "毫克", "毫克", "毫克", "毫克"};
        String[] nutritions = foodDao.findNutritionByName(food_name);
        String[] names = {"能量", "蛋白质", "脂肪", "膳食纤维", "碳水化物", "水分", "维生素A", "维生素B1", "维生素B2", "维生素B3", "维生素E", "维生素C", "铁", "钙", "钠", "胆固醇", "钾", "镁", "锌", "磷", "嘌呤"};
        nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);

        toolbarText_NT.setText(food_name + "营养试算");
        float percent = food_quantity / 100;
        nf_percent = nf.format(percent);
        Test_energy();


        for (int i = 1; i < units.length; i++) {
            if (!nutritions[i].equals("—")) {
                float fo_nutrition = Float.valueOf(nf_percent) * Float.valueOf(nutritions[i]);
                textViews[i].setText(names[i]+" "+nf.format(fo_nutrition)+ units[i]);
            } else textViews[i].setText(names[i] + "—" + units[i]);
        }

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
            rec_energy = nf.format(VS_max_energy) + "千卡";
        r_energy.setText(rec_energy);
        if (fo_energy <= VS_max_energy)
            ps_energy.setText("合理");
        else
            ps_energy.setText("超出范围");
    }

}
