//package com.example.dapp;
//
///**
// * Created by Administrator on 2018/1/8.
// */
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import Util.DrawUtil;
//import View.DecimalScaleRulerView;
//import View.ScaleRulerView;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
///**
// * Created by zoubo on 16/3/16.
// * 横向滚动刻度尺测试类
// */
//
//public class ScaleRuler_tall_weight extends AppCompatActivity {
//
//
//    ScaleRulerView mHeightWheelView;
//    TextView mHeightValue;
//    ScaleRulerView mWeightWheelView;
//    TextView mWeightValue;
//    DecimalScaleRulerView mWeightRulerView;
//    TextView mWeightValueTwo;
//    Button btn_choosen_result;
//
//
//    private float mHeight = 170;
//    private float mMaxHeight = 220;
//    private float mMinHeight = 100;
//
//
//    private float mWeight = 60.0f;
//    private float mMaxWeight = 200;
//    private float mMinWeight = 25;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.tall_weight_select);
//        mHeightValue = findViewById(R.id.tv_user_height_value);
////        mWeightWheelView = findViewById(R.id.scaleWheelView_weight);
////        mWeightValue = findViewById(R.id.tv_user_weight_value);
//        mWeightRulerView = findViewById(R.id.ruler_weight);
//        mWeightValueTwo = findViewById(R.id.tv_user_weight_value_two);
//        mHeightWheelView = findViewById(R.id.scaleWheelView_height);
//        btn_choosen_result = findViewById(R.id.btn_choose_result);
//
//        ButterKnife.bind(this);  //依赖注入
//        init();
//
//        btn_choosen_result.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(ScaleRuler_tall_weight.this, "选择身高： " + mHeight + " 体重： " + mWeight, Toast
//                        .LENGTH_LONG).show();
//                Intent intent = new Intent(ScaleRuler_tall_weight.this, Register_main.class);
//                Bundle bundle_tall_weight = new Bundle();
//                bundle_tall_weight.putString("user_tall", String.valueOf(mHeight));
//                bundle_tall_weight.putString("user_weight", String.valueOf(mWeight));
//                intent.putExtras(bundle_tall_weight);
//                startActivity(intent);
//                finish();
//            }
//        });
//    }
//
//    private void init() {
//        mHeightValue.setText((int) mHeight + "");
////        mWeightValue.setText(mWeight + "");
//        mWeightValueTwo.setText(mWeight + "kg");
//
//
//        mHeightWheelView.initViewParam(mHeight, mMaxHeight, mMinHeight);
//        mHeightWheelView.setValueChangeListener(new ScaleRulerView.OnValueChangeListener() {
//            @Override
//            public void onValueChange(float value) {
//                mHeightValue.setText((int) value + "");
//                mHeight = value;
//            }
//        });
////        mWeightWheelView.initViewParam(mWeight, mMaxWeight, mMinWeight);
////        mWeightWheelView.setValueChangeListener(new ScaleRulerView.OnValueChangeListener() {
////            @Override
////            public void onValueChange(float value) {
////                mWeightValue.setText(value + "");
////
////                mWeight = value;
////            }
////        });
//        mWeightRulerView.setParam(DrawUtil.dip2px(10), DrawUtil.dip2px(32), DrawUtil.dip2px(24),
//                DrawUtil.dip2px(14), DrawUtil.dip2px(9), DrawUtil.dip2px(12));
//        mWeightRulerView.initViewParam(mWeight, 20.0f, 200.0f, 1);
//        mWeightRulerView.setValueChangeListener(new DecimalScaleRulerView.OnValueChangeListener() {
//            @Override
//            public void onValueChange(float value) {
//                mWeightValueTwo.setText(value + "kg");
//
//                mWeight = value;
//            }
//        });
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        ButterKnife.bind(this);
//    }
//}

//
//import java.util.Calendar;
//import java.util.Date;
//
//public class DateGetAge
//{
//
//    public static int getAge(Date birthDay) throws Exception
//
//    {
//
//        Calendar cal = Calendar.getInstance();
//
//
//        if (cal.before(birthDay)) {
//
//            throw new
//                            IllegalArgumentException("The birthDay is before Now.It 's unbelievable!");
//
//        }
//
//        int
//
//                yearNow = cal.get(Calendar.YEAR);
//
//        int
//
//                monthNow = cal.get(Calendar.MONTH);
//
//        int
//
//                dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
//
//        cal.setTime(birthDay);
//
//
//        int
//
//                yearBirth = cal.get(Calendar.YEAR);
//
//        int
//
//                monthBirth = cal.get(Calendar.MONTH);
//
//        int
//
//                dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
//
//
//        int
//
//                age = yearNow - yearBirth;
//
//
//        if
//
//                (monthNow <= monthBirth)
//
//        {
//
//            if
//
//                    (monthNow == monthBirth)
//
//            {
//
//                if
//
//                        (dayOfMonthNow < dayOfMonthBirth)
//
//                    age--;
//
//            } else
//
//            {
//
//                age--;
//
//            }
//
//        }
//
//        return
//
//                age;
//
//    }
//
//}
