package com.mad.trafficclient.util;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mad.trafficclient.model.BaseBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    private static final boolean raceDevice = false;        //是否为2015年国赛设备环境
    public static String host = "http://121.9.253.237:8088/transportservice/";
    public static String userName = "admin";
    public static final OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
    private static Gson gson = new Gson();
    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static JSONObject buildJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        if (!raceDevice)

            jsonObject.put("UserName", userName);
        return jsonObject;
    }

    public static JSONObject buildJSON(String json) throws JSONException {
        return new JSONObject(json);
    }

    public static RequestBody buildRequestBody(JSONObject jsonObject) throws JSONException {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
    }

    public static Call buildCall(String api, RequestBody requestBody) {
        return okHttpClient.newCall((new Request.Builder()
                .url(host + api)
                .post(requestBody)
                .build()));
    }

    //同步请求块
    public static <T>T  postRequest(Class<T> cls, String api) throws JSONException, IOException {
        return convertGSON(buildCall(api, buildRequestBody(buildJSON())).execute().body().string(), cls);
    }

    public static <T>T  postRequest(Class<T> cls, String api, String json) throws Exception {
        return convertGSON(buildCall(api, buildRequestBody(buildJSON(json))).execute().body().string(), cls);
    }

    public static <T>T  postRequest(Class<T> cls, String api, RequestBody requestBody) throws IOException {
        return convertGSON(buildCall(api, requestBody).execute().body().string(), cls);
    }

    public static <T>T  postRequest(Class<T> cls, String api, String[] key, Object... values) throws JSONException, IOException {
        JSONObject jsonObject = buildJSON();
        for (int i = 0; i < key.length; ++i)
            jsonObject.put(key[i], values[i]);
        return convertGSON(buildCall(api, buildRequestBody(jsonObject)).execute().body().string(), cls);
    }

    //异步请求块
    public static void asyncRequest(String api, Callback callback) throws JSONException {
        buildCall(api, buildRequestBody(buildJSON())).enqueue(callback);
    }

    public static void asyncRequest(String api, String json, Callback callback) throws JSONException {
        buildCall(api, buildRequestBody(buildJSON(json))).enqueue(callback);
    }

    public static void asyncRequest(String api, RequestBody requestBody, Callback callback) {
        buildCall(api, requestBody).enqueue(callback);
    }

    public static void asyncRequest(String api, Callback callback, String[] key, Object... values) throws JSONException {
        JSONObject jsonObject = buildJSON();
        for (int i = 0; i < key.length; ++i)
            jsonObject.put(key[i], values[i]);
        buildCall(api, buildRequestBody(jsonObject)).enqueue(callback);
    }

    public static boolean checkSuccessOrToast(final FragmentActivity activity, final BaseBean baseBean) {
        if (baseBean != null && (raceDevice ? (baseBean.getRESULT() == null || baseBean.getRESULT().equals("ok")) : baseBean.getRESULT().equals("S")))
            return true;
        else activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, "连接服务器失败", Toast.LENGTH_LONG).show();
            }
        });
        LoadingDialog.disDialog();
        return false;
    }

    public static boolean checkSuccessOrToast(final Context context, final Handler handler, final BaseBean baseBean) {
        if (baseBean != null && (raceDevice ? (baseBean.getRESULT() == null || baseBean.getRESULT().equals("ok")) : baseBean.getRESULT().equals("S")))
            return true;
        else handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "连接服务器失败", Toast.LENGTH_LONG).show();
            }
        });
        LoadingDialog.disDialog();
        return false;
    }

    //线程控制代码块
    //注意提交的Runnable不要有线程休眠代码
    public synchronized static Future<?> submit(Runnable rub) {
        return executorService.submit(rub);
    }

    //使用submit提交后记得在Fragment的onDestroyView生命周期取消事务
    public synchronized static void cancelAll() {
        executorService.shutdownNow();
        executorService = Executors.newFixedThreadPool(1);
    }

    public static <T>T convertGSON(String json, Class<T> cls) {
        try {
            if (!raceDevice)
                return gson.fromJson(json, cls);
            else {
                JSONObject jsonObject = new JSONObject(json);
                String s = jsonObject.getString("serverinfo");
                if (s.indexOf("{") == 0)
                    return gson.fromJson(jsonObject.getString("serverinfo"), cls);
                else
                    return null;
            }
        }
        catch (Exception e) {
            HttpUtil.printLogError(e);
            return null;
        }
    }

    public static void printLogError(Throwable e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        Log.e(HttpUtil.class.getSimpleName(), stringWriter.toString());
    }

}
