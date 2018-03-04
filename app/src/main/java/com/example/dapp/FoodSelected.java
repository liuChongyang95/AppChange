package com.example.dapp;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Objects;

import Database.DBHelper;
import Fragment_fs.Fragment_FS_GI;
import Fragment_fs.Fragment_FS_nutritioninfo;
import Fragment_fs.Fragment_FS_titalinfo;
import SearchDao.CareerDao;
import SearchDao.FoodDao;
import SearchDao.UserDao;
import SearchDao.UserIntakeDao;
import Util.Staticfinal_Value;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Administrator on 2017/12/28.
 */

public class FoodSelected extends AppCompatActivity implements View.OnClickListener {
    private String fruitName;
    private String Energy;
    private String Protein;
    private String Fat;
    private String DF;
    private String CH;

    private Bundle bundle_from_FMA;
    private CareerDao careerDao = new CareerDao(this);
    private FoodDao foodDao = new FoodDao(this);
    private UserDao userDao = new UserDao(this);

    private Staticfinal_Value sfv = new Staticfinal_Value();
    private DBHelper dbHelper = new DBHelper(this, "DApp.db", null, sfv.staticVersion());

    private LayoutInflater mInflater;
    private AlertDialog Add_dialog;
    private TextView date_setup;
    private View add_view;

    private String fdClassic;

    private TextView breakfast_0;
    private TextView lunch_1;
    private TextView dinner_2;
    private TextView befor_lunch_3;
    private TextView after_lunch_4;
    private TextView any_time_5;

    private EditText food_q;
    private TextView food_q_e;
    private float fq;

    private String[] NutArray;
    private String[] Dao_energy;
    private String[] from_result_ab;
    private UserIntakeDao userIntakeDao;

    private NumberFormat nf;
    private float percent;

    private DrawerLayout drawerLayoutFS;
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
        setContentView(R.layout.food_message);
        mInflater = LayoutInflater.from(this);
        Toolbar toolbar = findViewById(R.id.toolBar_fS);
        toolbar.getBackground().setAlpha(0);
        setSupportActionBar(toolbar);
        drawerLayoutFS = findViewById(R.id.food_msg_DL);
        NavigationView navFS = findViewById(R.id.navContent_FM);
        View navHead = navFS.getHeaderView(0);
        navFS.setItemTextColor(null);
        navFS.setItemIconTintList(null);

        Intent intent = getIntent();
        bundle_from_FMA = intent.getExtras();
        CircleImageView userPhoto = navHead.findViewById(R.id.nav_user);
        TextView tv_userid = navHead.findViewById(R.id.nav_id);
        TextView tv_userNN = navHead.findViewById(R.id.nav_nickname);
        final String initUserid = bundle_from_FMA.getString("from_Login_User_id");
        String initUserNN = "用户昵称: " + userDao.getUserName(initUserid);
        userPhoto.setImageDrawable(userDao.getUser_Photo(initUserid));
        String TVuserid = "用户ID: " + initUserid;
        tv_userid.setText(TVuserid);
        tv_userNN.setText(initUserNN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.category);
        }
        navFS.setCheckedItem(R.id.nav_Record);
        navFS.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_Record:
                        drawerLayoutFS.closeDrawer(GravityCompat.START);
                        Intent intent1 = new Intent(FoodSelected.this, FoodRecordListView.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("from_Login_User_id", initUserid);
                        intent1.putExtras(bundle);
                        startActivity(intent1);
                        break;
                }
                return true;
            }
        });

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FoodSelected.this.finish();
//            }
//        });


        fruitName = bundle_from_FMA.getString("fruit_name");
        TextView fruitNameText = findViewById(R.id.searchResult_title);
        fruitNameText.setText(fruitName);

        nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);

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
        Energy = foodDao.find_energy(fruitName);
        if (Energy != null && !Energy.equals("…") && !Energy.equals("Tr") && Energy.length() > 0 && !Energy.equals("─") && !Energy.equals("┄"))
            Energy = foodDao.find_energy(fruitName) + "千卡";
        else Energy = "—千卡";

        Protein = foodDao.find_protein(fruitName);
        if (Protein != null && !Protein.equals("…") && !Protein.equals("Tr") && Protein.length() > 0 && !Protein.equals("─") && !Protein.equals("┄"))
            Protein = foodDao.find_protein(fruitName) + "克";
        else Protein = "—克";

        Fat = foodDao.find_fat(fruitName);
        if (Fat != null && !Fat.equals("…") && !Fat.equals("Tr") && Fat.length() > 0 && !Fat.equals("─") && !Fat.equals("┄"))
            Fat = foodDao.find_fat(fruitName) + "克";
        else Fat = "—克";

        DF = foodDao.find_DF(fruitName);
        if (DF != null && !DF.equals("…") && !DF.equals("Tr") && DF.length() > 0 && !DF.equals("─") && !DF.equals("┄"))
            DF = foodDao.find_DF(fruitName) + "克";
        else DF = "—克";

        CH = foodDao.find_CH(fruitName);
        if (CH != null && !CH.equals("…") && !CH.equals("Tr") && CH.length() > 0 && !CH.equals("─") && !CH.equals("┄"))
            CH = foodDao.find_CH(fruitName) + "克";
        else CH = "—克";
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
        final EditText VS_editText = new EditText(FoodSelected.this);
        VS_editText.setHint("点击输入(最高5位数)");
        VS_editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
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
                    Intent intent = new Intent(FoodSelected.this, Nutrition_Test.class);
                    bundle_from_FMA.putInt("food_quantity", quantity);
                    intent.putExtras(bundle_from_FMA);
                    startActivity(intent);
                }
            }
        });
        VS_input.show();
    }

    private void ADD_Rec() {
        if (add_view == null) {
            add_view = mInflater.inflate(R.layout.record_add, null);
            date_setup = add_view.findViewById(R.id.date_record);
            date_setup.setText(initDate());
        }
        Add_dialog = new AlertDialog.Builder(this).create();
        Add_dialog.setView(add_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(Add_dialog.getWindow()).setContentView(R.layout.record_add);
        }
        Add_dialog.setCancelable(true);
        Button cancel = add_view.findViewById(R.id.add_concern);
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
//                handler = new MyHandler(FoodSelected.this);弱引用
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
        breakfast_0 = add_view.findViewById(R.id.fo_breakfast);
        lunch_1 = add_view.findViewById(R.id.fo_lunch);
        dinner_2 = add_view.findViewById(R.id.fo_dinner);
        befor_lunch_3 = add_view.findViewById(R.id.fo_beforeL);
        after_lunch_4 = add_view.findViewById(R.id.fo_afterL);
        any_time_5 = add_view.findViewById(R.id.fo_anytime);
        fdClassicBtn();

        String initfood_q = "100";
        food_q = add_view.findViewById(R.id.food_size);
        food_q_e = add_view.findViewById(R.id.food_size_energy);
        food_q.setText(initfood_q);
        fq = Float.parseFloat(food_q.getText().toString().trim().replace(",", ""));
        percent = fq / 100;
        String nf_per = nf.format(percent).replace(",", "");
        String g_energy = foodDao.find_energy(fruitName);
        float energy = Float.valueOf(nf_per) * Float.valueOf(g_energy);
        String energy_result = "热量" + nf.format(energy) + "千卡";
        food_q_e.setText(energy_result);

        food_q.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                float fq;
                try {
                    if (food_q.getText().toString().trim().length() > 0) {
                        fq = Float.parseFloat(food_q.getText().toString().trim().replace(",", ""));
                        percent = fq / 100;
                        String nf_per = nf.format(percent).replace(",", "");
                        String g_energy = foodDao.find_energy(fruitName);
                        double energy = Float.valueOf(nf_per) * Float.valueOf(g_energy);
                        String energy_result = "热量" + nf.format(energy) + "千卡";
                        food_q_e.setText(energy_result);
                    } else food_q_e.setText("热量0千卡");
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//添加记录表
        Button sureAdd = add_view.findViewById(R.id.add_agree);
        sureAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (food_q.getText().toString().length() > 0) {
                    String userId = bundle_from_FMA.getString("from_Login_User_id");
                    String rec_time = date_setup.getText().toString().trim();
                    String rec_classic = fdClassic;
                    String rec_size = food_q.getText().toString().trim();
                    String rec_id = bundle_from_FMA.getString("fruit_id");
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues UF = new ContentValues();
                    UF.put("User_id", userId);
                    UF.put("Food_date", rec_time);
                    UF.put("Food_class", rec_classic);
                    UF.put("Food_id", rec_id);
                    UF.put("Food_intake", rec_size);
                    UF.put("Food_ck", "未设置");
                    UF.put("Food_ingre_1", rec_id);
                    UF.put("intake_1", rec_size);
                    db.insert("UserFood", null, UF);
                    calculateNutri();
                    UF.clear();
                    db.close();
                    Add_dialog.dismiss();
                    dbHelper.close();
                    Toast.makeText(FoodSelected.this, "添加成功", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(FoodSelected.this, "您是不是没设置餐量?", Toast.LENGTH_SHORT).show();
            }
        });
        Add_dialog.show();
        //再点击 清空控件，加载dialog。
        add_view = null;
    }

    private void fdClassicBtn() {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        if (hours >= 6 && hours <= 8) {
            fdClassic = breakfast_0.getText().toString().trim();
            set_bg(breakfast_0);
        } else if (hours > 8 && hours <= 9) {
            fdClassic = befor_lunch_3.getText().toString().trim();
            set_bg(befor_lunch_3);
        } else if (hours >= 11 && hours <= 13) {
            fdClassic = lunch_1.getText().toString().trim();
            set_bg(lunch_1);
        } else if (hours > 13 && hours <= 14) {
            fdClassic = after_lunch_4.getText().toString().trim();
            set_bg(after_lunch_4);
        } else if (hours >= 18 && hours <= 21) {
            fdClassic = dinner_2.getText().toString().trim();
            set_bg(dinner_2);
        } else {
            fdClassic = any_time_5.getText().toString().trim();
            set_bg(any_time_5);
        }

        breakfast_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fdClassic = breakfast_0.getText().toString().trim();
                set_bg(breakfast_0);
                set_bg_null(lunch_1);
                set_bg_null(dinner_2);
                set_bg_null(befor_lunch_3);
                set_bg_null(after_lunch_4);
                set_bg_null(any_time_5);
            }
        });

        lunch_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fdClassic = lunch_1.getText().toString().trim();
                set_bg(lunch_1);
                set_bg_null(breakfast_0);
                set_bg_null(dinner_2);
                set_bg_null(befor_lunch_3);
                set_bg_null(after_lunch_4);
                set_bg_null(any_time_5);
            }
        });

        dinner_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fdClassic = dinner_2.getText().toString().trim();
                set_bg(dinner_2);
                set_bg_null(breakfast_0);
                set_bg_null(lunch_1);
                set_bg_null(befor_lunch_3);
                set_bg_null(after_lunch_4);
                set_bg_null(any_time_5);
            }
        });

        befor_lunch_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fdClassic = befor_lunch_3.getText().toString().trim();
                set_bg(befor_lunch_3);
                set_bg_null(breakfast_0);
                set_bg_null(lunch_1);
                set_bg_null(dinner_2);
                set_bg_null(after_lunch_4);
                set_bg_null(any_time_5);
            }
        });

        after_lunch_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fdClassic = after_lunch_4.getText().toString().trim();
                set_bg(after_lunch_4);
                set_bg_null(breakfast_0);
                set_bg_null(lunch_1);
                set_bg_null(dinner_2);
                set_bg_null(befor_lunch_3);
                set_bg_null(any_time_5);
            }
        });

        any_time_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fdClassic = any_time_5.getText().toString().trim();
                set_bg(any_time_5);
                set_bg_null(breakfast_0);
                set_bg_null(lunch_1);
                set_bg_null(dinner_2);
                set_bg_null(befor_lunch_3);
                set_bg_null(after_lunch_4);
            }
        });

    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    String initDate = initDate();
                    date_setup.setText(initDate);
                    Toast.makeText(FoodSelected.this, initDate, Toast.LENGTH_SHORT).show();
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

    public String initDate() {
        String date;
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
        return date;
    }

    private void set_bg(TextView fo_class) {
        fo_class.setBackgroundResource(R.drawable.fo_class_btn);
    }

    private void set_bg_null(TextView fo_class) {
        fo_class.setBackgroundColor(Color.TRANSPARENT);
    }

    //要插入的23-2个营养元素求和值    记录值----要插入的基础值----倍数
    public String[] result_ab(String[] nutrition, String[] f_nutrition, String nf_per) {
        String[] result_str = new String[23];

        for (int i = 0; i < 21; i++) {
            if (nutrition[i] != null) {
                float result_a = Float.valueOf(nutrition[i]);
                if (f_nutrition[i] != null && !f_nutrition[i].equals("…") && !f_nutrition[i].equals("Tr") && f_nutrition[i].length() > 0 && !f_nutrition[i].equals("—") && !f_nutrition[i].equals("┄") && !f_nutrition[i].equals("─")) {
                    float result_b = Float.valueOf(
                            nf.format(Float.valueOf(nf_per) * Float.valueOf(f_nutrition[i])).replace(",", ""));
                    result_str[i] = nf.format(result_a + result_b).replace(",", "");
                }
            } else if (f_nutrition[i] != null && !f_nutrition[i].equals("…") && !f_nutrition[i].equals("Tr") && f_nutrition[i].length() > 0 && !f_nutrition[i].equals("—") && !f_nutrition[i].equals("┄") && !f_nutrition[i].equals("─")) {
                result_str[i] = nf.format(Float.valueOf(nf_per) * Float.valueOf(f_nutrition[i])).replace(",", "");
            } else {
                result_str[i] = null;
            }
        }
        return result_str;
    }

    //添加到用户营养摄入
    private void calculateNutri() {
        //数据库的23-2个营养元素值
        NutArray = new String[23];
        NutArray[0] = foodDao.find_energy(fruitName);
        NutArray[1] = foodDao.find_protein(fruitName);
        NutArray[3] = foodDao.find_DF(fruitName);
        NutArray[4] = foodDao.find_CH(fruitName);
        NutArray[2] = foodDao.find_fat(fruitName);
        NutArray[5] = foodDao.find_water(fruitName);
        NutArray[6] = foodDao.find_vA(fruitName);
        NutArray[7] = foodDao.find_vB1(fruitName);
        NutArray[8] = foodDao.find_vB2(fruitName);
        NutArray[9] = foodDao.find_vB3(fruitName);
        NutArray[10] = foodDao.find_vE(fruitName);
        NutArray[11] = foodDao.find_vC(fruitName);
        NutArray[12] = foodDao.find_Fe(fruitName);
        NutArray[13] = foodDao.find_Ga(fruitName);
        NutArray[14] = foodDao.find_Na(fruitName);
        NutArray[15] = foodDao.find_CLS(fruitName);
        NutArray[16] = foodDao.find_K(fruitName);
        NutArray[17] = foodDao.find_Mg(fruitName);
        NutArray[18] = foodDao.find_Zn(fruitName);
        NutArray[19] = foodDao.find_P(fruitName);
        NutArray[20] = foodDao.find_purine(fruitName);

        //23-2个列名
        String[] NutName = {"UI_energy", "UI_protein", "UI_fat", "UI_DF", "UI_CH", "UI_water", "UI_VA", "UI_VB1",
                "UI_VB2", "UI_VB3", "UI_VE", "UI_VC", "UI_Fe", "UI_Ga", "UI_Na", "UI_CLS", "UI_K", "UI_Mg",
                "UI_Zn", "UI_P", "UI_purine"};

        float fq = Float.parseFloat(food_q.getText().toString().trim());
        percent = fq / 100;
        String nf_per = nf.format(percent).replace(",", "");
        String userId = bundle_from_FMA.getString("from_Login_User_id");
        String UIdate = date_setup.getText().toString().trim();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues userIntake = new ContentValues();

        try {
            userIntakeDao = new UserIntakeDao(this);
            Dao_energy = userIntakeDao.getFromUserIntake(userId, fdClassic, UIdate);
            from_result_ab = result_ab(Dao_energy, NutArray, nf_per);
            userIntake.put("User_id", userId);
            userIntake.put("UI_date", UIdate);
            userIntake.put("UI_class", fdClassic);
            for (int j = 0; j < 21; j++) {
                userIntake.put(NutName[j], from_result_ab[j]);
            }
            db.insertOrThrow("UserIntake", null, userIntake);
        } catch (SQLiteConstraintException ex) {
//            userIntake.clear();
//            userIntakeDao = new UserIntakeDao(this);
//            //数据库中的23-2个营养元素值
//            Dao_energy = userIntakeDao.getFromUserIntake(userId, fdClassic, UIdate);
//            //要插入的23-2个营养元素求和值
//            from_result_ab = result_ab(Dao_energy, NutArray, nf_per);
//            //插入23-2个值
//            for (int j = 0; j <= 21; j++) {
//                userIntake.put(NutName[j], from_result_ab[j]);
//            }
            db.update("UserIntake", userIntake, "User_id = ? and UI_date=? and UI_class=?", new String[]{userId, UIdate, fdClassic});
        } finally {
            userIntake.clear();
            db.close();
            dbHelper.close();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayoutFS.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    //弱引用
//    private static class MyHandler extends Handler {
//        WeakReference<FoodSelected> fruitSelectWeakReference;
//
//        MyHandler(FoodSelected fruitSelect) {
//            fruitSelectWeakReference = new WeakReference<>(fruitSelect);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            FoodSelected fruitSelect = fruitSelectWeakReference.get();
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

