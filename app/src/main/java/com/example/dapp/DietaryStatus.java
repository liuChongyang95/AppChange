package com.example.dapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXFileObject;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.List;

import JavaBean.Dietary;
import SearchDao.FoodDao;
import SearchDao.FoodRecordDao;


/*
 * String StringBuilder StringBuffer用不好，需要练习*/

public class DietaryStatus extends AppCompatActivity {

    final int version = Build.VERSION.SDK_INT;
    private String userId;
    private String nowDay;
    private FoodSelected foodSelected;
    private FoodRecordDao foodRecordDao;
    private String dataJson2JS;
    private static final String TAG = "DietaryStatus";
    private String foodName;
    private WebView webView;
    private static final String APP_ID = "wxafc530d47fd59207";
    private IWXAPI iwxapi;

    @SuppressLint("SetJavaScriptEnabled")
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
        Toolbar toolbar = findViewById(R.id.dietaryToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DietaryStatus.this.finish();
            }
        });
        Intent intent = getIntent();
        Bundle bundleFromFAF = intent.getExtras();
        if (bundleFromFAF != null) {
//          用户id
            userId = bundleFromFAF.getString("from_Login_User_id");
        }
//        调用存在的方法，获取当前日期。
        foodSelected = new FoodSelected();
//        当前日期
        nowDay = foodSelected.initDate();
        foodRecordDao = new FoodRecordDao(DietaryStatus.this);
        String dataJson = foodRecordDao.dayRecord(userId, nowDay);
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
        webView = findViewById(R.id.dietrayDoughnut);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.getBuiltInZoomControls();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webView.loadUrl("file:///android_asset/web/Doughnut.html");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript: endEXE('" + dataJson2JS + "')");
            }
        });
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
        WXWebpageObject webpageObject=new WXWebpageObject();
        webpageObject.webpageUrl="www.me11571467-1.icoc.me";
//         包裹要发送的信息
        WXMediaMessage wxMediaMessage = new WXMediaMessage();
        wxMediaMessage.mediaObject =webpageObject; //msg.mediaObject实际上是个IMediaObject对象, 比如WXTextObject对应发送的信息是文字,想要发送文字直接传入WXTextObject对象就行
        wxMediaMessage.description="当日饮食情况(测试)";
        wxMediaMessage.title="糖Dapp";
//        ②创建缩略图
        BitmapDrawable wxShare = (BitmapDrawable) getResources().getDrawable(R.drawable.wxshare);
        Bitmap wxSharebm=Bitmap.createScaledBitmap(wxShare.getBitmap(),120,120,true);
        wxMediaMessage.setThumbImage(wxSharebm);
        wxSharebm.recycle();
//        ③请求对象
        //创建请求对象
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        //唯一的请求字段
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = wxMediaMessage;
//        发送到朋友圈  req.scene 默认发送个人WXSceneSession
//        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        iwxapi.sendReq(req);
    }
}
