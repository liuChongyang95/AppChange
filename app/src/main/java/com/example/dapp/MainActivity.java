package com.example.dapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Adapter.FruitAdapter;
import Database.DBHelper;
import Model.Fruit;

public class MainActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();
    private ListView listView;
    private EditText searchText;
    private ImageButton searchFruit;
    private String searchFruitText;
    private boolean isnull = true;
    //    private Drawable mDefaultQuery;
    private Drawable mQueryClear;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchFruit = findViewById(R.id.search_button);
        //测试数据库
        dbHelper = new DBHelper(this, "Fruit.db", null, 1);
        searchFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                创建数据库
//                dbHelper.getWritableDatabase();
//                for (int i =0 ; i<=5;i++) {
//                    SQLiteDatabase db = dbHelper.getWritableDatabase();
//                    ContentValues values = new ContentValues();
//                    values.put("name", "苹果");
//                    db.insert("Fruit", null, values);
//                    values.clear();
//                    values.put("name", "橘子");
//                    db.insert("Fruit", null, values);
//                    values.clear();
//                    values.put("name", "梨");
//                    db.insert("Fruit", null, values);
//                    values.clear();
//                }
            }
        });

        initFruits();
        listView = findViewById(R.id.search_result);
        searchText = findViewById(R.id.search_text);
        final Resources res = getResources();
        mQueryClear = res.getDrawable(R.drawable.clear);
        FruitAdapter adapter = new FruitAdapter(MainActivity.this, R.layout.fruit_item, fruitList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fruit fruit = fruitList.get(i);
                Intent intent = new Intent(MainActivity.this, SearchResult.class);
                Bundle bundle = new Bundle();
                bundle.putString("fruit_name", fruit.getName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        searchText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        int pad = (int) motionEvent.getX();
                        if (pad > view.getWidth() - 100 && pad < view.getWidth() - 25 && !TextUtils.isEmpty(searchText
                                .getText())) {
                            searchText.setText("");
                            int cacheInputType = searchText.getInputType();
                            searchText.setInputType(InputType.TYPE_NULL);
                            searchText.onTouchEvent(motionEvent);
                            searchText.setInputType(cacheInputType);
                            return true;
                        }
                        break;
                }
                return false;
            }

        });

        searchText.addTextChangedListener(new TextWatcher() {

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
                        searchText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                        isnull = true;
                    }
                } else {
                    if (isnull) {
                        searchText.setCompoundDrawablesWithIntrinsicBounds(null, null, mQueryClear, null);
                        isnull = false;
                    }

                }
            }
        });

//        searchFruit = findViewById(R.id.search_button);
//        searchFruit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });


    }

    private void initFruits() {
        for (int i = 0; i < 5; i++) {
            Fruit apple = new Fruit("Apple", R.drawable.apple);
            Fruit orange = new Fruit("Orange", R.drawable.orange);
            Fruit pear = new Fruit("Pear", R.drawable.pear);
            fruitList.add(apple);
            fruitList.add(orange);
            fruitList.add(pear);
        }
    }
}
