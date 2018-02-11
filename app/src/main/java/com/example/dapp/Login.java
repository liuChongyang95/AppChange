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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import SearchDao.UserDao;

/**
 * Created by Administrator on 2018/1/2.
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.login_app);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        rememberPass = findViewById(R.id.remember_pass);
        boolean isRemember = pref.getBoolean("remember_password", false);


        username = findViewById(R.id.user_name);
        password = findViewById(R.id.user_password);
        register = findViewById(R.id.register_button);
        login = findViewById(R.id.login_button);
        register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        register.setTextColor(Color.RED);
        register.setClickable(true);
        userDao = new UserDao(Login.this);
        if (isRemember) {
            username_str = pref.getString("username_pref", "");
            password_str = pref.getString("password_pref", "");
            username.setText(username_str);
            password.setText(password_str);
            rememberPass.setChecked(true);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //记住密码


                username_str = username.getText().toString().trim();
                password_str = password.getText().toString().trim();
                Boolean flag = userDao.login(username_str, password_str);
                if (flag) {
                    //remember password
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
                    username_str = username.getText().toString().trim();
                    password_str = password.getText().toString().trim();
                    intent_Userid = userDao.getUserId(username_str);
                    Intent intent = new Intent();
                    intent.setClass(Login.this, AllFunction.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("from_Login_User_id", intent_Userid);
                    bundle.putString("from_Login_User_Username",username_str);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    String flag_cause = userDao.failedCause(username_str, password_str);
                    Toast.makeText(Login.this, flag_cause, Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }
}
