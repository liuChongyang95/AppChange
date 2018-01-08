package com.example.dapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import Util.Fastblur;
import Util.DrawUtil;
import View.DecimalScaleRulerView;
import View.ScaleRulerView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/6.
 */

public class Register_main extends AppCompatActivity {
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
    private int sex_i;


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

        //模糊图片
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.warmbg);
//        Bitmap bitmap = bitmapDrawable.getBitmap();
//        RelativeLayout relativeLayout = findViewById(R.id.register_relative);
//        Drawable register_bg = setBlurBackground(bitmap);
//        relativeLayout.setBackground(register_bg);

    }

//    private Drawable setBlurBackground(Bitmap bmp) {
//        final Bitmap blurBmp = Fastblur.fastblur(Register_main.this, bmp, 20);//0-25，表示模糊值
//        final Drawable drawable = Register_main.getDrawable(Register_main.this, blurBmp);//将bitmap类型图片 转为 Drawable类型
//        return drawable;
//    }

//    //bitmap 转 drawable
//    public static Drawable getDrawable(Context context, Bitmap bm) {
//        BitmapDrawable bd = new BitmapDrawable(context.getResources(), bm);
//        return bd;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.register_sure:
                register_name_str = register_name.getText().toString().trim();
                register_password_str = register_password.getText().toString().trim();
                register_password2_str = register_password2.getText().toString().trim();
                sex = selectedSex();
                register_birth_str = register_birth_tv.getText().toString().trim();
                register_weight_str = mWeight;
                register_tall_str = mHeight;

                Log.d("Register_main", register_name_str + sex + register_password_str + register_password2_str +
                        register_birth_str + register_tall_str + register_weight_str);
                break;
        }
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
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

    private void init() {
        mHeightValue.setText((int) mHeight + "cm");
//        mWeightValue.setText(mWeight + "");
        mWeightValueTwo.setText(mWeight + "kg");

        mHeightWheelView.initViewParam(mHeight, mMaxHeight, mMinHeight);
        mHeightWheelView.setValueChangeListener(new ScaleRulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                mHeightValue.setText((int) value + "");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this);
    }
}