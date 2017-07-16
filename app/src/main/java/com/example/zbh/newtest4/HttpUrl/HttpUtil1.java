package com.example.zbh.newtest4.HttpUrl;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by zbh on 2017/6/14.
 */
//////////////////////////////post方式传一个数据
public class HttpUtil1 {
    public static void sendOkhttpRequest(String address,String keyname,String contentname, Callback callback){
        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody=new FormBody.Builder()
                .add(keyname,contentname)
                .build();
        Request request=new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
