package com.GProject.DiabetesApp;
/*
bundle里面有ID和用户名

*/

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Util.AnimateUtil;
import Util.SlideLayout;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

public class FoodAllFunction extends AppCompatActivity implements View.OnClickListener {
    private String[] record_item = {"分析报告", "记录修改", "饮食情况"};
    private int[] record_pic = {R.drawable.analysis, R.drawable.list, R.drawable.data_usage};
    private List<Map<String, Object>> record_list;
    private Toolbar toolbar;
    private GridView gridView;
    private SimpleAdapter sim_adapter;
    private LinearLayout add_food_LL;
    private PopupWindow popupWindow;
    private Bundle bundle_from_FMA;
    private int year;
    private int month;
    //    调用背景变暗
    private float bgAlpha = 1f;
    private boolean bright = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
//        背景色
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
        setContentView(R.layout.food_all_function);
        Intent intent = getIntent();
        bundle_from_FMA = intent.getExtras();
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        new SlideLayout(this).bind();
        toolbar = findViewById(R.id.food_record_all_toolbar);
        gridView = findViewById(R.id.foodRecord_gridView);
        add_food_LL = findViewById(R.id.add_food_record_LL);
        record_list = new ArrayList<>();
        getData();
        String[] from = {"image_record", "text_record"};
        int[] to = {R.id.image_record, R.id.text_record};
        sim_adapter = new SimpleAdapter(this, record_list, R.layout.record_all_item, from, to);
        gridView.setAdapter(sim_adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
//                        饮食报告
                        Intent intent2 = new Intent(FoodAllFunction.this, FoodReport.class);
                        intent2.putExtras(bundle_from_FMA);
                        startActivity(intent2);
                        break;
                    case 1:
//                        饮食列表
                        Intent intent = new Intent();
                        intent.setClass(FoodAllFunction.this, FoodRecordListView.class);
                        intent.putExtras(bundle_from_FMA);
                        startActivity(intent);
                        break;
                    case 2:
                        showPopupWindow();
//                        饮食情况

                        break;
                }
            }
        });
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodAllFunction.this.finish();
            }
        });
    }

    //    用于选择信息筛选模式
    private void showPopupWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.date_popupwindow, null);
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.style.MyPopupWindow_anim_style);
        TextView single = contentView.findViewById(R.id.chooseSingle);
        TextView list = contentView.findViewById(R.id.chooseList);
        single.setOnClickListener(this);
        list.setOnClickListener(this);
        View rootView=LayoutInflater.from(this).inflate(R.layout.food_all_function,null);
        popupWindow.showAtLocation(rootView, Gravity.CENTER,0,0);
        if (popupWindow != null && popupWindow.isShowing()) {
            toggleBright();
        }
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                toggleBright();
            }
        });
    }

    private List<Map<String, Object>> getData() {
        for (int i = 0; i < record_pic.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image_record", record_pic[i]);
            map.put("text_record", record_item[i]);
            record_list.add(map);
        }
        return record_list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_food_record_LL:
                Intent intent1 = new Intent(FoodAllFunction.this, FoodSearchToAdd.class);
                intent1.putExtras(bundle_from_FMA);
                startActivity(intent1);
                break;
            case R.id.chooseSingle:
                final AlertDialog.Builder builder = new AlertDialog.Builder(FoodAllFunction.this);
                View dialogView = LayoutInflater.from(FoodAllFunction.this).inflate(R.layout.datepicker_aige, null);
                final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
                datePicker.setDate(year, month + 1);
                datePicker.setMode(DPMode.SINGLE);
                datePicker.setTodayDisplay(true);
                datePicker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
                    @Override
                    public void onDatePicked(String date) {
                        Toast.makeText(FoodAllFunction.this, date, Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent();
                        intent1.setClass(FoodAllFunction.this, DietaryStatus.class);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                                日期格式转换
                        try {
                            bundle_from_FMA.putString("pick_Time", simpleDateFormat.format(simpleDateFormat.parse(date)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            Log.d("FAF", "picktime has problem");
                        }
                        intent1.putExtras(bundle_from_FMA);
                        startActivity(intent1);
                    }
                });
                builder.setView(dialogView);
                builder.show();
                popupWindow.dismiss();
                break;
            case R.id.chooseList:
                final AlertDialog.Builder listDialog=new AlertDialog.Builder(FoodAllFunction.this);
                View dialogView_list=LayoutInflater.from(this).inflate(R.layout.datepicker_aige,null);
                final DatePicker datePicker1=dialogView_list.findViewById(R.id.datePicker);
                datePicker1.setDate(year,month+1);
                datePicker1.setTodayDisplay(true);
                datePicker1.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(List<String> date) {

                    }
                });
                listDialog.setView(dialogView_list);
                listDialog.show();
                popupWindow.dismiss();
                break;
        }
    }
    private void toggleBright() {
        //        变暗动画
        AnimateUtil animateUtil = new AnimateUtil();
        //三个参数分别为： 起始值 结束值 时长  那么整个动画回调过来的值就是从0.5f--1f的
        animateUtil.setValueAnimator(0.5f, 1f, 350);
        animateUtil.addUpdateListener(new AnimateUtil.UpdateListener() {
            @Override
            public void progress(float progress) {
                //此处系统会根据上述三个值，计算每次回调的值是多少，我们根据这个值来改变透明度
                bgAlpha = bright ? progress : (1.5f - progress);//三目运算，应该挺好懂的。
//                背景变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = bgAlpha; //0.0-1.0
                getWindow().setAttributes(lp);
//                注销。因为可能和自带的变暗效果冲突
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });
        animateUtil.addEndListner(new AnimateUtil.EndListener() {
            @Override
            public void endUpdate(Animator animator) {
                //在一次动画结束的时候，翻转状态
                bright = !bright;
            }
        });
        animateUtil.startAnimator();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("FAF", "onDestroy: ");
    }
}
