package com.example.dapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Adapter.HistoryAdapter;
import Database.DBHelper;
import JavaBean.History;
import SearchDao.SearchHistoryDao;
import Util.Staticfinal_Value;

public class FoodSearchToAdd extends AppCompatActivity implements View.OnClickListener {
    private Bundle bundle_from_FAF;
    private List<History> histories = new ArrayList<>();
    private String getId;
    private String foodName;
    private Drawable mQuery;
    private EditText foodSearch;
    private boolean isnull = true;
    private ImageButton search_query;
    private ListView listHistory;
    private HistoryAdapter historyAdapter;
    private Staticfinal_Value sfv;
    private DBHelper dbHelper;
    private SearchHistoryDao historyDao;
    private TextView clearHis;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.food_searchtoadd);
        Intent intent = getIntent();
        bundle_from_FAF = intent.getExtras();
        getId = bundle_from_FAF.getString("from_Login_User_id");
        Toolbar toolbar = findViewById(R.id.toolbar_food_searchtoAdd);
        foodSearch = findViewById(R.id.myEditText);
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodSearchToAdd.this.finish();
            }
        });
        sfv = new Staticfinal_Value();
        dbHelper = new DBHelper(this, "DApp.db", null, sfv.staticVersion());
        final Resources res = getResources();
        mQuery = res.getDrawable(R.drawable.clear);
        foodSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        int pad = (int) motionEvent.getX();
                        if (pad > view.getWidth() - 100 && pad < view.getWidth() - 25 && !TextUtils.isEmpty(foodSearch
                                .getText())) {
                            foodSearch.setText("");
                            int cacheInputType = foodSearch.getInputType();
                            foodSearch.setInputType(InputType.TYPE_NULL);
                            foodSearch.onTouchEvent(motionEvent);
                            foodSearch.setInputType(cacheInputType);
                            return true;
                        }
                        break;
                }
                return false;
            }
        });

        foodSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    if (!isnull) {
                        foodSearch.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                        isnull = true;
                    }
                } else {
                    if (isnull) {
                        foodSearch.setCompoundDrawablesWithIntrinsicBounds(null, null, mQuery, null);
                        isnull = false;
                    }
                }
            }
        });

        //软键盘
        foodSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId,
                                          KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ((InputMethodManager) foodSearch.getContext().getSystemService
                            (Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(FoodSearchToAdd.this.getCurrentFocus
                                            ().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    if (!foodSearch.getText().toString().equals("")) {
                        foodName = foodSearch.getText().toString().trim();
                        Intent intent = new Intent(FoodSearchToAdd.this, FoodMainActivity.class);
                        bundle_from_FAF.putString("searchFood_name", foodName);
                        intent.putExtras(bundle_from_FAF);
                        transData();
                        startActivity(intent);
                    }
                    return true;
                }
                return false;
            }
        });
        listHistory = findViewById(R.id.search_history);
        historyDao = new SearchHistoryDao(this);
        histories = historyDao.getHistory(getId);
        historyAdapter = new HistoryAdapter(this, R.layout.history_item, histories);
        listHistory.setAdapter(historyAdapter);

        listHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                History history = histories.get(position);
                bundle_from_FAF.putString("searchFood_name", history.getFoodname());
                Intent intent = new Intent(FoodSearchToAdd.this, FoodMainActivity.class);
                intent.putExtras(bundle_from_FAF);
                transData_ls(history.getFoodname());
                startActivity(intent);
            }
        });

        clearHis = findViewById(R.id.clear_history);
        clearHis.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_to_add:
                foodName = foodSearch.getText().toString().trim();
                bundle_from_FAF.putString("searchFood_name", foodName);
                Intent intent = new Intent(FoodSearchToAdd.this, FoodMainActivity.class);
                intent.putExtras(bundle_from_FAF);
                transData();
                startActivity(intent);
                break;

            case R.id.clear_history:
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);
                break;
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
                    sqLiteDatabase.execSQL("delete from tempSH");
                    dbHelper.close();
                    sqLiteDatabase.close();
                    onResume();
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    private void transData() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues SHtemp = new ContentValues();
        ContentValues FSH = new ContentValues();
        if (foodName != null && foodName.length() > 0) {
            SHtemp.put("tempName", foodName);
            SHtemp.put("User_id", getId);
            FSH.put("SH_food_name", foodName);
            FSH.put("User_id", getId);
            try {
                sqLiteDatabase.insertOrThrow("FoodSH", null, FSH);
                sqLiteDatabase.insertOrThrow("tempSH", null, SHtemp);
            } catch (SQLiteConstraintException e) {
                sqLiteDatabase.delete("tempSH", "tempName=?", new String[]{foodName});
                sqLiteDatabase.insertOrThrow("tempSH", null, SHtemp);
            } finally {
                FSH.clear();
                SHtemp.clear();
                sqLiteDatabase.close();
                dbHelper.close();
            }
        }
    }

    private void transData_ls(String foodName) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues SHtemp = new ContentValues();
        ContentValues FSH = new ContentValues();
        if (foodName != null && foodName.length() > 0) {
            SHtemp.put("tempName", foodName);
            SHtemp.put("User_id", getId);
            FSH.put("SH_food_name", foodName);
            FSH.put("User_id", getId);
            try {
                sqLiteDatabase.insertOrThrow("FoodSH", null, FSH);
                sqLiteDatabase.insertOrThrow("tempSH", null, SHtemp);
            } catch (SQLiteConstraintException e) {
                sqLiteDatabase.delete("tempSH", "tempName=?", new String[]{foodName});
                sqLiteDatabase.insertOrThrow("tempSH", null, SHtemp);
            } finally {
                FSH.clear();
                SHtemp.clear();
                sqLiteDatabase.close();
                dbHelper.close();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        histories = historyDao.getHistory(getId);
        historyAdapter = new HistoryAdapter(this, R.layout.history_item, histories);
        listHistory.setAdapter(historyAdapter);
        if (listHistory.getCount() <= 0) {
            clearHis.setVisibility(View.INVISIBLE);
        } else clearHis.setVisibility(View.VISIBLE);

    }
}
