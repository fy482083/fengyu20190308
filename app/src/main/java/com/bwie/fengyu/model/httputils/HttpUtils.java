package com.bwie.fengyu.model.httputils;


import android.os.Handler;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtils {

    private static HttpUtils httpUtils;
    public Handler handler = new Handler();

    //单例
    private HttpUtils(){}
    public static HttpUtils getInstance(){
        if (httpUtils==null){
            httpUtils = new HttpUtils();
            return httpUtils;
        }else{
            return httpUtils;
        }
    }
    //get方法
    public void getData(String url, final Class tclass, final CallBackData callBackdata){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBackdata.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                final Object o = gson.fromJson(string, tclass);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBackdata.onResponse(o);
                    }
                });
            }
        });
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//               callBackdata.onFailure(e);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                String string = response.body().string();
//                Gson gson = new Gson();
//               final Object o = gson.fromJson(string, tclass);
//
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        callBackdata.onResponse(o);
//                    }
//                });
//            }
//        });

    }

    public interface CallBackData{
           void onResponse(Object data);
           void onFailure(IOException e);
    }
}
