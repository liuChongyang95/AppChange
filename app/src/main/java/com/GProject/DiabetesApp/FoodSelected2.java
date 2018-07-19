package com.GProject.DiabetesApp;
/*
* 自维护，饮食录入界面
* */
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Fragment_fs.Fragment_FS_GI;
import Fragment_fs.Fragment_FS_nutritioninfo;
import Fragment_fs.Fragment_FS_titalinfo;
import SearchDao.FoodDao;

public class FoodSelected2 extends AppCompatActivity implements View.OnClickListener {
    private FoodDao foodDao;
    private Bundle bundle;
    private String fruitName;
    private String Energy;
    private String Protein;
    private String Fat;
    private String DF;
    private String CH;
    private String nameCode;
    private String namePosition;

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
        setContentView(R.layout.food_selected_2);
        Toolbar toolbar = findViewById(R.id.toolBar_fS_2);
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodSelected2.this.finish();
            }
        });
        Intent intent = getIntent();
        bundle = intent.getExtras();
        foodDao = new FoodDao(this);
        fruitName = bundle.getString("fruit_name");
        TextView title2 = findViewById(R.id.searchResult_title_2);
        title2.setText(fruitName);
        Energy = foodDao.find_energy(fruitName);
        namePosition = bundle.getString("name_position");

//        碎片标题
        Fragment_FS_titalinfo fragment_fs_titalinfo = (Fragment_FS_titalinfo) getSupportFragmentManager().findFragmentById(R.id.title_fragment_2);
        fragment_fs_titalinfo.loading_title(fruitName, Energy);

//        内容碎片
        Energy = foodDao.find_energy(fruitName);
        if (!(Energy != null && !Energy.equals("…") && !Energy.equals("Tr") && Energy.length() > 0 && !Energy.equals("─") && !Energy.equals("┄")))
            Energy = "—";

        Protein = foodDao.find_protein(fruitName);
        if (!(Protein != null && !Protein.equals("…") && !Protein.equals("Tr") && Protein.length() > 0 && !Protein.equals("─") && !Protein.equals("┄")))
            Protein = "—";

        Fat = foodDao.find_fat(fruitName);
        if (!(Fat != null && !Fat.equals("…") && !Fat.equals("Tr") && Fat.length() > 0 && !Fat.equals("─") && !Fat.equals("┄")))
            Fat = "—";

        DF = foodDao.find_DF(fruitName);
        if (!(DF != null && !DF.equals("…") && !DF.equals("Tr") && DF.length() > 0 && !DF.equals("─") && !DF.equals("┄")))
            DF = "—";

        CH = foodDao.find_CH(fruitName);
        if (!(CH != null && !CH.equals("…") && !CH.equals("Tr") && CH.length() > 0 && !CH.equals("─") && !CH.equals("┄")))
            CH = "—";

        Fragment_FS_nutritioninfo fsNutritioninfo = (Fragment_FS_nutritioninfo) getSupportFragmentManager().findFragmentById(R.id.nutrition_fragment_2);
        fsNutritioninfo.loading_nutrition(Energy, Protein, CH, DF, Fat);
        fsNutritioninfo.setArguments(bundle);

//        GI碎片
        String GI = foodDao.find_GI(fruitName);
        String GI_per = foodDao.find_GI_per(fruitName);
        Fragment_FS_GI fragment_fs_gi = (Fragment_FS_GI) getSupportFragmentManager().findFragmentById(R.id.gi_fragment_2);
        fragment_fs_gi.loading_GI(GI, GI_per);
    }

    private void VS() {
        final EditText VS_editText = new EditText(FoodSelected2.this);
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
                    Intent intent = new Intent(FoodSelected2.this, Nutrition_Test.class);
                    bundle.putInt("food_quantity", quantity);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        VS_input.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.VS_LL_2:
                VS();
                break;
            case R.id.Add_LL_2:
                switch (namePosition) {
                    case "name1":
                        nameCode = "1";
                        break;
                    case "name2":
                        nameCode = "2";
                        break;
                    case "name3":
                        nameCode = "3";
                        break;
                    case "name4":
                        nameCode = "4";
                        break;
                    case "name5":
                        nameCode = "5";
                        break;
                }
                final EditText editText = new EditText(FoodSelected2.this);
                editText.setHint("点击输入成分数量");
                editText.setMaxLines(1);
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                final AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(FoodSelected2.this);
                inputDialog.setTitle("成分" + nameCode + "录入").setView(editText);
                inputDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                inputDialog.setPositiveButton("确定录入", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (editText.length() != 0) {
                            Intent intent = new Intent(FoodSelected2.this, SelfMainFood.class);
                            bundle.putString("nameCode", nameCode);
                            bundle.putString("nameValue", editText.getText().toString());
                            Log.d("FoodSelected2", nameCode);
                            Log.d("FoodSelected2", editText.getText().toString() );
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        } else dialog.dismiss();
                    }
                }).show();
                break;
        }
    }
}
