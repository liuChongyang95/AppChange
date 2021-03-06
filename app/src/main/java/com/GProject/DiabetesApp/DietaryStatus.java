package com.GProject.DiabetesApp;
/*
* 用户饮食情况环状图
* */
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.List;

import JavaBean.Dietary;
import JavaBean.PassValueUtil;
import SearchDao.FoodDao;
import SearchDao.FoodRecordDao;
import SearchDao.UserIntakeDao;
import Util.AnimateUtil;


/*
pick_Type 值有两种： Various Single

 * String StringBuilder StringBuffer用不好，需要练习*/

public class DietaryStatus extends AppCompatActivity {

    final int version = Build.VERSION.SDK_INT;
    private String userId;
    private String pickType;
    private String nowDay;
    private String dateList_json = null;
    private List<String> dateList;
    private FoodRecordDao foodRecordDao;
    private String dataJson2JS;
    private static final String TAG = "DietaryStatus";
    private String foodName;
    private WebView webView;
    private static final String APP_ID = "wxafc530d47fd59207";
    private IWXAPI iwxapi;
    private float bgAlpha = 1f;
    private boolean bright = false;
    private IntentFilter intentFilter;
    private NetworkStatusReceiver networkStatusReceiver;
    private Gson gson;


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
        setContentView(R.layout.dietary_doughnut);
//        页面微信绑定
        reg2WX();
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkStatusReceiver = new NetworkStatusReceiver();
        Toolbar toolbar = findViewById(R.id.dietaryToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DietaryStatus.this.finish();
            }
        });
        gson = new Gson();
        //        当前日期
        foodRecordDao = new FoodRecordDao(DietaryStatus.this);

        //        web设置
        webView = findViewById(R.id.dietrayDoughnut);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.getBuiltInZoomControls();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webView.loadUrl("file:///android_asset/web/Doughnut.html");

        Intent intent = getIntent();
        Bundle bundleFromFAF = intent.getExtras();
        if (bundleFromFAF != null) {
//          用户id
            userId = bundleFromFAF.getString("from_Login_User_id");
            pickType = bundleFromFAF.getString("pick_Type");
            if ("Single".equals(pickType)) {
                nowDay = bundleFromFAF.getString("pick_Time");
                //        查找每日记录,分类整合，并转化为JSON
                dateList_json = gson.toJson(nowDay);
                dayRecordtrans2JSON(nowDay);
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        view.loadUrl("javascript: endEXE('" + dataJson2JS + "','" + dateList_json + "')");
                    }
                });
            }
            if ("Various".equals(pickType)) {
                PassValueUtil passValueUtil;
                passValueUtil = (PassValueUtil) bundleFromFAF.getSerializable("dateList");
                if (passValueUtil != null) {
                    dateList = passValueUtil.getDatepickList();
//                    日期组用于在标题显示
                    dateList_json = gson.toJson(dateList);
                    Log.d(TAG, dateList_json);
                }
                dateListRecordtrans2JSON(dateList);
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        view.loadUrl("javascript: endEXE('" + dataJson2JS + "','" + dateList_json + "')");
                    }
                });
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wxshare, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.wxShare:
                Log.d(TAG, "onOptionsItemSelected: ");
                shared2WX();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void reg2WX() {
        iwxapi = WXAPIFactory.createWXAPI(DietaryStatus.this, APP_ID, true);
        iwxapi.registerApp(APP_ID);
    }

    public void shared2WX() {
//        ①包裹要发送的信息类型
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = "www.me11571467-1.icoc.me";
        //         包裹要发送的信息
        final WXMediaMessage wxMediaMessage = new WXMediaMessage();
        wxMediaMessage.mediaObject = webpageObject; //msg.mediaObject实际上是个IMediaObject对象, 比如WXTextObject对应发送的信息是文字,想要发送文字直接传入WXTextObject对象就行
        wxMediaMessage.description = "当日饮食情况(测试)";
        wxMediaMessage.title = "糖Dapp";
        //        ②创建缩略图
        BitmapDrawable wxShare = (BitmapDrawable) getResources().getDrawable(R.drawable.wxshare);
        Bitmap wxSharebm = Bitmap.createScaledBitmap(wxShare.getBitmap(), 120, 120, true);
        wxMediaMessage.setThumbImage(wxSharebm);
        wxSharebm.recycle();
        //        ③请求对象
        //创建请求对象
        final SendMessageToWX.Req req = new SendMessageToWX.Req();
        //唯一的请求字段
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = wxMediaMessage;

        View shareScrollview = LayoutInflater.from(this).inflate(R.layout.wxshare_popupwindow, null);
        PopupWindow popupWindow = new PopupWindow(shareScrollview, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setContentView(shareScrollview);
        popupWindow.setAnimationStyle(R.style.MyPopupWindow_anim_style);
        Button sharepy = shareScrollview.findViewById(R.id.share_py);
        Button sharepyq = shareScrollview.findViewById(R.id.share_pyq);
        sharepy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                req.scene = SendMessageToWX.Req.WXSceneSession;
                iwxapi.sendReq(req);
            }
        });

        sharepyq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //        发送到朋友圈  req.scene 默认发送个人WXSceneSession
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                iwxapi.sendReq(req);
            }
        });
        View rootView = LayoutInflater.from(this).inflate(R.layout.dietary_doughnut, null);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        if (popupWindow.isShowing()) {
            toggleBright();
        }

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                toggleBright();
            }
        });
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

            } else {
                Toast.makeText(context, "网络连接异常,可能无法使用微信分享", Toast.LENGTH_SHORT).show();
            }

        }

    }

    //        查找每日记录,分类整合，并转化为JSON
    private void dayRecordtrans2JSON(String date) {
        String dataJson = foodRecordDao.dayRecord(userId, date);
        List<Dietary> dietaryList = gson.fromJson(dataJson, new TypeToken<List<Dietary>>() {
        }.getType());
        FoodDao foodDao = new FoodDao(this);
//        关于String和StringBuilder返回值的问题，会有资源开销
        int dLength = dietaryList.size();
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        int initItem = 1;
        for (int a = 0; a < dLength; a++) {
            Dietary dietaryA = dietaryList.get(a);
            if (dietaryA.getFoodId() != null) {
                foodName = foodDao.find_Name(dietaryA.getFoodId());
                if (a == dLength - 1) {
//                    foodName值被改变
                    stringIntegerHashMap.put(foodName, initItem);
                    initItem = 1;
                } else {
                    for (int b = a + 1; b < dLength; b++) {
                        Dietary dietaryB = dietaryList.get(b);
                        if (dietaryA.getFoodId().equals(dietaryB.getFoodId())) {
                            initItem = initItem + 1;
                            dietaryB.setFoodId(null);
                        }
                    }
                    stringIntegerHashMap.put(foodName, initItem);
                    initItem = 1;
                }
            }
        }
        dataJson2JS = gson.toJson(stringIntegerHashMap);
    }

    //    整合日期组所有食物
    private void dateListRecordtrans2JSON(List<String> dateList) {
        String dataJson = foodRecordDao.dayListRecord(userId, dateList);
        Gson gson = new Gson();
        List<Dietary> dietaryList = gson.fromJson(dataJson, new TypeToken<List<Dietary>>() {
        }.getType());
        FoodDao foodDao = new FoodDao(this);
//        关于String和StringBuilder返回值的问题，会有资源开销
        int dLength = dietaryList.size();
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        int initItem = 1;
        for (int a = 0; a < dLength; a++) {
            Dietary dietaryA = dietaryList.get(a);
            if (dietaryA.getFoodId() != null) {
                foodName = foodDao.find_Name(dietaryA.getFoodId());
                if (a == dLength - 1) {
//                    foodName值被改变
                    stringIntegerHashMap.put(foodName, initItem);
                    initItem = 1;
                } else {
                    for (int b = a + 1; b < dLength; b++) {
                        Dietary dietaryB = dietaryList.get(b);
                        if (dietaryA.getFoodId().equals(dietaryB.getFoodId())) {
                            initItem = initItem + 1;
                            dietaryB.setFoodId(null);
                        }
                    }
                    stringIntegerHashMap.put(foodName, initItem);
                    initItem = 1;
                }
            }
        }
        dataJson2JS = gson.toJson(stringIntegerHashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(networkStatusReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkStatusReceiver);
        webView.removeAllViews();
        webView.destroy();
    }
}
