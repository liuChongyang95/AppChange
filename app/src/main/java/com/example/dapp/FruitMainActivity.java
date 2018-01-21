package com.example.dapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import Adapter.FruitAdapter;
import Database.DBHelper;
import JavaBean.Fruit;
import SearchDao.FruitDao;

public class FruitMainActivity extends AppCompatActivity {

    private Fruit fruit;
    private List<Fruit> fruitList = new ArrayList<>();
    private ListView listView;
    private EditText searchEText;
    private ImageButton searchFruit;
    private String searchFruitText;
    private boolean isnull = true;
    private Drawable mQueryClear;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private FruitDao fruitDao;
    private String fruitName;
    private String fruitNutrition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.fruit_main);
        Toolbar toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(255);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FruitMainActivity.this.finish();
            }
        });
        listView = findViewById(R.id.search_result);
        searchEText = findViewById(R.id.search_text);
        searchFruit = findViewById(R.id.search_button);

        final Resources res = getResources();
        mQueryClear = res.getDrawable(R.drawable.clear);

        //加载页面
        fruitDao = new FruitDao(this);
        fruitList = fruitDao.getFruitList();
        FruitAdapter adapter = new FruitAdapter(FruitMainActivity.this, R.layout.fruit_item, fruitList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fruit fruit = fruitList.get(i);
                Intent intent = new Intent(FruitMainActivity.this, FruitSelect.class);
                Bundle bundle = new Bundle();
                bundle.putString("fruit_name", fruit.getRi_Food_name());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        searchEText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        int pad = (int) motionEvent.getX();
                        if (pad > view.getWidth() - 100 && pad < view.getWidth() - 25 && !TextUtils.isEmpty(searchEText
                                .getText())) {
                            searchEText.setText("");
                            int cacheInputType = searchEText.getInputType();
                            searchEText.setInputType(InputType.TYPE_NULL);
                            searchEText.onTouchEvent(motionEvent);
                            searchEText.setInputType(cacheInputType);
                            return true;
                        }
                        break;
                }
                return false;
            }
        });

        searchEText.addTextChangedListener(new TextWatcher() {

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
                        searchEText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                        isnull = true;
                    }
                } else {
                    if (isnull) {
                        searchEText.setCompoundDrawablesWithIntrinsicBounds(null, null, mQueryClear, null);
                        isnull = false;
                    }
                }
            }
        });



        searchFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!searchEText.getText().toString().equals("")) {
                    searchFruitText = searchEText.getText().toString().trim();
                    Intent intent = new Intent(FruitMainActivity.this, FruitSearch.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("searchText", searchFruitText);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        searchEText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId,
                                          KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ((InputMethodManager) searchEText.getContext().getSystemService
                            (Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(FruitMainActivity.this.getCurrentFocus
                                            ().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    if (!searchEText.getText().toString().equals("")) {
                        searchFruitText = searchEText.getText().toString().trim();
                        Intent intent = new Intent(FruitMainActivity.this, FruitSearch.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("searchText", searchFruitText);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    return true;
                }
                return false;
            }
        });

    }



}
