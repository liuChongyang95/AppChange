package com.GProject.DiabetesApp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Random;

import SearchDao.FoodDao;
import Util.DBUtil;

public class SelfMainFood extends AppCompatActivity implements View.OnClickListener {

    private Bundle bundle;
    private EditText[] editTexts;
    private EditText[] editTexts_name;
    private float[] eachPercent;
    private EditText name1;
    private EditText num1;
    private EditText name2;
    private EditText num2;
    private EditText name3;
    private EditText num3;
    private EditText name4;
    private EditText num4;
    private EditText name5;
    private EditText num5;
    private EditText nameAll;
    private NumberFormat numberFormat;
    private FoodDao foodDao;
    private static final String TAG = "SelfMainFood";
    private String[] nutri = {"Food_dic_energy", "Food_dic_protein", "Food_dic_fat", "Food_dic_DF", "Food_dic_CH",
            "Food_dic_water", "Food_dic_vA", "Food_dic_vB1", "Food_dic_vB2", "Food_dic_vB3",
            "Food_dic_vE", "Food_dic_vC", "Food_dic_Fe", "Food_dic_Ga", "Food_dic_Na", "Food_dic_CLS", "Food_dic_K", "Food_dic_Mg", "Food_dic_Zn", "Food_dic_P", "Food_dic_purine"};
    private static final String PACKAGE_NAME = "com.GProject.DiabetesApp";
    private static final String DATABASE_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME + "/" + "databases";
    private static final String DATABASE_FILENAME = "food.db";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selfmainfood);
        if (Build.VERSION.SDK_INT >= 21) {
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        Toolbar toolbar = findViewById(R.id.selfMainToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelfMainFood.this.finish();
            }
        });
        numberFormat=NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        foodDao = new FoodDao(this);
        Intent intent = getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            bundle.putString("activity", "SelfMainFood");
        }
//if语句判断顺序

        editTexts = new EditText[5];
        editTexts_name = new EditText[5];


        nameAll = findViewById(R.id.foodName);

        name1 = findViewById(R.id.component1_name);
        name1.setFocusable(false);
        num1 = findViewById(R.id.component1_num);
        name1.setOnClickListener(this);
        editTexts[0] = num1;

        name2 = findViewById(R.id.component2_name);
        name2.setFocusable(false);
        num2 = findViewById(R.id.component2_num);
        name2.setOnClickListener(this);
        editTexts[1] = num2;

        name3 = findViewById(R.id.component3_name);
        name3.setFocusable(false);
        num3 = findViewById(R.id.component3_num);
        name3.setOnClickListener(this);
        editTexts[2] = num3;

        name4 = findViewById(R.id.component4_name);
        name4.setFocusable(false);
        num4 = findViewById(R.id.component4_num);
        name4.setOnClickListener(this);
        editTexts[3] = num4;


        name5 = findViewById(R.id.component5_name);
        name5.setFocusable(false);
        num5 = findViewById(R.id.component5_num);
        name5.setOnClickListener(this);
        editTexts[4] = num5;

        editTexts_name[0] = name1;
        editTexts_name[1] = name2;
        editTexts_name[2] = name3;
        editTexts_name[3] = name4;
        editTexts_name[4] = name5;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle = intent.getExtras();
        if (bundle.getString("nameCode") != null) {
            switch (bundle.getString("nameCode")) {
                case "1":
                    name1.setText(bundle.getString("fruit_name"));
                    num1.setText(bundle.getString("nameValue"));
                    break;
                case "2":
                    name2.setText(bundle.getString("fruit_name"));
                    num2.setText(bundle.getString("nameValue"));
                    break;
                case "3":
                    name3.setText(bundle.getString("fruit_name"));
                    num3.setText(bundle.getString("nameValue"));
                    break;
                case "4":
                    name4.setText(bundle.getString("fruit_name"));
                    num4.setText(bundle.getString("nameValue"));
                    break;
                case "5":
                    name5.setText(bundle.getString("fruit_name"));
                    num5.setText(bundle.getString("nameValue"));
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.selfmain_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addfood_sure:
//                自维护录入
//                随机生成22以后的食物字典
                Random random = new Random();
                Integer i = random.nextInt(10);
                Integer b = i + random.nextInt(543);
//                name
                String foodName = nameAll.getText().toString();
//                id
                StringBuilder foodId = new StringBuilder();
//                总和
                int allNum = 0;
                int tempNum;
                for (int m = 0; m < editTexts.length; m++) {
                    if (!"".equals(editTexts[m].getText().toString().trim())) {
                        tempNum = allNum + Integer.valueOf(editTexts[m].getText().toString().trim());
                        allNum = tempNum;
                    }
                }
                float[] eachNutrition = new float[21];
                String[] tempNutrition;
//                单品百分比
                eachPercent = new float[5];
                for (int n = 0; n < eachPercent.length; n++) {
                    if (!"".equals(editTexts_name[n].getText().toString().trim())) {
                        eachPercent[n] = Float.valueOf(editTexts[n].getText().toString().trim()) / allNum;
                        BigDecimal bigDecimal = new BigDecimal(eachPercent[n]);
                        Log.d(TAG, String.valueOf(eachPercent[n]));
                        float halfUp = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                        tempNutrition = foodDao.findNutritionByName(editTexts_name[n].getText().toString().trim());
                        for (int h = 0; h < tempNutrition.length; h++) {
                            Log.d(TAG, tempNutrition[h]);
                            if (!tempNutrition[h].equals("—")) {
                                tempNutrition[h] = String.valueOf(Float.valueOf(tempNutrition[h]) * halfUp);
                                eachNutrition[h] = eachNutrition[h] + Float.valueOf(tempNutrition[h]);
                            }
                        }
                    }
                }
                if (!foodName.equals("")) {
                    foodId.append("22.").append(i.toString()).append(".").append(b.toString());
                    SQLiteDatabase foodDB = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH + "/" + DATABASE_FILENAME, null);
                    ContentValues values = new ContentValues();
                    for (int p = 0; p < nutri.length; p++) {
                        values.put(nutri[p],numberFormat.format(eachNutrition[p]).replace(",",""));
                    }
                    values.put("Food_dic_classic", "(自)");
                    values.put("Food_dic_id", String.valueOf(foodId));
                    values.put("Food_dic_name", foodName);
                    foodDB.insertOrThrow("Food_Dic", null, values);
                    values.clear();
                    foodDB.close();
                    Toast.makeText(this, "自维护饮食" + foodName + "，添加成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else Toast.makeText(this, "请定义自维护饮食名字", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.component1_name:
                Intent intent1 = new Intent(SelfMainFood.this, FoodSearchToAdd.class);
                bundle.putString("name_position", "name1");
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
            case R.id.component2_name:
                Intent intent2 = new Intent(SelfMainFood.this, FoodSearchToAdd.class);
                bundle.putString("name_position", "name2");
                intent2.putExtras(bundle);
                startActivity(intent2);
                break;
            case R.id.component3_name:
                Intent intent3 = new Intent(SelfMainFood.this, FoodSearchToAdd.class);
                bundle.putString("name_position", "name3");
                intent3.putExtras(bundle);
                startActivity(intent3);
                break;
            case R.id.component4_name:
                Intent intent4 = new Intent(SelfMainFood.this, FoodSearchToAdd.class);
                bundle.putString("name_position", "name4");
                intent4.putExtras(bundle);
                startActivity(intent4);
                break;
            case R.id.component5_name:
                bundle.putString("name_position", "name5");
                Intent intent5 = new Intent(SelfMainFood.this, FoodSearchToAdd.class);
                intent5.putExtras(bundle);
                startActivity(intent5);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
