package com.example.dapp;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import Fragment_fs.Fragment_FS_GI;
import Fragment_fs.Fragment_FS_nutritioninfo;
import Fragment_fs.Fragment_FS_titalinfo;
import SearchDao.CareerDao;
import SearchDao.FoodDao;
import SearchDao.UserDao;


/**
 * Created by Administrator on 2017/12/28.
 */

public class FruitSelect extends AppCompatActivity implements View.OnClickListener {
    private String fruitName;
    private String Energy;

    private Bundle bundle_from_FMA;
    private CareerDao careerDao = new CareerDao(this);
    private FoodDao foodDao = new FoodDao(this);
    private UserDao userDao = new UserDao(this);

    private LayoutInflater mInflater;
    private AlertDialog Add_dialog;
    private TextView date_setup;
//    private Handler handler;弱引用

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.fruit_message);
        mInflater = LayoutInflater.from(this);

        LinearLayout VS_nutri_LL = findViewById(R.id.VS_LL);
        VS_nutri_LL.setOnClickListener(this);
        LinearLayout ADD_LL = findViewById(R.id.Add_LL);

        Toolbar toolbar = findViewById(R.id.toolBar_fS);
        toolbar.getBackground().setAlpha(0);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FruitSelect.this.finish();
            }
        });

        Intent intent = getIntent();
        bundle_from_FMA = intent.getExtras();
        fruitName = bundle_from_FMA.getString("fruit_name");
        TextView fruitNameText = findViewById(R.id.searchResult_title);
        fruitNameText.setText(fruitName);
        food_title();
        food_nutrition();
        food_gi();

    }

    private void food_title() {
        Energy = foodDao.find_energy(fruitName);
        Fragment_FS_titalinfo fragment_fs_titalinfo = (Fragment_FS_titalinfo) getSupportFragmentManager().findFragmentById(R.id.title_fragment);
        fragment_fs_titalinfo.loading_title(fruitName, Energy);
    }

    private void food_nutrition() {
        Energy = foodDao.find_energy(fruitName) + "千卡";
        String Protein = foodDao.find_protein(fruitName) + "克";
        String Fat = foodDao.find_fat(fruitName) + "克";
        String DF = foodDao.find_DF(fruitName) + "克";
        String CH = foodDao.find_CH(fruitName) + "克";
        Fragment_FS_nutritioninfo fsNutritioninfo = (Fragment_FS_nutritioninfo) getSupportFragmentManager().findFragmentById(R.id.nutrition_fragment);
        fsNutritioninfo.loading_nutrition(Energy, Protein, CH, DF, Fat);
        fsNutritioninfo.setArguments(bundle_from_FMA);
    }

    private void food_gi() {
        String GI = foodDao.find_GI(fruitName);
        String GI_per = foodDao.find_GI_per(fruitName);
        Fragment_FS_GI fragment_fs_gi = (Fragment_FS_GI) getSupportFragmentManager().findFragmentById(R.id.gi_fragment);
        fragment_fs_gi.loading_GI(GI, GI_per);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.VS_LL:
                VS();
                break;
            case R.id.Add_LL:
                ADD_Rec();
                break;
        }
    }

    private void VS() {
        final EditText VS_editText = new EditText(FruitSelect.this);
        VS_editText.setHint("点击输入");
        VS_editText.setWidth(100);
        VS_editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        final AlertDialog.Builder VS_input = new AlertDialog.Builder(this);
        VS_input.setTitle("输入试算量(克)").setView(VS_editText);
        VS_input.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        VS_input.setPositiveButton("试算", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (VS_editText.length() > 0) {
                    int quantity = Integer.parseInt(VS_editText.getText().toString().trim());
                    Intent intent = new Intent(FruitSelect.this, Nutrition_Test.class);
                    bundle_from_FMA.putInt("food_quantity", quantity);
                    intent.putExtras(bundle_from_FMA);
                    startActivity(intent);
                }
            }
        });
        VS_input.show();
    }

    private void ADD_Rec() {
        Add_dialog = new AlertDialog.Builder(this).create();
        View add_view = mInflater.inflate(R.layout.add_record, null);
        Add_dialog.setView(add_view);
        Add_dialog.getWindow().setContentView(R.layout.add_record);
        Button cancel = add_view.findViewById(R.id.add_concern);
        date_setup = add_view.findViewById(R.id.date_record);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_dialog.dismiss();
            }
        });

        Button today = add_view.findViewById(R.id.date_today);
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                handler = new MyHandler(FruitSelect.this);弱引用
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);
            }
        });
        Button date_select = add_view.findViewById(R.id.date_select);
        date_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        });
        Add_dialog.show();
    }


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            String date;
            switch (msg.what) {
                case 0:
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
                    date_setup.setText(date);
                    Toast.makeText(FruitSelect.this, date, Toast.LENGTH_SHORT).show();
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

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                return new DatePickerDialog(this, foodRecDialog, year, month, day);
        }
        return null;

    }

    private DatePickerDialog.OnDateSetListener foodRecDialog = new DatePickerDialog.OnDateSetListener() {
        @Override
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
            String food_rec = zc_year + "-" + zc_month + "-" + zc_day;
            date_setup.setText(food_rec);
            Toast.makeText(view.getContext(), food_rec, Toast.LENGTH_SHORT).show();
        }
    };

//弱引用
//    private static class MyHandler extends Handler {
//        WeakReference<FruitSelect> fruitSelectWeakReference;
//
//        MyHandler(FruitSelect fruitSelect) {
//            fruitSelectWeakReference = new WeakReference<>(fruitSelect);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            FruitSelect fruitSelect = fruitSelectWeakReference.get();
//            if (fruitSelect != null) {
//                String date;
//                switch (msg.what) {
//                    case 0:
//                        Calendar calendar = Calendar.getInstance();
//                        int year = calendar.get(Calendar.YEAR);
//                        int month = calendar.get(Calendar.MONTH) + 1;
//                        int day = calendar.get(Calendar.DAY_OF_MONTH);
//                        date = year + "-" + month + "-" + day;
//                        fruitSelect.date_setup.setText(date);
//                        break;
//                    case 1:
//
//                        break;
//                    default:
//                        break;
//                }
//            }
//        }
//    }
}

