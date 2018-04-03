package com.example.dapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import SearchDao.UserDao;
import Util.Staticfinal_Value;

/**
 * Created by Administrator on 2018/1/2.
 * 2018/4/3 登录跳转有问题
 */

public class Login extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private String username_str;
    private String password_str;
    private TextView register;
    private Button login;
    private UserDao userDao;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPass;
    private String intent_Userid;
    private Boolean flag;
    private Button back2App;
    private Button cancelUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        //保存基本信息，SharedPreferences键值方式保存信息
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        //取之前的值，是否勾选
        boolean isRemember = pref.getBoolean("remember_password", false);
        userDao = new UserDao(Login.this);
        if (isRemember) {
            username_str = pref.getString("username_pref", "");
            password_str = pref.getString("password_pref", "");
            flag = userDao.login(username_str, password_str);
            intent_Userid = userDao.getUserId(username_str);
            if (flag) {
                setContentView(R.layout.skiplogin);
                TextView userName = findViewById(R.id.login_info_username);//登录账户
                TextView nickName = findViewById(R.id.login_info_nickname);//昵称
                TextView userId = findViewById(R.id.login_info_userId);//患者ID
                String strUserId = "用户ID:  " + intent_Userid;
                userId.setText(strUserId);
                String strUsername = "账户:  " + username_str;
                userName.setText(strUsername);
                String strNickname = "昵称:  " + userDao.getNickname(intent_Userid);
                nickName.setText(strNickname);
                back2App = findViewById(R.id.backToApp);
                cancelUser = findViewById(R.id.cancelUser);
                loginInApp();
            } else {
                Toast.makeText(this, "密码已被更改", Toast.LENGTH_SHORT).show();
                getContentView();
            }
        } else {
            getContentView();
        }
    }

    private void getContentView() {
        setContentView(R.layout.login_app);
        rememberPass = findViewById(R.id.remember_pass);
        username = findViewById(R.id.user_name);
        password = findViewById(R.id.user_password);
        register = findViewById(R.id.register_button);
        login = findViewById(R.id.login_button);
        register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        register.setTextColor(Color.RED);
        register.setClickable(true);
        username_str = pref.getString("username_pref", "");
        username.setText(username_str);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //记住密码
                username_str = username.getText().toString().trim();
                password_str = password.getText().toString().trim();
                flag = userDao.login(username_str, password_str);
                if (flag) {
                    //如果勾选
                    editor = pref.edit();
                    if (rememberPass.isChecked()) {
                        editor.putString("username_pref", username_str);
                        editor.putString("password_pref", password_str);
                        editor.putBoolean("remember_password", true);
                    } else {
                        editor.clear();
                    }
                    editor.apply();
                    Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();
                    intent_Userid = userDao.getUserId(username_str);
                    Intent intent = new Intent();
                    intent.setClass(Login.this, AllFunction.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("from_Login_User_id", intent_Userid);
                    bundle.putString("from_Login_User_Username", username_str);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    //弹出正确错误信息的方式
                    String flag_cause = userDao.failedCause(username_str, password_str);
                    Toast.makeText(Login.this, flag_cause, Toast.LENGTH_SHORT).show();
                }
            }
        });
        //跳转到注册界面
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

    private void loginInApp() {
        Intent intent = new Intent();
        intent.setClass(Login.this, AllFunction.class);
        Bundle bundle = new Bundle();
        bundle.putString("from_Login_User_id", intent_Userid);
        bundle.putString("from_Login_User_Username", username_str);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        back2App.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginInApp();
            }
        });
        cancelUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContentView();
            }
        });
    }
}
