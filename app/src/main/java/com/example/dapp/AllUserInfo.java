package com.example.dapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

import SearchDao.UserDao;
import de.hdodenhof.circleimageview.CircleImageView;


public class AllUserInfo extends AppCompatActivity implements View.OnClickListener {
    private String get_edit_ID;
    private TextView edit_user_nickname;
    private TextView edit_user_sex;
    private TextView edit_user_birth;
    private TextView edit_user_tall;
    private TextView edit_user_weight;
    private TextView edit_user_career;
    private TextView edit_user_intensity;
    private TextView edit_user_shape;
    private TextView edit_user_weight_expect;
    private TextView edit_user_age;
    private CircleImageView edit_user_photo;
    private Date edit_birth_str_date;
    private float edit_expect_weight;
    private UserDao userDao = new UserDao(this);

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("SetTextI18n")
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
        setContentView(R.layout.user_info_edit);
        Toolbar toolbar = findViewById(R.id.user_info_edit_toolbar);
        edit_user_photo = findViewById(R.id.user_info_edit_photo);
        TextView edit_user_ID = findViewById(R.id.user_info_medical_id);
        edit_user_nickname = findViewById(R.id.user_info_edit_nickname);
        TextView edit_user_loginName = findViewById(R.id.user_info_login_name);
        edit_user_sex = findViewById(R.id.user_info_edit_sex);
        edit_user_birth = findViewById(R.id.user_info_edit_birth);
        edit_user_tall = findViewById(R.id.user_info_edit_tall);
        edit_user_weight = findViewById(R.id.user_info_edit_weight);
        edit_user_career = findViewById(R.id.user_info_edit_career);
        edit_user_intensity = findViewById(R.id.user_info_intensity);
        edit_user_shape = findViewById(R.id.user_info_shape);
        edit_user_weight_expect = findViewById(R.id.user_info_Expect_weight);
        edit_user_age = findViewById(R.id.user_info_age);


        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllUserInfo.this.finish();
            }
        });
        Intent intent = getIntent();
        Bundle bundle_from_MA = intent.getExtras();
        get_edit_ID = bundle_from_MA.getString("from_Login_User_id");
        String get_edit_LoginName = bundle_from_MA.getString("from_Login_User_Username");
        UserDao userDao = new UserDao(this);
        edit_user_photo.setImageDrawable(userDao.getUser_Photo(get_edit_ID));
        edit_user_ID.setText(get_edit_ID);
        edit_user_nickname.setText(userDao.getNickname(get_edit_ID));
        edit_user_loginName.setText(get_edit_LoginName);
        edit_user_sex.setText(userDao.getSex(get_edit_ID));
        edit_user_birth.setText(userDao.getBirth(get_edit_ID));
        edit_user_tall.setText(userDao.getTall(get_edit_ID) + "cm");
        edit_user_weight.setText(userDao.getWeight(get_edit_ID) + "kg");
        edit_user_weight_expect.setText(userDao.getExpect_weight(get_edit_ID) + "kg");
        edit_user_career.setText(userDao.getCareer(get_edit_ID));
        edit_user_shape.setText(userDao.getShape(get_edit_ID));
        edit_user_intensity.setText(userDao.getIntensity(userDao.getCareer(get_edit_ID)));
        edit_birth_str_date = Date.valueOf(edit_user_birth.getText().toString());
        int a = getAge(edit_birth_str_date);
        edit_user_age.setText(String.valueOf(a));


    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //修改昵称
            case R.id.user_info_LL_nickname:
                final EditText editText = new EditText(AllUserInfo.this);
                editText.setHint("点击输入");
                final AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(this);
                inputDialog.setTitle("修改昵称").setView(editText);
                inputDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                inputDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        onResume();
                    }
                });
                inputDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (editText.length() != 0) {
                            userDao.changNickname(get_edit_ID, editText.getText().toString());
                            Toast.makeText(AllUserInfo.this, editText.getText().toString(), Toast.LENGTH_SHORT).show();
                        } else dialog.dismiss();
                    }
                }).show();
                break;
            //头像
            case R.id.user_info_LL_photo:
                if (ContextCompat.checkSelfPermission(AllUserInfo.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AllUserInfo.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
                break;
            //性别
            case R.id.user_info_LL_sex:
                final String[] items = {"男性", "女性"};
                final int[] yourChoice = {-1};
                AlertDialog.Builder singleChoiceDialog =
                        new AlertDialog.Builder(AllUserInfo.this);
                singleChoiceDialog.setTitle("修改性别");
                // 第二个参数是默认选项，此处设置为0
                singleChoiceDialog.setSingleChoiceItems(items, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                yourChoice[0] = which;
                            }
                        });
                singleChoiceDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (yourChoice[0] != -1) {
                                    userDao.changeSex(get_edit_ID, items[yourChoice[0]]);
                                    Toast.makeText(AllUserInfo.this,
                                            items[yourChoice[0]],
                                            Toast.LENGTH_SHORT).show();
                                } else dialog.dismiss();
                            }
                        });
                singleChoiceDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        onResume();
                    }
                });
                singleChoiceDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                singleChoiceDialog.show();
                break;
            case R.id.user_info_LL_birth:
                showDialog(4);
                break;

            case R.id.user_info_LL_tall:
                final String[] edit_result_tall = {null};
                AlertDialog.Builder tallDialog =
                        new AlertDialog.Builder(AllUserInfo.this);
                final View[] dialogView = {LayoutInflater.from(AllUserInfo.this)
                        .inflate(R.layout.dialog_tall, null)};
                final EditText big = dialogView[0].findViewById(R.id.bignumber_tall);
                final EditText small = dialogView[0].findViewById(R.id.smallnumber_tall);
                tallDialog.setTitle("输入身高");
                tallDialog.setView(dialogView[0]);
                tallDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 获取EditView中的输入内容
                                if (big.length() != 0) {
                                    int a = Integer.parseInt(big.getText().toString().trim());
                                    if (a < 250 && a > 30) {
                                        if (small.length() == 0) {
                                            edit_result_tall[0] = big.getText().toString() + ".0";
                                            userDao.changeTall(get_edit_ID, edit_result_tall[0]);
                                            edit_expect_weight = Float.valueOf(edit_result_tall[0]) - 105;
                                            userDao.changeExpectWeight(get_edit_ID, edit_expect_weight);
                                            String editShape = getShape(userDao.getWeight(get_edit_ID), userDao.getExpect_weight(get_edit_ID));
                                            userDao.changeShape(get_edit_ID, editShape);
                                            Toast.makeText(AllUserInfo.this, edit_result_tall[0] + "cm", Toast.LENGTH_SHORT).show();
                                        } else {
                                            edit_result_tall[0] = big.getText().toString() + "." + small.getText().toString();
                                            userDao.changeTall(get_edit_ID, edit_result_tall[0]);
                                            edit_expect_weight = Float.valueOf(edit_result_tall[0]) - 105;
                                            userDao.changeExpectWeight(get_edit_ID, edit_expect_weight);
                                            String editShape = getShape(userDao.getWeight(get_edit_ID), userDao.getExpect_weight(get_edit_ID));
                                            userDao.changeShape(get_edit_ID, editShape);
                                            Toast.makeText(AllUserInfo.this, edit_result_tall[0] + "cm", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(AllUserInfo.this, "数据可能错误", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(AllUserInfo.this, "无更改信息，或者格式不符合规范", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                tallDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        onResume();
                    }
                });
                tallDialog.show();
                break;
            //体重
            case R.id.user_info_LL_weight:
                final String[] edit_result_weight = {null};
                AlertDialog.Builder weightDialog =
                        new AlertDialog.Builder(AllUserInfo.this);
                final View[] dialogView_weight = {LayoutInflater.from(AllUserInfo.this)
                        .inflate(R.layout.dialog_weight, null)};
                final EditText big_weight = dialogView_weight[0].findViewById(R.id.bignumber_weight);
                final EditText small_weight = dialogView_weight[0].findViewById(R.id.smallnumber_weight);
                weightDialog.setTitle("输入体重");
                weightDialog.setView(dialogView_weight[0]);
                weightDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 获取EditView中的输入内容
                                if (big_weight.length() != 0) {
                                    int a = Integer.parseInt(big_weight.getText().toString().trim());
                                    if (a < 200 && a > 10) {
                                        if (small_weight.length() == 0) {
                                            edit_result_weight[0] = big_weight.getText().toString() + ".0";
                                            userDao.changeWeight(get_edit_ID, edit_result_weight[0]);
                                            String editShape = getShape(userDao.getWeight(get_edit_ID), userDao.getExpect_weight(get_edit_ID));
                                            userDao.changeShape(get_edit_ID, editShape);
                                            Toast.makeText(AllUserInfo.this, edit_result_weight[0] + "cm", Toast.LENGTH_SHORT).show();
                                        } else {
                                            edit_result_weight[0] = big_weight.getText().toString() + "." + small_weight.getText().toString();
                                            userDao.changeWeight(get_edit_ID, edit_result_weight[0]);
                                            String editShape = getShape(userDao.getWeight(get_edit_ID), userDao.getExpect_weight(get_edit_ID));
                                            userDao.changeShape(get_edit_ID, editShape);
                                            Toast.makeText(AllUserInfo.this, edit_result_weight[0] + "cm", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(AllUserInfo.this, "数据可能错误", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(AllUserInfo.this, "无更改信息，或者格式不符合规范", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                weightDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        onResume();
                    }
                });
                weightDialog.show();
                break;
            case R.id.user_info_LL_career:
                Intent intent = new Intent();
                intent.setClass(AllUserInfo.this, CareerList.class);
                startActivityForResult(intent, 5);
                break;
            case R.id.user_info_LL_password:
                AlertDialog.Builder passwordDialog =
                        new AlertDialog.Builder(AllUserInfo.this);
                final View dialogView_password = LayoutInflater.from(AllUserInfo.this)
                        .inflate(R.layout.dialog_password, null);
                passwordDialog.setTitle("更改密码");
                passwordDialog.setView(dialogView_password);
                passwordDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 获取EditView中的输入内容
                                EditText password1 =
                                        dialogView_password.findViewById(R.id.change_password_1);
                                EditText password2 =
                                        dialogView_password.findViewById(R.id.change_password_2);
                                if (password1.getText().toString().trim().equals(password2.getText().toString().trim()) && password1.getText().toString().trim().length() >= 5) {
                                    String editpassword = password1.getText().toString().trim();
                                    userDao.changePassword(get_edit_ID, editpassword);
                                    Toast.makeText(AllUserInfo.this, "更改成功", Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(AllUserInfo.this, "密码确认失败，或者长度不够", Toast.LENGTH_SHORT).show();
                            }
                        });
                passwordDialog.show();
                break;
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4以上系统
                        handleImageOnKitKat(data);
                    } else {
                        //4.4以下系统
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            case 3:
                if (data != null) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    Drawable photo;
                    photo = new BitmapDrawable(bitmap);
                    try {
                        userDao.changeUser_Photo(get_edit_ID, photo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 5:
                if (resultCode == RESULT_OK) {
                    String editCareer = data.getStringExtra("career_name");
                    userDao.changeCareer(get_edit_ID, editCareer);
                    String editIntensity = userDao.getIntensity(editCareer);
                    userDao.changeIntensity(get_edit_ID, editIntensity);
                    onResume();
                }
                break;
            default:
                break;
        }
    }

    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("outputFormat", "jpg");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, 3);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = null;
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                crop(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                crop(contentUri);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            crop(uri);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            crop(uri);
        }
//        displayImage(imagePath);
    }

   /* private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        } else {
            Toast.makeText(this, "获取图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }*/


    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        crop(uri);
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 4:
                // onDateSetListener为用户点击设置时执行的回调函数，数字是默认显示的日期，
                // 注意月份设置11而实际显示12，会自动加1
                return new DatePickerDialog(this, onDateSetListener, 1991, 2, 2);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        // 第一个参数指整个DatePicker，第二个参数是当前设置的年份，
        // 第三个参数是当前设置的月份，注意的是，获取设置的月份时需要加1，因为java中规定月份在0~11之间

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
            String changeBirth = zc_year + "-" + zc_month + "-" + zc_day;
            Date changeBirth_sql = Date.valueOf(changeBirth);
            userDao.changeBirth(get_edit_ID, changeBirth_sql);
            onResume();
            Toast.makeText(view.getContext(), changeBirth, Toast.LENGTH_SHORT).show();
        }

    };

    public String getShape(String R_weight, String A_weight) {
        String shape;
        double rate = (Double.valueOf(R_weight) - Double.valueOf(A_weight)) / Double.valueOf(A_weight);
        if (rate <= -0.2) {
            shape = "消瘦";
        } else if (rate > -0.2 && rate < -0.1) {
            shape = "偏瘦";
        } else if (rate >= -0.1 && rate <= 0.1) {
            shape = "正常";
        } else if (rate > 0.1 && rate < 0.2) {
            shape = "超重";
        } else {
            shape = "肥胖";
        }
        return shape;
    }

    public int getAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException("The birthDay is before Now.It 's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth)
                    age--;
            } else {
                age--;
            }
        }
        return age;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        edit_user_photo.setImageDrawable(userDao.getUser_Photo(get_edit_ID));
        edit_user_nickname.setText(userDao.getNickname(get_edit_ID));
        edit_user_sex.setText(userDao.getSex(get_edit_ID));
        edit_user_birth.setText(userDao.getBirth(get_edit_ID));
        edit_user_tall.setText(userDao.getTall(get_edit_ID) + "cm");
        edit_user_weight.setText(userDao.getWeight(get_edit_ID) + "kg");
        edit_user_weight_expect.setText(userDao.getExpect_weight(get_edit_ID) + "kg");
        edit_user_career.setText(userDao.getCareer(get_edit_ID));
        edit_user_shape.setText(userDao.getShape(get_edit_ID));
        edit_user_intensity.setText(userDao.getIntensity(userDao.getCareer(get_edit_ID)));
        edit_birth_str_date = Date.valueOf(edit_user_birth.getText().toString());
        int a = getAge(edit_birth_str_date);
        edit_user_age.setText(String.valueOf(a));
    }


}
