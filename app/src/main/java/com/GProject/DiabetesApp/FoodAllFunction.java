package com.GProject.DiabetesApp;
/*
bundle里面有ID和用户名

*/

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import JavaBean.PassValueUtil;
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
    private SimpleDateFormat simpleDateFormat;
    private int year;
    private int month;
    //    日期组
    private List<String> result;

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
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
        View rootView = LayoutInflater.from(this).inflate(R.layout.food_all_function, null);
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
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
                Toast.makeText(this, "点击日期，跳转到相应页面", Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(FoodAllFunction.this);
                View dialogView = LayoutInflater.from(FoodAllFunction.this).inflate(R.layout.datepicker_aige, null);
                final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
                datePicker.setDate(year, month + 1);
                datePicker.setMode(DPMode.SINGLE);
                datePicker.setTodayDisplay(true);
                datePicker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
                    @Override
                    public void onDatePicked(String date) {
                        Toast.makeText(FoodAllFunction.this, date, Toast.LENGTH_LONG).show();
                        Intent intent1 = new Intent();
                        intent1.setClass(FoodAllFunction.this, DietaryStatus.class);
//                                日期格式转换
                        try {
                            bundle_from_FMA.putString("pick_Type","Single");
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
                Toast.makeText(this, "选择要查看的日期，点击右上角确定", Toast.LENGTH_LONG).show();
                final AlertDialog.Builder listDialog = new AlertDialog.Builder(FoodAllFunction.this);
                View dialogView_list = LayoutInflater.from(this).inflate(R.layout.datepicker_aige, null);
                final DatePicker datePicker1 = dialogView_list.findViewById(R.id.datePicker);
                datePicker1.setDate(year, month + 1);
                datePicker1.setTodayDisplay(true);
                datePicker1.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(List<String> date) {
                        result = new ArrayList<>();
                        Iterator iterator = date.iterator();
                        while (iterator.hasNext()) {
                            try {
                                result.add(simpleDateFormat.format(simpleDateFormat.parse(iterator.next().toString())));
//                              Bundle不能携带大量数据，考虑可能替换不用bundle携带日期数据
                            } catch (ParseException e) {
                                Log.i("FAF.class", "选择日期模块，功能出现问题");
                                e.printStackTrace();
                            }
                        }
                        PassValueUtil passValueUtil=new PassValueUtil();
                        passValueUtil.setDatepickList(result);
                        Intent intent=new Intent(FoodAllFunction.this,DietaryStatus.class);
                        bundle_from_FMA.putString("pick_Type","Various");
                        bundle_from_FMA.putSerializable("dateList",passValueUtil);
                        intent.putExtras(bundle_from_FMA);
                        startActivity(intent);
                    }
                });
                listDialog.setView(dialogView_list);
                listDialog.show();
                popupWindow.dismiss();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
