package Util;
/*
* 服务器请求数据 遍历全国省市*/
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
//    请求地址 和 回调
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request= new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
