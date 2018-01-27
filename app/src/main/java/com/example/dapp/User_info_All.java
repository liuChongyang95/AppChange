package com.example.dapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import SearchDao.UserDao;
import Util.Fastblur;
import de.hdodenhof.circleimageview.CircleImageView;


public class User_info_All extends AppCompatActivity implements View.OnClickListener {
    private String get_edit_ID;
    TextView edit_user_ID;
    TextView edit_user_nickname;
    TextView edit_user_loginName;
    TextView edit_user_sex;
    TextView edit_user_birth;
    TextView edit_user_tall;
    TextView edit_user_weight;
    TextView edit_user_career;
    TextView edit_user_intensity;
    TextView edit_user_shape;
    TextView edit_user_weight_expect;
    CircleImageView edit_user_photo;
    String get_edit_LoginName;

    private UserDao userDao = new UserDao(this);

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.user_info_edit);
        RelativeLayout all_edit = findViewById(R.id.relative_all_edit);
        Toolbar toolbar = findViewById(R.id.user_info_edit_toolbar);
        edit_user_photo = findViewById(R.id.user_info_edit_photo);
        edit_user_ID = findViewById(R.id.user_info_medical_id);
        edit_user_nickname = findViewById(R.id.user_info_edit_nickname);
        edit_user_loginName = findViewById(R.id.user_info_login_name);
        edit_user_sex = findViewById(R.id.user_info_edit_sex);
        edit_user_birth = findViewById(R.id.user_info_edit_birth);
        edit_user_tall = findViewById(R.id.user_info_edit_tall);
        edit_user_weight = findViewById(R.id.user_info_edit_weight);
        edit_user_career = findViewById(R.id.user_info_edit_career);
        edit_user_intensity = findViewById(R.id.user_info_intensity);
        edit_user_shape = findViewById(R.id.user_info_shape);
        edit_user_weight_expect = findViewById(R.id.user_info_Expect_weight);


        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User_info_All.this.finish();
            }
        });
        Intent intent = getIntent();
        Bundle bundle_from_MA = intent.getExtras();
        get_edit_ID = bundle_from_MA.getString("from_Login_User_id");
        get_edit_LoginName = bundle_from_MA.getString("from_Login_User_Username");
        UserDao userDao = new UserDao(this);
        edit_user_photo.setImageDrawable(userDao.getUser_Photo(get_edit_ID));
        edit_user_ID.setText(get_edit_ID);
        edit_user_nickname.setText(userDao.getNickname(get_edit_ID));
        edit_user_loginName.setText(get_edit_LoginName);
        edit_user_sex.setText(userDao.getSex(get_edit_ID));
        edit_user_birth.setText(userDao.getBirth(get_edit_ID));
        edit_user_tall.setText(userDao.getTall(get_edit_ID) + "cm");
        edit_user_weight.setText(userDao.getWeight(get_edit_ID) + "kg");
        edit_user_weight_expect.setText(userDao.getExpect_weight(get_edit_ID) + "kg");
        edit_user_career.setText(userDao.getCareer(get_edit_ID));
        edit_user_shape.setText(userDao.getShape(get_edit_ID));
        edit_user_intensity.setText(userDao.getIntensity(userDao.getCareer(get_edit_ID), userDao.getShape(get_edit_ID)));

        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.black);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Drawable register_bg = setBlurBackground(bitmap);
        all_edit.setBackground(register_bg);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Drawable setBlurBackground(Bitmap bmp) {
        final Bitmap blurBmp = Fastblur.fastblur(User_info_All.this, bmp, 13);//0-25，表示模糊值
        final Drawable drawable_bg = MainAll.getDrawable(User_info_All.this, blurBmp);//将bitmap类型图片 转为 Drawable类型
        return drawable_bg;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_info_LL_nickname:
                final EditText editText = new EditText(User_info_All.this);
                editText.setHint("点击输入");
                final AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(this);
                inputDialog.setTitle("修改昵称").setView(editText, 50, 0, 50, 0);
                inputDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                inputDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        onResume();
                    }
                });
                inputDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (editText.length() != 0)
                            userDao.changNickname(get_edit_ID, editText.getText().toString());
                        else dialog.dismiss();
                    }
                }).show().setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        showKeyboard(editText);
                    }
                });
                break;
        }
    }

    public void showKeyboard(EditText editText) {
        if (editText != null) {
            //设置可获得焦点
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            //请求获得焦点
            editText.requestFocus();
            //调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        edit_user_photo.setImageDrawable(userDao.getUser_Photo(get_edit_ID));
        edit_user_nickname.setText(userDao.getNickname(get_edit_ID));
        edit_user_sex.setText(userDao.getSex(get_edit_ID));
        edit_user_birth.setText(userDao.getBirth(get_edit_ID));
        edit_user_tall.setText(userDao.getTall(get_edit_ID) + "cm");
        edit_user_weight.setText(userDao.getWeight(get_edit_ID) + "kg");
        edit_user_weight_expect.setText(userDao.getExpect_weight(get_edit_ID) + "kg");
        edit_user_career.setText(userDao.getCareer(get_edit_ID));
        edit_user_shape.setText(userDao.getShape(get_edit_ID));
        edit_user_intensity.setText(userDao.getIntensity(userDao.getCareer(get_edit_ID), userDao.getShape(get_edit_ID)));
    }


}
