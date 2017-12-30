package com.example.dapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.ImageView;
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
    private Drawable mQueryClear;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

//                测试数据库
        dbHelper = new DBHelper(this, "Fruit.db", null, 1);
//        EXPORT-1
        sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("Fruit", null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                byte[] b = cursor.getBlob(cursor.getColumnIndexOrThrow(DBHelper.PicColumns.picture));
                //将获取的数据转换成drawable
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length, null);
                BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
                Drawable drawable = bitmapDrawable;
                String name = cursor.getString(cursor.getColumnIndex("name"));
                Fruit fruit = new Fruit(name, drawable);
                fruitList.add(fruit);
            }
        }
//        initFruits();
    }

//    private void initFruits() {
//        for (int i = 0; i < 5; i++) {
//            Fruit apple = new Fruit("Apple", R.drawable.apple, "100/1克");
//            Fruit orange = new Fruit("Orange", R.drawable.orange, "100/1克");
//            Fruit pear = new Fruit("Pear", R.drawable.pear, "100/1克");
//            fruitList.add(apple);
//            fruitList.add(orange);
//            fruitList.add(pear);
//        }
//    }
}
