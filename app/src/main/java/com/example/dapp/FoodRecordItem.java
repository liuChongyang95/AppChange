package com.example.dapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Objects;

import Database.DBHelper;
import SearchDao.FoodDao;
import SearchDao.FoodRecordDao;
import SearchDao.UserIntakeDao;
import Util.Staticfinal_Value;

public class FoodRecordItem extends AppCompatActivity implements View.OnClickListener {
    private DBHelper dbHelper;
    private NumberFormat numberFormat;
    //    基本食物信息
    private String item_id;
    private String userId;
    private String UIclass;
    private String UIdate;
    private String foodName;
    private String foodSize;
    private String foodEnergy;
    private String foodId;
    private String unitStr;
    private String nf_per;
    private float percent;
    //暂时除去能量
    private String[] nutrition = new String[21];
    private String[] nutriArr = new String[21];
    private String[] unit = {"千卡", "克", "克", "克", "克",
            "克", "克", "克", "毫克", "毫克",
            "毫克", "毫克", "毫克", "毫克", "毫克",
            "毫克", "毫克", "毫克", "毫克", "毫克",
            "毫克"};

    private View view;
    //    修改记录
    private AlertDialog changeRecDialog;
    private LayoutInflater inflater;
    private TextView date_setup_c;
    private RadioButton breakfast_0;
    private RadioButton lunch_1;
    private RadioButton dinner_2;
    private RadioButton after_lunch_4;
    private RadioButton befor_lunch_3;
    private RadioButton any_time_5;
    private RadioGroup foodClassgroup;
    private String fdClassic;
    private EditText intakeSize;
    private TextView intakeSize_str;
    private String unitEnergy;
    private FoodDao foodDao;
    private UserIntakeDao userIntakeDao;
    private FoodRecordDao foodRecordDao;
    private SQLiteDatabase sqLiteDatabase;


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
        inflater = LayoutInflater.from(this);
        Staticfinal_Value sfv = new Staticfinal_Value();
        dbHelper = new DBHelper(this, "DApp.db", null, sfv.staticVersion());
        userIntakeDao = new UserIntakeDao(this);
        foodRecordDao = new FoodRecordDao(this);
        Toolbar toolbar = findViewById(R.id.FR_I_toolbar);
        setSupportActionBar(toolbar);
        TextView date = findViewById(R.id.FR_I_date);
        TextView food_class = findViewById(R.id.FR_I_class);
        TextView food_size = findViewById(R.id.FRI_size);
        TextView food_nutrition = findViewById(R.id.FRI_nutrition);
        TextView food_egy_size = findViewById(R.id.FRI_energy_size);
        TextView food_protein = findViewById(R.id.FRI_protein_size);
        TextView food_fat = findViewById(R.id.FRI_fat_size);
        TextView food_CH = findViewById(R.id.FRI_CH_size);
        TextView food_DF = findViewById(R.id.FRI_DF_size);
        TextView food_water = findViewById(R.id.FRI_water_size);
        TextView food_CLS = findViewById(R.id.FRI_CLS_size);
        TextView food_VA = findViewById(R.id.FRI_VA_size);
        TextView food_VB1 = findViewById(R.id.FRI_VB1_size);
        TextView food_VB2 = findViewById(R.id.FRI_VB2_size);
        TextView food_VB3 = findViewById(R.id.FRI_VB3_size);
        TextView food_VE = findViewById(R.id.FRI_VE_size);
        TextView food_VC = findViewById(R.id.FRI_VC_size);
        TextView food_Ga = findViewById(R.id.FRI_Ga_size);
        TextView food_Na = findViewById(R.id.FRI_Na_size);
        TextView food_Fe = findViewById(R.id.FRI_Fe_size);
        TextView food_Mg = findViewById(R.id.FRI_Mg_size);
        TextView food_P = findViewById(R.id.FRI_P_size);
        TextView food_Zn = findViewById(R.id.FRI_Zn_size);
        TextView food_K = findViewById(R.id.FRI_K_size);
        TextView food_purine = findViewById(R.id.FRI_purine_size);
//        获得传值
        Intent intent = getIntent();
        Bundle bundle_from_FRLV = intent.getExtras();
        if (bundle_from_FRLV != null) {
            item_id = bundle_from_FRLV.getString("itemId");
            userId = bundle_from_FRLV.getString("User_id");
            UIclass = bundle_from_FRLV.getString("intake_class");
            UIdate = bundle_from_FRLV.getString("intake_date");
            foodName = bundle_from_FRLV.getString("food_name");
            foodSize = bundle_from_FRLV.getString("itemSize");
            foodEnergy = bundle_from_FRLV.getString("itemEnergy");
            foodId = bundle_from_FRLV.getString("food_id");
        }
//        初始化界面
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        date.setText(UIdate);
//        餐别
        food_class.setText(UIclass);
//        定义单位用于更改记录、初始化百分比用于删除记录
        switch (foodRecordDao.getRecordUnit(item_id)) {
            case 0:
                percent = Float.parseFloat(numberFormat.format(Float.valueOf(foodSize) / 100));
                unitStr = "克";
                break;
            case 1:
                percent = Float.parseFloat(numberFormat.format(Float.valueOf(foodSize) / 100 * 106.4));
                unitStr = "个(小)";
                break;
            case 2:
                percent = Float.parseFloat(numberFormat.format(Float.valueOf(foodSize) / 100 * 159.6));
                unitStr = "个(中)";
                break;
            case 3:
                percent = Float.parseFloat(numberFormat.format(Float.valueOf(foodSize) / 100 * 288.8));
                unitStr = "个(大)";
                break;
        }
//      标题头 苹果XX个
        String fz = foodName + foodSize + unitStr;
        food_size.setText(fz);
//      标题头 XXX千焦能量
        food_nutrition.setText(foodEnergy);
//      能量列表
        foodDao = new FoodDao(this);
//      ①单品所有营养元素
        nutriArr = foodDao.findNutrition(foodId);
        for (int i = 0; i < 21; i++) {
            if (nutriArr[i] != null && !nutriArr[i].equals("…") && !nutriArr[i].equals("Tr")
                    && nutriArr[i].length() > 0 && !nutriArr[i].equals("—")
                    && !nutriArr[i].equals("┄") && !nutriArr[i].equals("─")) {
                nutrition[i] = numberFormat.format(Float.valueOf(nutriArr[i]) * percent) + unit[i];
            } else {
                nutrition[i] = "—";
            }
        }
        food_egy_size.setText(nutrition[0]);
        food_protein.setText(nutrition[1]);
        food_fat.setText(nutrition[2]);
        food_CH.setText(nutrition[3]);
        food_DF.setText(nutrition[4]);
        food_water.setText(nutrition[5]);
        food_CLS.setText(nutrition[6]);
        food_VA.setText(nutrition[7]);
        food_VB1.setText(nutrition[8]);
        food_VB2.setText(nutrition[9]);
        food_VB3.setText(nutrition[10]);
        food_VC.setText(nutrition[11]);
        food_VE.setText(nutrition[12]);
        food_Ga.setText(nutrition[13]);
        food_Na.setText(nutrition[14]);
        food_Fe.setText(nutrition[15]);
        food_Mg.setText(nutrition[16]);
        food_Zn.setText(nutrition[17]);
        food_P.setText(nutrition[18]);
        food_K.setText(nutrition[19]);
        food_purine.setText(nutrition[20]);
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
                sqLiteDatabase = dbHelper.getWritableDatabase();
                deleteRecord(foodName, userId, UIclass, UIdate);
                sqLiteDatabase.delete("UserFood", "_id=?", new String[]{item_id});
                Toast.makeText(this, "记录删除", Toast.LENGTH_SHORT).show();
                dbHelper.close();
                sqLiteDatabase.close();
                FoodRecordItem.this.finish();
                break;
            case R.id.changeRecord:
                cRec();
                break;
            default:
                break;
        }
        return true;
    }


    private void cRec() {
        if (view == null) {
            view = inflater.inflate(R.layout.record_change, null);
        }
        date_setup_c = view.findViewById(R.id.date_record_c);
        date_setup_c.setText(UIdate);
        changeRecDialog = new AlertDialog.Builder(this).create();
        changeRecDialog.setView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(changeRecDialog.getWindow()).setContentView(R.layout.dialog_record_add);
        }
        changeRecDialog.setCancelable(true);
        Button cancel = view.findViewById(R.id.change_concern);
        cancel.setOnClickListener(this);
        Button today = view.findViewById(R.id.date_today_c);
        today.setOnClickListener(this);
        Button dateChange_select = view.findViewById(R.id.date_select_c);
        dateChange_select.setOnClickListener(this);
        breakfast_0 = view.findViewById(R.id.fo_breakfast_c);
        lunch_1 = view.findViewById(R.id.fo_lunch_c);
        dinner_2 = view.findViewById(R.id.fo_dinner_c);
        befor_lunch_3 = view.findViewById(R.id.fo_beforeL_c);
        after_lunch_4 = view.findViewById(R.id.fo_afterL_c);
        any_time_5 = view.findViewById(R.id.fo_anytime_c);
        foodClassgroup = view.findViewById(R.id.fo_group_c);
        TextView sizeUnitTV = view.findViewById(R.id.size_unit_c);
//        文本框旁边的单位显示textView
        sizeUnitTV.setText(unitStr);
//        初始化餐别按钮
        FdClassicBtn();
        intakeSize = view.findViewById(R.id.food_size_c);
        intakeSize_str = view.findViewById(R.id.food_size_energy_c);
//        读取记录中的食物是多少克的 然后初始化editText
        intakeSize.setText(foodSize);
        String[] egy_change = foodDao.findNutrition(foodId);
        //        每100克能量
        unitEnergy = egy_change[0];
        //        Edittext旁边的string
        String initEnergy = "热量" + numberFormat
                .format(Float.valueOf(unitEnergy) * percent)
                .replace(",", "") + "千卡";
        intakeSize_str.setText(initEnergy);
//        设置餐量的时候计算单位能量的倍数，并且在TextView显示
        intakeSize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (intakeSize.getText().toString().trim().length() > 0) {
                    String intakeValue = intakeSize.getText().toString().trim().replace(",", "");
                    float fq = Float.valueOf(intakeValue);
                    String nf_per = null;
                    switch (unitStr) {
                        case "克":
//                            更改记录时的percent
                            nf_per = numberFormat.format(fq / 100);
                            break;
                        case "个(小)":
                            nf_per = numberFormat.format(fq / 100 * 106.4);
                            break;
                        case "个(中)":
                            nf_per = numberFormat.format(fq / 100 * 159.6);
                            break;
                        case "个(大)":
                            nf_per = numberFormat.format(fq / 100 * 288.8);
                            break;
                    }
                    String energy = numberFormat.format(Float.valueOf(nf_per) * Float.valueOf(unitEnergy));
                    String resultStr = "热量" + energy + "千卡";
                    intakeSize_str.setText(resultStr);
                } else intakeSize_str.setText("热量0千卡");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button change_agree = view.findViewById(R.id.change_agree);
        change_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intakeSize.getText().toString().trim().length() > 0) {
                    String changedate = date_setup_c.getText().toString().trim();
                    String changeSize = intakeSize.getText().toString().trim();
                    sqLiteDatabase = dbHelper.getWritableDatabase();
                    ContentValues UF_value = new ContentValues();
                    //更改用户饮食记录的信息
                    UF_value.put("Food_date", changedate);
                    UF_value.put("Food_class", fdClassic);
                    UF_value.put("Food_intake", changeSize);
                    //更改用户饮食记录
                    sqLiteDatabase.update("UserFood", UF_value, "_id =?", new String[]{item_id});
                    //要更改的饮食每100可所含营养
                    String[] changeNutri;
                    changeNutri = foodDao.findNutrition(foodId);
                    //计算更改的百分比，注释掉，使用全局百分比percent
//                    String percent = numberFormat.format(Float.valueOf(changeSize) / 100);
                    //每一项要更改的值
                    for (int i = 0; i < 21; i++) {
                        if (changeNutri[i] != null && !changeNutri[i].equals("…")
                                && !changeNutri[i].equals("Tr") && changeNutri[i].length() > 0
                                && !changeNutri[i].equals("—") && !changeNutri[i].equals("┄")
                                && !changeNutri[i].equals("─"))
                            changeNutri[i] = numberFormat.format(Float.valueOf(changeNutri[i]) * percent).replace(",", "");
                        else
                            changeNutri[i] = null;
                    }
                    //食物字典表对应顺序
                    String[] NutName = {"UI_energy", "UI_protein", "UI_fat", "UI_DF", "UI_CH", "UI_water", "UI_VA", "UI_VB1",
                            "UI_VB2", "UI_VB3", "UI_VE", "UI_VC", "UI_Fe", "UI_Ga", "UI_Na", "UI_CLS", "UI_K", "UI_Mg", "UI_Zn", "UI_P",
                            "UI_purine"};
                    ContentValues values = new ContentValues();
                    //更新营养摄入表1
                    try {
                        values.put("User_id", userId);
                        values.put("UI_date", changedate);
                        values.put("UI_class", fdClassic);
                        for (int i = 0; i < 21; i++) {
                            values.put(NutName[i], changeNutri[i]);
                        }
                        sqLiteDatabase.insertOrThrow("UserIntake", null, values);
                    } catch (SQLiteConstraintException ex) {
                        values.clear();
                        String[] userIntakedNutri;
                        //得到要更改到的目标约束内的目前数值
                        userIntakedNutri = userIntakeDao.getFromUserIntake(userId, fdClassic, changedate);
                        for (int i = 0; i < 21; i++) {
                            if (changeNutri[i] != null) {
                                if (userIntakedNutri[i] != null) {

                                    userIntakedNutri[i] = numberFormat.format(Float.valueOf(userIntakedNutri[i]) + Float.valueOf(changeNutri[i])).replace(",", "");
                                } else {
                                    userIntakedNutri[i] = changeNutri[i];
                                }
                            } else {
                                userIntakedNutri[i] = userIntakedNutri[i];
                            }
                            values.put(NutName[i], userIntakedNutri[i]);
                        }
                        sqLiteDatabase.update("UserIntake", values,
                                "User_id=? and UI_date=? and UI_class=?",
                                new String[]{userId, changedate, fdClassic});
                    } finally {
                        values.clear();
                        deleteRecord(foodName, userId, UIclass, UIdate);
                        UF_value.clear();
                        dbHelper.close();
                        sqLiteDatabase.close();
                    }
                    changeRecDialog.dismiss();
                    FoodRecordItem.this.finish();
                    Toast.makeText(FoodRecordItem.this, "修改成功" + changedate + "/" + fdClassic + "/" + changeSize, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FoodRecordItem.this, "餐量设置了吗？", Toast.LENGTH_SHORT).show();
                }
            }
        });
        changeRecDialog.show();
        view = null;
    }

    public void deleteRecord(String fruitName, String userId, String UIclass, String UIdate) {
        FoodDao foodDao = new FoodDao(this);
        ContentValues values2 = new ContentValues();
        String[] nutArray = new String[23];
        //字典表里的营养值
        nutArray[0] = foodDao.find_energy(fruitName);
        nutArray[1] = foodDao.find_protein(fruitName);
        nutArray[2] = foodDao.find_fat(fruitName);
        nutArray[3] = foodDao.find_DF(fruitName);
        nutArray[4] = foodDao.find_CH(fruitName);
        nutArray[5] = foodDao.find_water(fruitName);
        nutArray[6] = foodDao.find_vA(fruitName);
        nutArray[7] = foodDao.find_vB1(fruitName);
        nutArray[8] = foodDao.find_vB2(fruitName);
        nutArray[9] = foodDao.find_vB3(fruitName);
        nutArray[10] = foodDao.find_vE(fruitName);
        nutArray[11] = foodDao.find_vC(fruitName);
        nutArray[12] = foodDao.find_Fe(fruitName);
        nutArray[13] = foodDao.find_Ga(fruitName);
        nutArray[14] = foodDao.find_Na(fruitName);
        nutArray[15] = foodDao.find_CLS(fruitName);
        nutArray[16] = foodDao.find_K(fruitName);
        nutArray[17] = foodDao.find_Mg(fruitName);
        nutArray[18] = foodDao.find_Zn(fruitName);
        nutArray[19] = foodDao.find_P(fruitName);
        nutArray[20] = foodDao.find_purine(fruitName);
        //营养表里的营养值
        String[] intakeNutri = userIntakeDao.getFromUserIntake(userId, UIclass, UIdate);
//        删除计算的百分比，用于与单位能量做乘法
        float f_percent = percent;
        //临时赋值
        String result_ab;
        //营养表里的营养名称
        String[] NutName = {"UI_energy", "UI_protein", "UI_fat", "UI_DF", "UI_CH", "UI_water", "UI_VA", "UI_VB1",
                "UI_VB2", "UI_VB3", "UI_VE", "UI_VC", "UI_Fe", "UI_Ga", "UI_Na", "UI_CLS", "UI_K", "UI_Mg",
                "UI_Zn", "UI_P", "UI_purine"};
        //要减去的所有营养
        for (int i = 0; i < 21; i++) {
            if (nutArray[i] != null && !nutArray[i].equals("…") && !nutArray[i].equals("Tr") && nutArray[i].length() > 0 && !nutArray[i].equals("—") && !nutArray[i].equals("┄") && !nutArray[i].equals("─")) {
                nutArray[i] = numberFormat.format(Float.valueOf(nutArray[i]) * f_percent).replace(",", "");
                result_ab = numberFormat.format(Float.valueOf(intakeNutri[i]) - Float.valueOf(nutArray[i])).replace(",", "");
                values2.put(NutName[i], result_ab);
            }
        }
        sqLiteDatabase.update("UserIntake", values2, "User_id = ? and UI_date=? and UI_class=?", new String[]{userId, UIdate, UIclass});
        String[] afterUpdate = userIntakeDao.getFromUserIntake(userId, UIclass, UIdate);
        if (afterUpdate[0].equals("0")) {
            sqLiteDatabase.delete("UserIntake", "User_id=? and UI_date=? and UI_class=?", new String[]{userId, UIdate, UIclass});
        }
        values2.clear();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_concern:
                changeRecDialog.dismiss();
                break;
            case R.id.date_today_c:
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);
                break;
            case R.id.date_select_c:
                Message message1 = new Message();
                message1.what = 1;
                handler.sendMessage(message1);
                break;
        }
    }

    private void FdClassicBtn() {
        //        初始化类别按钮
        switch (UIclass) {
            case "早餐":
                fdClassic = UIclass;
                breakfast_0.setChecked(true);
                break;
            case "上午茶":
                fdClassic = UIclass;
                befor_lunch_3.setChecked(true);
                break;
            case "中餐":
                fdClassic = UIclass;
                lunch_1.setChecked(true);
                break;
            case "下午茶":
                fdClassic = UIclass;
                after_lunch_4.setChecked(true);
                break;
            case "晚餐":
                fdClassic = UIclass;
                dinner_2.setChecked(true);
                break;
            case "间点":
                fdClassic = UIclass;
                any_time_5.setChecked(true);
                break;
        }
        //更改餐别设置
        foodClassgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.fo_breakfast_c:
                        fdClassic = breakfast_0.getText().toString().trim();
                        break;
                    case R.id.fo_lunch_c:
                        fdClassic = lunch_1.getText().toString().trim();
                        break;
                    case R.id.fo_dinner_c:
                        fdClassic = dinner_2.getText().toString().trim();
                        break;
                    case R.id.fo_beforeL_c:
                        fdClassic = befor_lunch_3.getText().toString().trim();
                        break;
                    case R.id.fo_afterL_c:
                        fdClassic = after_lunch_4.getText().toString().trim();
                        break;
                    case R.id.fo_anytime_c:
                        fdClassic = any_time_5.getText().toString().trim();
                        break;
                }
            }
        });
    }

    private String initDate() {
        String date;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String zc_month;
        if (month <= 9) {
            zc_month = 0 + Integer.toString(month);
        } else zc_month = Integer.toString(month);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String zc_day;
        if (day <= 9) {
            zc_day = 0 + Integer.toString(day);
        } else zc_day = Integer.toString(day);
        date = year + "-" + zc_month + "-" + zc_day;
        return date;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                return new DatePickerDialog(this, dateChangeDialog, year, month, day);

        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dateChangeDialog = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String zc_year = Integer.toString(year);
            String zc_month;
            if (month < 9) {
                month = month + 1;
                zc_month = Integer.toString(month);
                zc_month = "0" + zc_month;
            } else {
                month = month + 1;
                zc_month = Integer.toString(month);
            }
            String zc_day;
            if (dayOfMonth < 10) {
                zc_day = Integer.toString(dayOfMonth);
                zc_day = "0" + zc_day;
            } else {
                zc_day = Integer.toString(dayOfMonth);
            }
            String food_rec = zc_year + "-" + zc_month + "-" + zc_day;
            date_setup_c.setText(food_rec);
            Toast.makeText(view.getContext(), food_rec, Toast.LENGTH_SHORT).show();
        }
    };

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    String initDate = initDate();
                    date_setup_c.setText(initDate);
                    Toast.makeText(FoodRecordItem.this, initDate, Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    showDialog(1);
                    break;
                default:
                    break;
            }
            return false;
        }
    });

}
