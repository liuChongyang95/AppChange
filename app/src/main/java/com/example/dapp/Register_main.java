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


import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import Database.DBHelper;
import SearchDao.UserDao;
import Util.DrawUtil;
import View.DecimalScaleRulerView;
import View.ScaleRulerView;
import butterknife.ButterKnife;
import Util.Staticfinal_Value;

/**
 * Created by Administrator on 2018/1/6.
 */

public class Register_main extends AppCompatActivity {

    private Staticfinal_Value sfv;
    private UserDao userDao;

    private static final int DATE_PICKER_ID = 0;
    private EditText register_name;
    private EditText register_password;
    private EditText register_password2;
    private String register_name_str;
    private String register_password_str;
    private String register_password2_str;
    private RadioGroup radioGroup_sex;
    private RadioButton radioButton_male;
    private RadioButton radioButton_female;
    private String sex;
    private Button register_birth;
    private String register_birth_str;//出生日期
    private TextView register_birth_tv;
    private String zc_year, zc_month, zc_day;
    private float register_tall_str;
    private float register_weight_str;
    private float register_weight_str_amb;
    private int sex_i;
    private Date register_birth_str_date;
    private DBHelper dbHelper;
    private String register_shape;
    private int register_age;
    private Button register_career;
    private TextView register_career_tv;
    private String register_career_str;
    private String register_intensity_str;
    private Date record_time;

    ScaleRulerView mHeightWheelView;
    TextView mHeightValue;
    ScaleRulerView mWeightWheelView;
    TextView mWeightValue;
    DecimalScaleRulerView mWeightRulerView;
    TextView mWeightValueTwo;
    Button btn_choosen_result;


    private float mHeight = 170;
    private float mMaxHeight = 220;
    private float mMinHeight = 100;


    private float mWeight = 60.0f;
    private float mMaxWeight = 200;
    private float mMinWeight = 25;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.register_app);
        sfv = new Staticfinal_Value();
        userDao=new UserDao(this);
        dbHelper = new DBHelper(this, "DApp.db", null, sfv.staticVersion());


        mHeightValue = findViewById(R.id.tv_user_height_value);
//        mWeightWheelView = findViewById(R.id.scaleWheelView_weight);
//        mWeightValue = findViewById(R.id.tv_user_weight_value);
        mWeightRulerView = findViewById(R.id.ruler_weight);
        mWeightValueTwo = findViewById(R.id.tv_user_weight_value_two);
        mHeightWheelView = findViewById(R.id.scaleWheelView_height);
//        btn_choosen_result = findViewById(R.id.btn_choose_result);

        ButterKnife.bind(this);  //依赖注入
        init();

//        if (savedInstanceState != null) {
//            register_name_str = savedInstanceState.getString("user_name_save");
//            register_password_str = savedInstanceState.getString("user_password_save");
//            register_password2_str = savedInstanceState.getString("user_password2_save");
//            sex = savedInstanceState.getString("user_sex_save");
//            register_birth_str = savedInstanceState.getString("user_birth_save");
//            int sex_selected = savedInstanceState.getInt("user_sex_i");
//            register_name.setText(register_name_str);
//            register_password2.setText(register_password2_str);
//            register_password.setText(register_password_str);
//            register_birth_tv.setText(register_birth_str);
//            radioGroup_sex.check(sex_selected);
//            Intent intent = getIntent();
//            Bundle bundle = intent.getExtras();
//            register_tall_str = bundle.getString("user_tall");
//            register_weight_str = bundle.getString("user_weight");
//        }
        Toolbar toolbar = findViewById(R.id.register_toolBar);
        register_name = findViewById(R.id.register_name);
        register_password = findViewById(R.id.register_password);
        register_password2 = findViewById(R.id.register_password2);
        radioGroup_sex = findViewById(R.id.rg_sex);
        radioButton_male = findViewById(R.id.rb_male);
        radioButton_female = findViewById(R.id.rb_female);
        register_birth = findViewById(R.id.register_burn);
        register_birth_tv = findViewById(R.id.register_burn_tv);
        register_career = findViewById(R.id.register_career);
        register_career_tv = findViewById(R.id.register_career_tv);

        register_career.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Register_main.this, Register_career.class);
                startActivityForResult(intent, 2);
            }
        });


        selectedSex();
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register_main.this.finish();
            }
        });

        register_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_PICKER_ID);
            }
        });

//        模糊图片
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.warmbg);
//        Bitmap bitmap = bitmapDrawable.getBitmap();
//        RelativeLayout relativeLayout = findViewById(R.id.register_relative);
//        Drawable register_bg = setBlurBackground(bitmap);
//        relativeLayout.setBackground(register_bg);
//
    }
//
//    private Drawable setBlurBackground(Bitmap bmp) {
//        final Bitmap blurBmp = Fastblur.fastblur(Register_main.this, bmp, 20);//0-25，表示模糊值
//        final Drawable drawable = Register_main.getDrawable(Register_main.this, blurBmp);//将bitmap类型图片 转为 Drawable类型
//        return drawable;
//    }
//
//    //bitmap 转 drawable
//    public static Drawable getDrawable(Context context, Bitmap bm) {
//        BitmapDrawable bd = new BitmapDrawable(context.getResources(), bm);
//        return bd;    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Random random = new Random();
        int i = random.nextInt(100000);
        int b = i + random.nextInt(54321);

        switch (item.getItemId()) {
            case R.id.register_sure:

                register_name_str = register_name.getText().toString().trim();
                register_password_str = register_password.getText().toString().trim();
                register_password2_str = register_password2.getText().toString().trim();
                sex = selectedSex();
                register_birth_str = register_birth_tv.getText().toString().trim();
                register_weight_str = mWeight;
                register_tall_str = mHeight;
                register_weight_str_amb = mHeight - 105;
//                try {
//                    register_age = getAge(register_birth_str_date);//计算年龄
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                register_shape = getShape(register_weight_str, register_weight_str_amb);
                register_intensity_str = userDao.getIntensity(register_career_str, register_shape);


                if (register_password2_str.equals(register_password_str) && register_password2_str != null) {
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
                    Drawable apple = this.getResources().getDrawable(R.drawable.apple);
                    values_User.put("User_Photo", dbHelper.getPicture(apple));
                    values_User.put("User_Shape", register_shape);
                    values_User.put("User_Intensity", register_intensity_str);


//                    values_User.put("Record_time", record_time.getTime());


                    values_Login.put("Username", register_name_str);//用户名
                    values_Login.put("password", register_password_str);
                    values_Login.put("User_id", i);

                    try {
                        db.insertOrThrow("User", null, values_User);
                        db.insertOrThrow("Login", null, values_Login);
                        db.setTransactionSuccessful();
                        Toast.makeText(Register_main.this, "注册成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (SQLiteConstraintException ex) {
                        Toast.makeText(this, "用户名重复,注册失败", Toast.LENGTH_LONG).show();
                    } finally {
                        db.endTransaction();
                    }
                } else {
                    Toast.makeText(this, "密码确认失败或密码不符合规范", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }


    private void init() {
        mHeightValue.setText((int) mHeight + "cm");
//        mWeightValue.setText(mWeight + "");
        mWeightValueTwo.setText(mWeight + "kg");

        mHeightWheelView.initViewParam(mHeight, mMaxHeight, mMinHeight);
        mHeightWheelView.setValueChangeListener(new ScaleRulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                mHeightValue.setText((int) value + "cm");
                mHeight = value;
            }
        });
//        mWeightWheelView.initViewParam(mWeight, mMaxWeight, mMinWeight);
//        mWeightWheelView.setValueChangeListener(new ScaleRulerView.OnValueChangeListener() {
//            @Override
//            public void onValueChange(float value) {
//                mWeightValue.setText(value + "");
//                mWeight = value;
//            }
//        });
        mWeightRulerView.setParam(DrawUtil.dip2px(10), DrawUtil.dip2px(32), DrawUtil.dip2px(24),
                DrawUtil.dip2px(14), DrawUtil.dip2px(9), DrawUtil.dip2px(12));
        mWeightRulerView.initViewParam(mWeight, 20.0f, 200.0f, 1);
        mWeightRulerView.setValueChangeListener(new DecimalScaleRulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                mWeightValueTwo.setText(value + "kg");
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
            zc_year = Integer.toString(year);
            if (monthOfYear < 9) {
                monthOfYear = monthOfYear + 1;
                zc_month = Integer.toString(monthOfYear);
                zc_month = "0" + zc_month;
            } else {
                monthOfYear = monthOfYear + 1;
                zc_month = Integer.toString(monthOfYear);
            }
            if (dayOfMonth < 10) {
                zc_day = Integer.toString(dayOfMonth);
                zc_day = "0" + zc_day;
            } else {
                zc_day = Integer.toString(dayOfMonth);

            }
            register_birth_str = zc_year + "-" + zc_month + "-" + zc_day;
            register_birth_tv.setText(register_birth_str);
//            register_birth_str_date=Timestamp.valueOf( register_birth_str);
            register_birth_str_date = java.sql.Date.valueOf(register_birth_str);
            Toast.makeText(view.getContext(), register_birth_str, Toast.LENGTH_SHORT).show();
        }
    };

    private String selectedSex() {
        radioGroup_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioButton_male.getId() == i) {
                    sex = "男";
                    sex_i = radioButton_male.getId();
                }
                if (radioButton_female.getId() == i) {
                    sex = "女";
                    sex_i = radioButton_female.getId();
                }
            }
        });
        return sex;
    }

    //    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        String name = register_name.getText().toString().trim();
//        String password = register_password.getText().toString().trim();
//        String password2 = register_password2.getText().toString().trim();
//        sex = selectedSex();
//        String birth = register_birth_tv.getText().toString().trim();
//        int sex_selected = sex_i;
//        outState.putString("user_name_save", name);
//        outState.putString("user_password_save", password);
//        outState.putString("user_password2_save", password2);
//        outState.putString("user_sex_save", sex);
//        outState.putString("user_birth_save", birth);
//        outState.putInt("user_sex_i", sex_selected);
//
//    }

    public String getShape(double R_weight, double A_weight) {
//        tall = tall / 100;
//        double BMI = weight / (tall * tall);
//
//        if (BMI < 18.5) {
//            register_shape = "太轻";
//        } else if (BMI >= 18.5 && BMI <= 25) {
//            register_shape = "正常";
//        } else if (BMI > 25 && BMI < 28) {
//            register_shape = "过重";
//        } else if (BMI >= 28 && BMI <= 32) {
//            register_shape = "肥胖";
//        } else {
//            register_shape = "非常肥胖";
//        }
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


    public int getAge(Date birthDay) throws Exception {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this);
    }
}