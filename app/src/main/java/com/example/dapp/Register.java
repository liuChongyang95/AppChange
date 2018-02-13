package com.example.dapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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


import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import Database.DBHelper;
import SearchDao.UserDao;
import Util.DrawUtil;
import View.DecimalScaleRulerView;
import View.ScaleRulerView;
import Util.Staticfinal_Value;

/**
 * Created by Administrator on 2018/1/6.
 */

public class Register extends AppCompatActivity {

    private UserDao userDao;

    private static final int DATE_PICKER_ID = 0;
    private EditText register_name;
    private EditText register_password;
    private EditText register_password2;
    private RadioGroup radioGroup_sex;
    private RadioButton radioButton_male;
    private RadioButton radioButton_female;
    private String sex;
    private String register_birth_str;//出生日期
    private TextView register_birth_tv;
    private DBHelper dbHelper;
    private String register_shape;
    private TextView register_career_tv;
    private String register_career_str;

    ScaleRulerView mHeightWheelView;
    TextView mHeightValue;
    DecimalScaleRulerView mWeightRulerView;
    TextView mWeightValueTwo;
    private float mHeight = 170;
    private float mWeight = 60;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
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
        setContentView(R.layout.register_app);
        Staticfinal_Value sfv = new Staticfinal_Value();
        userDao = new UserDao(this);
        dbHelper = new DBHelper(this, "DApp.db", null, sfv.staticVersion());

        mHeightValue = findViewById(R.id.tv_user_height_value);
        mWeightRulerView = findViewById(R.id.ruler_weight);
        mWeightValueTwo = findViewById(R.id.tv_user_weight_value_two);
        mHeightWheelView = findViewById(R.id.scaleWheelView_height);

        init();

        Toolbar toolbar = findViewById(R.id.register_toolBar);
        register_name = findViewById(R.id.register_name);
        register_password = findViewById(R.id.register_password);
        register_password2 = findViewById(R.id.register_password2);
        radioGroup_sex = findViewById(R.id.rg_sex);
        radioButton_male = findViewById(R.id.rb_male);
        radioButton_female = findViewById(R.id.rb_female);
        Button register_birth = findViewById(R.id.register_burn);
        register_birth_tv = findViewById(R.id.register_burn_tv);
        Button register_career = findViewById(R.id.register_career);
        register_career_tv = findViewById(R.id.register_career_tv);

        register_career.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Register.this, CareerList.class);
                startActivityForResult(intent, 2);
            }
        });


        selectedSex();
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register.this.finish();
            }
        });

        register_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_PICKER_ID);
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Random random = new Random();
        int i = random.nextInt(100000);
        int b = i + random.nextInt(54321);

        switch (item.getItemId()) {
            case R.id.register_sure:

                String register_name_str = register_name.getText().toString().trim();
                String register_password_str = register_password.getText().toString().trim();
                String register_password2_str = register_password2.getText().toString().trim();
                sex = selectedSex();
                register_birth_str = register_birth_tv.getText().toString().trim();
                double register_weight_str = mWeight;

                float register_tall_str = mHeight;
                float register_weight_str_amb = mHeight - 105;
                register_shape = getShape(register_weight_str, register_weight_str_amb);
                String register_intensity_str = userDao.getIntensity(register_career_str);


                if (register_password2_str.equals(register_password_str) && register_password2_str.length() > 0) {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.beginTransaction();
                    db.execSQL("PRAGMA foreign_keys=ON");

                    ContentValues values_User = new ContentValues();
                    ContentValues values_Login = new ContentValues();
                    values_User.put("User_id", i);
                    values_User.put("User_Nickname", "用户" + b);//昵称
                    values_User.put("User_Birth", register_birth_str);
                    values_User.put("User_Sex", sex);
                    values_User.put("User_Tall", register_tall_str);
                    values_User.put("User_Real_weight", register_weight_str);
                    values_User.put("User_Expect_weight", register_weight_str_amb);
                    values_User.put("Career", register_career_str);
                    values_User.put("User_Shape", register_shape);
                    Drawable apple = this.getResources().getDrawable(R.drawable.apple);
                    try {
                        values_User.put("User_Photo", dbHelper.getPicture(apple));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    values_User.put("User_Intensity", register_intensity_str);

//                    values_User.put("Record_time", record_time.getTime());

                    values_Login.put("Username", register_name_str);//用户名
                    values_Login.put("password", register_password_str);
                    values_Login.put("User_id", i);

                    try {
                        db.insertOrThrow("User", null, values_User);
                        db.insertOrThrow("Login", null, values_Login);
                        db.setTransactionSuccessful();
                        Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                        db.endTransaction();
                        db.close();
                        finish();
                    } catch (SQLiteConstraintException ex) {
                        db.endTransaction();
                        Toast.makeText(this, "用户名重复,注册失败", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "密码确认失败或密码不符合规范", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }


    private void init() {

        mWeightValueTwo.setText(mWeight + "kg");
        mHeightValue.setText((int) mHeight + "cm");
        float mMinHeight = 100;
        float mMaxHeight = 220;
        mHeightWheelView.initViewParam(mHeight, mMaxHeight, mMinHeight);
        mHeightWheelView.setValueChangeListener(new ScaleRulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                mHeightValue.setText((int) value + "cm");
                mHeight = value;
            }
        });
        mWeightRulerView.setParam(DrawUtil.dip2px(10), DrawUtil.dip2px(32), DrawUtil.dip2px(24),
                DrawUtil.dip2px(14), DrawUtil.dip2px(9), DrawUtil.dip2px(12));
        mWeightRulerView.initViewParam(mWeight, 20.0f, 200.0f, 1);
        mWeightRulerView.setValueChangeListener(new DecimalScaleRulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                mWeightValueTwo.setText((int) value + "kg");
                mWeight = value;
            }
        });
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:
                // onDateSetListener为用户点击设置时执行的回调函数，数字是默认显示的日期，
                // 注意月份设置11而实际显示12，会自动加1
                return new DatePickerDialog(this, onDateSetListener, 2011, 11, 12);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        // 第一个参数指整个DatePicker，第二个参数是当前设置的年份，
        // 第三个参数是当前设置的月份，注意的是，获取设置的月份时需要加1，因为java中规定月份在0~11之间

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // 通过Toast对话框输出当前设置的日期
            String zc_year = Integer.toString(year);
            String zc_month;
            if (monthOfYear < 9) {
                monthOfYear = monthOfYear + 1;
                zc_month = Integer.toString(monthOfYear);
                zc_month = "0" + zc_month;
            } else {
                monthOfYear = monthOfYear + 1;
                zc_month = Integer.toString(monthOfYear);
            }
            String zc_day;
            if (dayOfMonth < 10) {
                zc_day = Integer.toString(dayOfMonth);
                zc_day = "0" + zc_day;
            } else {
                zc_day = Integer.toString(dayOfMonth);

            }
            register_birth_str = zc_year + "-" + zc_month + "-" + zc_day;
            register_birth_tv.setText(register_birth_str);
            Toast.makeText(view.getContext(), register_birth_str, Toast.LENGTH_SHORT).show();
        }
    };

    private String selectedSex() {
        radioGroup_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioButton_male.getId() == i) {
                    sex = "男";

                }
                if (radioButton_female.getId() == i) {
                    sex = "女";
                }
            }
        });
        return sex;
    }


    public String getShape(double R_weight, double A_weight) {

        double rate = (R_weight - A_weight) / A_weight;
        if (rate <= -0.2) {
            register_shape = "消瘦";
        } else if (rate > -0.2 && rate < -0.1) {
            register_shape = "偏瘦";
        } else if (rate >= -0.1 && rate <= 0.1) {
            register_shape = "正常";
        } else if (rate > 0.1 && rate < 0.2) {
            register_shape = "超重";
        } else {
            register_shape = "肥胖";
        }
        return register_shape;
    }


    public int getAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException("The birthDay is before Now.It 's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth)
                    age--;
            } else {
                age--;
            }
        }
        return age;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:
                if (resultCode == RESULT_OK) {
                    register_career_str = data.getStringExtra("career_name");
                    register_career_tv.setText(register_career_str);
                }
                break;
            default:
        }
    }

}