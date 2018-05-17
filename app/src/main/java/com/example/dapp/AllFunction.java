package com.example.dapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2018/1/2.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import SearchDao.UserDao;
import de.hdodenhof.circleimageview.CircleImageView;

public class AllFunction extends AppCompatActivity implements View.OnClickListener {
    private List<Map<String, Object>> data_list = new ArrayList<>();
    private UserDao userDao;
    private String from_login_user_id;
    private TextView user_name;
    private CircleImageView user_photo;
    private Bundle bundle_id;

    // 图片封装为一个数组
    private int[] icon = {R.drawable.food, R.drawable.sport,
            R.drawable.blood, R.drawable.data, R.drawable.medical,
            R.drawable.question};
    private String[] iconName = {"饮食管理", "运动管理", "血糖管理", "数据上传", "医疗方案", "科普答疑"};

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
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
        setContentView(R.layout.main_all);
        user_photo = findViewById(R.id.user_info_pic);
        user_name = findViewById(R.id.user_info);
        Button user_change = findViewById(R.id.user_info_change);
        user_change.setOnClickListener(this);
        userDao = new UserDao(AllFunction.this);
        Intent intent = getIntent();
        bundle_id = intent.getExtras();
        if (bundle_id != null) {
            from_login_user_id = bundle_id.getString("from_Login_User_id");
        }
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        toolbar.getBackground().setAlpha(0);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllFunction.this.finish();
            }
        });

        final GridView gridView = findViewById(R.id.gridView);
        //获取数据
        getData();
        //新建适配器
        String[] from = {"image", "text"};
        int[] to = {R.id.image, R.id.text};
        final SimpleAdapter sim_adapter = new SimpleAdapter(this, data_list, R.layout.main_all_item, from, to);
        //配置适配器
        gridView.setAdapter(sim_adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    //饮食管理
                    case 0:
                        if (userDao.checkInfo(from_login_user_id).equals("未设置")) {
                            Toast.makeText(AllFunction.this, "请完善个人信息", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(AllFunction.this, FoodAllFunction.class);
                            intent.putExtras(bundle_id);
                            startActivity(intent);
                        }
                        break;
                    case 1:

                        break;

                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_info_change:
                Intent intent = new Intent();
                intent.setClass(AllFunction.this, AllUserInfo.class);
                intent.putExtras(bundle_id);
                startActivity(intent);
        }
    }

    public List<Map<String, Object>> getData() {
        //icon和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }
        return data_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        user_name.setText(userDao.getUserName(from_login_user_id));
        user_photo.setImageDrawable(userDao.getUser_Photo(from_login_user_id));
    }

}
