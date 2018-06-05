package com.GProject.DiabetesApp;

import android.Manifest;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.Calendar;

import SearchDao.UserDao;
import Util.AnimateUtil;
import de.hdodenhof.circleimageview.CircleImageView;


public class AllUserInfo extends AppCompatActivity implements View.OnClickListener {
    private String get_edit_ID;
    private String get_edit_LoginName;
    private TextView edit_user_ID;
    private TextView edit_user_loginName;
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
    private TextView edit_user_position;
    //    PopWindow 图库和相机
    private PopupWindow popupWindow;
    private CircleImageView edit_user_photo;
    private Date edit_birth_str_date;
    private float edit_expect_weight;
    private UserDao userDao = new UserDao(this);
    private String mExtStorDir;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CODE_REQUEST_CAMERA = 0xa4;
    private static final int REQUEST_PERMISSION = 7;
    private static final int REQUEST_CAMERA = 1;
    private static final String CROP_IMAGE_FILE_NAME = "cropPhoto.jpg";
    private static final String CAPTURE_PICTURE = "capturePhoto.jpg";
    private Uri mUriPath;
    private Bundle bundle_from_MA;
    private File tempFile;
    private static final String TAG = "AllUserInfo";
    //    调用背景变暗
    private float bgAlpha = 1f;
    private boolean bright = false;

    private IntentFilter intentFilter;
    private NetworkStatusReceiver networkStatusReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //任务栏适配，字体颜色，背景颜色
        if (Build.VERSION.SDK_INT >= 21) {
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //相机文件路径 大于7.0
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
        setContentView(R.layout.user_info_edit);
        Intent intent = getIntent();
        bundle_from_MA = intent.getExtras();
        mExtStorDir = Environment.getExternalStorageDirectory().toString();
        Toolbar toolbar = findViewById(R.id.user_info_edit_toolbar);
        edit_user_photo = findViewById(R.id.user_info_edit_photo);
        edit_user_ID = findViewById(R.id.user_info_medical_id);
        edit_user_nickname = findViewById(R.id.user_info_edit_nickname);
        edit_user_loginName = findViewById(R.id.user_info_login_name);
        edit_user_sex = findViewById(R.id.user_info_edit_sex);
        edit_user_birth = findViewById(R.id.user_info_edit_birth);
        edit_user_tall = findViewById(R.id.user_info_edit_tall);
        edit_user_weight = findViewById(R.id.user_info_edit_weight);
        edit_user_career = findViewById(R.id.user_info_edit_career);
        edit_user_intensity = findViewById(R.id.user_info_intensity);
        edit_user_shape = findViewById(R.id.user_info_shape);
        edit_user_weight_expect = findViewById(R.id.user_info_Expect_weight);
        edit_user_age = findViewById(R.id.user_info_age);
        edit_user_position = findViewById(R.id.user_info_edit_position);
//        用于将popwindow添加进去
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllUserInfo.this.finish();
            }
        });

        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkStatusReceiver = new NetworkStatusReceiver();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(networkStatusReceiver, intentFilter);
        get_edit_ID = bundle_from_MA.getString("from_Login_User_id");
        get_edit_LoginName = bundle_from_MA.getString("from_Login_User_Username");
        edit_user_ID.setText(get_edit_ID);
        edit_user_loginName.setText(get_edit_LoginName);
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
        edit_user_position.setText(userDao.getPosition(get_edit_ID));
        int a = getAge(edit_birth_str_date);
        edit_user_age.setText(String.valueOf(a));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //修改昵称
            case R.id.user_info_LL_nickname:
                final EditText editText = new EditText(AllUserInfo.this);
                editText.setHint("点击输入");
                editText.setMaxLines(1);
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
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
                showPopWindow();
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
                                            Toast.makeText(AllUserInfo.this, edit_result_weight[0] + "kg", Toast.LENGTH_SHORT).show();
                                        } else {
                                            edit_result_weight[0] = big_weight.getText().toString() + "." + small_weight.getText().toString();
                                            userDao.changeWeight(get_edit_ID, edit_result_weight[0]);
                                            String editShape = getShape(userDao.getWeight(get_edit_ID), userDao.getExpect_weight(get_edit_ID));
                                            userDao.changeShape(get_edit_ID, editShape);
                                            Toast.makeText(AllUserInfo.this, edit_result_weight[0] + "kg", Toast.LENGTH_SHORT).show();
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
            case R.id.user_info_LL_position:
                Intent intentPosition = new Intent(this, UserPosition.class);
                intentPosition.putExtras(bundle_from_MA);
                startActivity(intentPosition);
                break;
            case R.id.chooseGallery:
                checkReadPermissionGallery();
                popupWindow.dismiss();
                break;
            case R.id.chooseCamera:
                try {
                    checkPermissionCamera();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    popupWindow.dismiss();
                }
                break;
        }
    }

    private void showPopWindow() {
//        popwindow
        View contentView = LayoutInflater.from(this).inflate(R.layout.photo_popwindow, null);
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setContentView(contentView);
        popupWindow.setAnimationStyle(R.style.MyPopupWindow_anim_style);
        TextView gallery = contentView.findViewById(R.id.chooseGallery);
        TextView camera = contentView.findViewById(R.id.chooseCamera);
        gallery.setOnClickListener(this);
        camera.setOnClickListener(this);
//        显示popupwindow
        View rootView = LayoutInflater.from(this).inflate(R.layout.user_info_edit, null);
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
//        popupWindow.showAsDropDown(rootView);

        //        弹出popwin之后的背景变暗
        if (popupWindow != null && popupWindow.isShowing()) {
            toggleBright();
        }
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                toggleBright();
            }
        });
    }

    //    图库权限
    private void checkReadPermissionGallery() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission == PackageManager.PERMISSION_DENIED) {
            String[] permissions;
            permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION);
        } else {
            choseHeadImageFromGallery();
        }
    }

    //相机权限
    private void checkPermissionCamera() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permission == PackageManager.PERMISSION_DENIED) {
            String[] permissions;
            permissions = new String[]{Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CAMERA);
        } else {
            invokingCamera();
        }
    }

    //    相机
    private void invokingCamera() {
        String sdStatus = Environment.getExternalStorageState();
//        "MediaStore.ACTION_IMAGE_CAPTURE" 动作无法接收
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        如果装备了SD卡
        if (Environment.MEDIA_MOUNTED.equals(sdStatus)) {
//            popupWindow做出选项的时候，调用到startActivityforResult哪块
            tempFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + CAPTURE_PICTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            setResult(RESULT_OK, intent);
            startActivityForResult(intent, CODE_REQUEST_CAMERA);
        } else {
            Toast.makeText(this, "环境异常，可能未装备SD卡。", Toast.LENGTH_SHORT).show();
        }

    }

    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        // 设置文件类型    （在华为手机中不能获取图片，要替换代码）
        /*Intent intentFromGallery = new Intent();
        intentFromGallery.setType("image*//*");
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);*/
        Intent intentFromGallery = new Intent(Intent.ACTION_PICK, null);
        intentFromGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }
        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                Log.d(TAG, "cgr");
                cropRawPhoto(data.getData());
                break;
            case CODE_RESULT_REQUEST:
                /*if (intent != null) {
                    setImageToHeadView(intent);    //此代码在小米有异常，换以下代码
                }*/
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mUriPath));
                    setImageToHeadView(data, bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
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
            case CODE_REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {//                获取intent里面的值
                    try {
                        if (tempFile.exists()) {
//                            可能需要一个版本判断
                            cropRawPhoto(Uri.fromFile(tempFile));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    public void cropRawPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", 240);
        intent.putExtra("outputY", 240);
        intent.putExtra("return-data", true);
        //startActivityForResult(intent, CODE_RESULT_REQUEST); //直接调用此代码在小米手机有异常，换以下代码
        String mLinshi = System.currentTimeMillis() + CROP_IMAGE_FILE_NAME;
        File mFile = new File(mExtStorDir, mLinshi);
//        mHeadCachePath = mHeadCacheFile.getAbsolutePath();
        mUriPath = Uri.parse("file://" + mFile.getAbsolutePath());
        //将裁剪好的图输出到所建文件中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUriPath);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        //注意：此处应设置return-data为false，如果设置为true，是直接返回bitmap格式的数据，耗费内存。设置为false，然后，设置裁剪完之后保存的路径，即：intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPath);
//        intent.putExtra("return-data", true);
        intent.putExtra("return-data", false);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    private void setImageToHeadView(Intent intent, Bitmap b) {
        /*Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            headImage.setImageBitmap(photo);
        }*/
        try {
            if (intent != null) {
//                Bitmap bitmap = imageZoom(b);//看个人需求，可以不压缩
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 100, os);
                byte[] ba = os.toByteArray();
                userDao.changeUser_Photo(get_edit_ID, ba);
//                long millis = System.currentTimeMillis();
                /*File file = FileUtil.saveFile(mExtStorDir, millis+CROP_IMAGE_FILE_NAME, bitmap);
                if (file!=null){
                    //传递新的头像信息给我的界面
                    Intent ii = new Intent();
                    setResult(new_icon,ii);
                    Glide.with(this).load(file).apply(RequestOptions.circleCropTransform())
//                                .apply(RequestOptions.fitCenterTransform())
                            .apply(RequestOptions.placeholderOf(R.mipmap.user_logo)).apply(RequestOptions.errorOf(R.mipmap.user_logo))
                            .into(mIvTouxiangPersonal);
//                uploadImg(mExtStorDir,millis+CROP_IMAGE_FILE_NAME);
                    uploadImg(mExtStorDir,millis+CROP_IMAGE_FILE_NAME);
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    choseHeadImageFromGallery();
                } else {
                    Toast.makeText(this, "图库权限不通过", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    invokingCamera();
                } else {
                    Toast.makeText(this, "相机权限不通过", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //    变亮或者变暗 全局bright bgAlpha
    private void toggleBright() {
        //        变暗动画
        AnimateUtil animateUtil = new AnimateUtil();
        //三个参数分别为： 起始值 结束值 时长  那么整个动画回调过来的值就是从0.5f--1f的
        animateUtil.setValueAnimator(0.5f, 1f, 350);
        animateUtil.addUpdateListener(new AnimateUtil.UpdateListener() {
            @Override
            public void progress(float progress) {
                //此处系统会根据上述三个值，计算每次回调的值是多少，我们根据这个值来改变透明度
                bgAlpha = bright ? progress : (1.5f - progress);//三目运算，应该挺好懂的。
//                背景变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = bgAlpha; //0.0-1.0
                getWindow().setAttributes(lp);
//                注销。因为可能和自带的变暗效果冲突
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });
        animateUtil.addEndListner(new AnimateUtil.EndListener() {
            @Override
            public void endUpdate(Animator animator) {
                //在一次动画结束的时候，翻转状态
                bright = !bright;
            }
        });
        animateUtil.startAnimator();
    }

    class NetworkStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (connectivityManager != null) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            if (networkInfo != null && networkInfo.isAvailable()) {
                Toast.makeText(context, "网络连接正常", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "网络连接异常", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkStatusReceiver);
    }

}
