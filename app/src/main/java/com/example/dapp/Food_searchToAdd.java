package com.example.dapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Food_searchToAdd extends AppCompatActivity implements View.OnClickListener {
    Bundle bundle_from_FRa;
    private String getId;
    private String foodName;
    private Drawable mQuery;
    private EditText foodSearch;
    private boolean isnull = true;
    private ImageButton search_query;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_searchtoadd);
        Intent intent = getIntent();
        bundle_from_FRa = intent.getExtras();
        getId = bundle_from_FRa.getString("from_Login_User_id");
        Toolbar toolbar = findViewById(R.id.toolbar_food_searchtoAdd);
        foodSearch = findViewById(R.id.myEditText);
        search_query = findViewById(R.id.search_to_add);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Food_searchToAdd.this.finish();
            }
        });
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
                            .hideSoftInputFromWindow(Food_searchToAdd.this.getCurrentFocus
                                            ().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    if (!foodSearch.getText().toString().equals("")) {
                        foodName = foodSearch.getText().toString().trim();
                        Intent intent = new Intent(Food_searchToAdd.this, FruitMainActivity.class);
                        bundle_from_FRa.putString("searchFood_name", foodName);
                        intent.putExtras(bundle_from_FRa);
                        startActivity(intent);
                    }
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_to_add:
                foodName = foodSearch.getText().toString().trim();
                bundle_from_FRa.putString("searchFood_name", foodName);
                Intent intent = new Intent(Food_searchToAdd.this, FruitMainActivity.class);
                intent.putExtras(bundle_from_FRa);
                startActivity(intent);
                break;

            case R.id.myEditText:

                break;
        }
    }
}
